package turbomeca.gamme.assembly.services.dvi.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import turbomeca.gamme.assembly.services.constants.Constants;
import turbomeca.gamme.assembly.services.dvi.IScheduleContentAssemblyService;
import turbomeca.gamme.assembly.services.dvi.ISearchIntermediaryCreationContext;
import turbomeca.gamme.assembly.services.dvi.SearchCreationContextException;
import turbomeca.gamme.assembly.services.schedule.creation.bean.ContextScheduleCreationAssembly;
import turbomeca.gamme.assembly.services.schedule.creation.bean.ContextUserAssemblyServer;
import turbomeca.gamme.assembly.services.schedule.model.types.RangeType;
import turbomeca.gamme.ecran.services.bodb.bo.BoSchedule;
import turbomeca.gamme.ecran.services.bodb.service.IContextSynchroBoDataBuilderService;
import turbomeca.gamme.ecran.services.bodb.service.ISynchronizationScheduleDatabaseService;
import turbomeca.gamme.ecran.services.bodvi.bo.BoDviArticleInfo;
import turbomeca.gamme.ecran.services.bodvi.bo.BoDviResult;
import turbomeca.gamme.ecran.services.bodvi.bo.BoDviResultDocument;
import turbomeca.gamme.ecran.services.bodvi.bo.BoDviSchedule;
import turbomeca.gamme.ecran.services.bodvi.bo.BoDviScheduleInfo;
import turbomeca.gamme.ecran.services.bodvi.bo.DviResultCode;
import turbomeca.gamme.ecran.services.bomodel.IBoSchedule;
import turbomeca.gamme.ecran.services.common.bean.ContextUser;
import turbomeca.gamme.ecran.services.dvi.BoToDviScheduleTransformer;
import turbomeca.gamme.ecran.services.dvi.RepositoryType;
import turbomeca.gamme.ecran.services.dvi.impl.DviInterfaceService;
import turbomeca.gamme.ecran.services.runtime.bean.ContextSynchronizeBoData;
import turbomeca.gamme.ecran.services.schedule.bo.BoInstanciatedSchedule;
import turbomeca.gamme.ecran.services.schedule.dao.DaoInstanciatedSchedule;
import turbomeca.gamme.ecran.services.schedule.service.creation.IInstantiateScheduleService;

/***
 * Functional notes:
 * <ul>
 * <li>only assembly new schedules are managed by the
 * <code>getScheduleStructure*()</code> methods
 * <li>all schedules types are supported for the <code>getScheduleContent</code>
 * service
 * 
 * @author ahermo
 *
 */
@WebService
@Service(value="scheduleContentAssemblyService") 
public class ScheduleContentAssemblyServiceImpl implements IScheduleContentAssemblyService {

	private static final Logger logger = Logger.getLogger(ScheduleContentAssemblyServiceImpl.class);

	@Autowired
	private ISynchronizationScheduleDatabaseService synchronizationService;

	@Autowired
	private DaoInstanciatedSchedule daoService;

	@Autowired
	private DviInterfaceService dviInterfaceService;

	@Autowired
	private ISearchIntermediaryCreationContext serviceIntermediarySchedule;

	@Autowired
	private IInstantiateScheduleService serviceInstantiateSchedule;
	
	@Autowired
	private IContextSynchroBoDataBuilderService contextSynchroBoDataBuilderService;

	@WebMethod
	@Override
	public BoDviResultDocument getScheduleStructureFromArticle(
			@WebParam(name = "articlePn") String articlePn,
			@WebParam(name = "va") String va,
			@WebParam(name = "family") String family, 
			@WebParam(name = "variant") String variant,
			@WebParam(name = "material") String material, 
			@WebParam(name = "isolated") Boolean isolated,
			@WebParam(name = "referential") String referential, 
			@WebParam(name = "site") String site, 
			@WebParam(name = "lang") String lang) {

		logger.info( new StringBuilder().append("Schedule Structure From Article for : ")
				.append(articlePn).append(" articlePn ")
				.append(va).append(" va  ")
				.append(family).append(" family  ")
				.append(variant).append(" variant  ")
				.append(material).append(" material  ")
				.append(isolated).append(" isolated  ")
				.append(referential).append(" referential  ")
				.append(lang).append(" lang ")
				.append(site).append(" site  ").toString());

		BoDviResultDocument document = new BoDviResultDocument();
		document.setResult(new ArrayList<BoDviResult>());

		Integer ref = null;
		try {
			ref = Integer.parseInt(referential);
		} catch (NumberFormatException e) {
		}
		
		BoDviResult mandatoryParam = checkMandatoryParam(articlePn, va, family, variant, material, site);
		if (mandatoryParam == null) {
			ContextScheduleCreationAssembly creationContext = null;
			RepositoryType repo = ref != null && ref >=0 && ref < 2 ? RepositoryType.values()[ref] : null;
			try {
				if (repo != null) {
					creationContext = serviceIntermediarySchedule.getIntermediaryCreationContext(
							articlePn, va, family, variant, material, isolated, repo, site, lang);
				} else {
					try {
						creationContext = serviceIntermediarySchedule.getIntermediaryCreationContext(
								articlePn, va, family, variant, material, isolated, RepositoryType.OFFICIAL, site, lang);
					} catch (SearchCreationContextException e) {
						logger.warn("No schedule for PN " + articlePn + " with va " + va + " in official repository", e);
					} finally {
						if (creationContext == null) {
							creationContext = serviceIntermediarySchedule.getIntermediaryCreationContext(
									articlePn, va, family, variant, material, isolated, RepositoryType.OFFICIAL_TEST, site, lang);
						}
					}
				}
				if(creationContext!=null) {
					getSchedule(creationContext, site, document);
				} else {
					// No Order
					BoDviResult noFoundResult = new BoDviResult();
					noFoundResult.setCode(DviResultCode.ERROR);
					noFoundResult.setMessage("No schedule for PN " + articlePn + " with va " + va);
					document.getResult().add(noFoundResult);
				}
			}
			catch (SearchCreationContextException e) {
				BoDviResult successResult = new BoDviResult();
				successResult.setCode(DviResultCode.ERROR);
				successResult.setMessage(e.getMessage());
				document.getResult().add(successResult);
				logger.error(e.getMessage(), e);
			}
			catch(Exception e) {
				BoDviResult successResult = new BoDviResult();
				successResult.setCode(DviResultCode.ERROR);
				successResult.setMessage(e.getMessage());
				document.getResult().add(successResult);
				logger.error(e.getMessage(), e);
			}
		} else {
			document.getResult().add(mandatoryParam);
		}

		return document;
	}

	@WebMethod
	@Override
	public BoDviResultDocument getScheduleStructureFromGeode(
			@WebParam(name = "geodeRef") String geodeRef,
			@WebParam(name = "geodeVersion") String geodeVersion, 
			@WebParam(name = "referential") String referential) {

		logger.info( new StringBuilder().append("Schedule Structure From Geode for : ")
				.append(geodeRef).append(" geodeRef ")
				.append(geodeVersion).append(" geodeVersion  ")
				.append(referential).append(" referential  ").toString());

		BoDviResultDocument document = new BoDviResultDocument();
		document.setResult(new ArrayList<BoDviResult>());

		Integer ref = null;
		try {
			ref = Integer.parseInt(referential);
		} catch (NumberFormatException e) {
		}
		
		BoDviResult mandatoryParam = checkMandatoryParam(geodeRef, geodeVersion);
		if (mandatoryParam == null) {
			ContextScheduleCreationAssembly creationContext = null;
			RepositoryType repo = ref != null && ref >=0 && ref < 2 ? RepositoryType.values()[ref] : null;
			try {
				if (repo != null) {
					creationContext = serviceIntermediarySchedule.getIntermediaryCreationContextFromGeode(
							geodeRef, geodeVersion, repo);
				} else {
					try {
						creationContext = serviceIntermediarySchedule.getIntermediaryCreationContextFromGeode(
								geodeRef, geodeVersion, RepositoryType.OFFICIAL);
					} catch (SearchCreationContextException e) {
						logger.warn("No schedule for geodeRef " + geodeRef + " with geodVersion " + geodeVersion + " for official repository", e);
					} finally {
						if (creationContext == null) {
							creationContext = serviceIntermediarySchedule.getIntermediaryCreationContextFromGeode(
									geodeRef, geodeVersion, RepositoryType.OFFICIAL_TEST);
						}
					}
				}
				if (creationContext!=null) {
					document.getResult().add(getSchedule(creationContext, null, document));
				} else {
					BoDviResult noFoundResult = new BoDviResult();
					noFoundResult.setCode(DviResultCode.ERROR);
					noFoundResult.setMessage("No schedule for geodeRef " + geodeRef + " with geodVersion " + geodeVersion);
					document.getResult().add(noFoundResult);
				}
			} catch (SearchCreationContextException e) {
				BoDviResult successResult = new BoDviResult();
				successResult.setCode(DviResultCode.ERROR);
				successResult.setMessage(e.getMessage());
				document.getResult().add(successResult);
				logger.error(e.getMessage(), e);
			} catch(Exception e) {
				BoDviResult successResult = new BoDviResult();
				successResult.setCode(DviResultCode.ERROR);
				successResult.setMessage(e.getMessage());
				document.getResult().add(successResult);
				logger.error(e.getMessage(), e);
			}
		} else {
			document.getResult().add(mandatoryParam);
		}
		return document;
	}

	private BoDviResult getSchedule(ContextScheduleCreationAssembly contextCreation, String site,
			BoDviResultDocument document) {
		BoDviResult res = new BoDviResult();

		contextCreation.setDateCreation(Calendar.getInstance());
		contextCreation.setScheduleDatabaseId(0L);

		ContextUserAssemblyServer contextUser = new ContextUserAssemblyServer();
		contextUser.setSite(site);
		contextUser.setLogin("DVI");
		contextUser.setName("DVI");
		
		try {
			String s = serviceInstantiateSchedule.instantiateScheduleAsStream(contextUser, contextCreation);
			if(s!=null) {
				ContextSynchronizeBoData contextSynchroBoData = contextSynchroBoDataBuilderService.
						buildContextSynchronizeBoData(buildContextUserWsFromContextUserApp(contextUser, contextCreation));
				IBoSchedule schedule = synchronizationService.loadSchedule(s, contextSynchroBoData, null);
				if(schedule!=null) {
					transformSchedule((BoSchedule)schedule, null, document);
					res.setCode(DviResultCode.NO_ERROR);
				}
			}
			else {
				res.setCode(DviResultCode.WARNING);
				res.setMessage("Could not instantiate schedule");
			}
		} 
		catch (Exception e) {
			res.setCode(DviResultCode.ERROR);
			res.setMessage(e.getMessage());
			logger.error(e.getMessage(), e);
		}
		return res;
	}

	@WebMethod
	@Override
	public BoDviResultDocument getScheduleContent(
			@WebParam(name = "order") String order,
			@WebParam(name = "referential") String referential,
			@WebParam(name = "sn") String sn) {

		logger.info( new StringBuilder().append("Schedule Content for : ")
				.append(order).append(" order ")
				.append(referential).append(" referential  ")
				.append(sn).append(" sn  ").toString());

		BoDviResultDocument document = new BoDviResultDocument();
		document.setResult(new ArrayList<BoDviResult>());

		Integer ref = null;
		try {
			ref = Integer.parseInt(referential);
		} catch (NumberFormatException e) {
		}
		
		BoDviResult mandatoryParam = checkMandatoryParam(order, sn);
		if (mandatoryParam == null) {
			List<BoInstanciatedSchedule> results = null;
			RepositoryType repo = ref != null && ref >=0 && ref < 2 ? RepositoryType.values()[ref] : null;
			try {
				if (repo != null) {
					results = daoService.findByOrderNumberAndScheduleState(order, repo.name());
				} else {
					results = daoService.findByOrderNumberAndScheduleState(order, RepositoryType.OFFICIAL.name());
					if (results.isEmpty()) {
						results = daoService.findByOrderNumberAndScheduleState(order,  RepositoryType.OFFICIAL_TEST.name());
						if (results != null && !results.isEmpty()) {
							repo = RepositoryType.OFFICIAL_TEST;
						}
					} else {
						repo = RepositoryType.OFFICIAL;
					}
				}
			} catch (Exception e) {
				BoDviResult exceptionResult = new BoDviResult();
				exceptionResult.setCode(DviResultCode.ERROR);
				exceptionResult.setMessage(e.getMessage());
				document.getResult().add(exceptionResult);
				logger.error(e.getMessage(), e);
			}
			if (results == null || results.isEmpty()) {
				// No Order
				BoDviResult noFoundResult = new BoDviResult();
				noFoundResult.setCode(DviResultCode.ERROR);
				noFoundResult.setMessage("No schedule for order " + order + " with reference " + repo);
				document.getResult().add(noFoundResult);
			} else if (results.size() > 1) {
				// Too Much Order
				BoDviResult noFoundResult = new BoDviResult();
				noFoundResult.setCode(DviResultCode.ERROR);
				noFoundResult.setMessage("Many schedule founds for order " + order + " with reference " + repo);
				document.getResult().add(noFoundResult);
			} else {
				try {
					BoInstanciatedSchedule boInstanciatedSchedule = results.get(0);
					File range = new File(dviInterfaceService.getRepository(repo).getInstancesFolder(), boInstanciatedSchedule.getFilePath());
					
					ContextSynchronizeBoData contextSynchroBoData = contextSynchroBoDataBuilderService.
							buildContextSynchronizeBoData(boInstanciatedSchedule);
					IBoSchedule schedule = synchronizationService.loadSchedule(range, contextSynchroBoData, sn);
					transformSchedule((BoSchedule)schedule, sn != null && !sn.isEmpty() ? sn : null, document);

					BoDviResult successResult = new BoDviResult();
					successResult.setCode(DviResultCode.NO_ERROR);
					document.getResult().add(successResult);
				} catch (Exception e) {
					BoDviResult errorResult = new BoDviResult();
					errorResult.setCode(DviResultCode.ERROR);
					errorResult.setMessage(e.getMessage());
					document.getResult().add(errorResult);
				}
			}
		} else {
			document.getResult().add(mandatoryParam);
		}
		return document;
	}
	
	private ContextUser buildContextUserWsFromContextUserApp(ContextUserAssemblyServer contextUser, ContextScheduleCreationAssembly contextCreation){
		ContextUser contextUserWs = new ContextUser();
		contextUserWs.setLogin(contextUser.getLogin());
		contextUserWs.setUserName(contextUser.getName());
		contextUserWs.setConfigIndex(contextUser.getDataContext());
		contextUserWs.setRangeName(contextCreation.getScheduleFileAndFolder());
		
		int rangeType = -1;
		if (contextCreation.getScheduleType().equalsIgnoreCase(RangeType.ASSEMBLY.toString())) {
			rangeType = Constants.SCHEDULE_TYPE_ASSEMBLY;
		} else {
			rangeType = Constants.SCHEDULE_TYPE_DISASSEMBLY;
		}
		contextUserWs.setRangeType(rangeType);
		
		return contextUserWs;
	}
	
	private BoDviSchedule transformSchedule(BoSchedule schedule, String sn, BoDviResultDocument document) {
		BoDviSchedule dviSchedule = BoToDviScheduleTransformer.transformBoToDvi(schedule, sn);

		document.setArticleInfo((BoDviArticleInfo) dviSchedule.getArticle());
		document.setScheduleInfo((BoDviScheduleInfo) dviSchedule.getScheduleInfos());
		document.setProvisions(dviSchedule.getProvisions());
		document.setComponents(dviSchedule.getComponents());
		document.setCharacteristics(dviSchedule.getCharacteristics());
		document.setOperations(dviSchedule.getOperations());

		return dviSchedule;
	}

	private BoDviResult checkMandatoryParam(String... args) {
		BoDviResult result = null;
		for (String arg : args) {
			if (arg == null) {
				result = new BoDviResult();
				result.setCode(DviResultCode.ERROR);
				result.setMessage("Missing mandatory parameters");
			}
		}
		return result;
	}
}
