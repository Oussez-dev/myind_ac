package turbomeca.gamme.assembly.services.schedule.creation;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import turbomeca.gamme.assembly.services.constants.Constants;
import turbomeca.gamme.assembly.services.schedule.creation.exception.CreateScheduleAssemblyException;
import turbomeca.gamme.assembly.services.schedule.creation.utils.AssemblyGlobalServices;
import turbomeca.gamme.assembly.services.utils.DocTypeUtils;
import turbomeca.gamme.ecran.services.common.enumtypes.Domain;
import turbomeca.gamme.ecran.services.common.utils.misc.PublishedScheduleUtils;
import turbomeca.gamme.ecran.services.model.constants.ModelGlobalConstants;
import turbomeca.gamme.ecran.services.publication.bo.BoPublishedSchedule;
import turbomeca.gamme.ecran.services.publication.bo.BoPublishedScheduleParameter;
import turbomeca.gamme.ecran.services.publication.bo.BoVariantEffectivity;
import turbomeca.gamme.ecran.services.publication.dao.DaoPublishedSchedule;
import turbomeca.gamme.ecran.services.schedule.bean.ContextScheduleCreation;
import turbomeca.gamme.ecran.services.schedule.service.creation.IServiceScheduleIntermediaryDao;
import turbomeca.gamme.ecran.services.schedule.service.creation.ScheduleCreationDTO;

/**
 * This class handles database DAO for searching intermediary schedule
 * @author  "gbiet"
 *
 */
public class ServiceSearchAssemblyScheduleIntermediaryDbDao implements IServiceScheduleIntermediaryDao {

	private static Logger logger = Logger.getLogger(ServiceSearchAssemblyScheduleIntermediaryDbDao.class);
	
	private static String DEFAULT_INSTANCES_NUMBER = "1";

	@Autowired
	DaoPublishedSchedule daoPublishedSchedule;

	/*
	 * Search for an intermediary schedule
	 * (non-Javadoc)
	 * @see turbomeca.gamme.ecran.server.dao.createSchedule.IServiceScheduleIntermediaryDao#searchScheduleIntermediary(turbomeca.gamme.ecran.server.model.createSchedule.ScheduleCreationDTO, turbomeca.gamme.ecran.services.schedule.bean.ContextScheduleCreation, boolean)
	 */
	@Override
	public ScheduleCreationDTO searchScheduleIntermediary(
			ScheduleCreationDTO scheduleCreation,
			ContextScheduleCreation context) throws Exception {
		
		Collection<BoPublishedSchedule> publishedSchedules;
		HashMap<String, List<?>> listResult = new HashMap<String, List<?>>();
		HashMap<String, String> effectivitiesFromMethod = new HashMap<String, String>();
		
		// Retrieve published schedule list
		logger.debug("searchScheduleIntermediary - call to getPublishedScheduleList()");
		publishedSchedules = getPublishedScheduleList(scheduleCreation, context.isExternalCreation());
		
		
		// Throw exception if empty
		if (publishedSchedules == null || publishedSchedules.isEmpty()) {
			throw new CreateScheduleAssemblyException(
					CreateScheduleAssemblyException.EXCEPTION_EFFECTIVITY_NOT_FOUND);
		}
		
		// Search for all methods
		String variant = "";
		Map<String, String> methodEntityMap = new HashMap<String, String>();
		BoPublishedSchedule myPublishedSchedule = null;
		for (BoPublishedSchedule publishedSchedule : publishedSchedules) {
			
			String effectivity = publishedSchedule.getFileName();
			
			List<BoVariantEffectivity> listEffectivityVariant = PublishedScheduleUtils.getPublishedScheduleVariants(publishedSchedule);
			BoVariantEffectivity effectivityVariant = PublishedScheduleUtils.getVariantFromPn(listEffectivityVariant, scheduleCreation.getPn());
			if(effectivityVariant != null){
				variant = effectivityVariant.getVariant();
			}
			
			List<BoPublishedScheduleParameter> listMethod = PublishedScheduleUtils.getParametersFromSchedule(publishedSchedule, AssemblyGlobalServices.PARAMETER_METHOD);
			
			if (!listMethod.isEmpty()) {
				if(effectivitiesFromMethod.containsKey(listMethod.get(0).getValueSynthPubParam())) {
					if(PublishedScheduleUtils.isLaterVersion(publishedSchedule, methodEntityMap.get(listMethod.get(0).getValueSynthPubParam()))) {
						effectivitiesFromMethod.put(listMethod.get(0).getValueSynthPubParam(), effectivity);
						methodEntityMap.put(listMethod.get(0).getValueSynthPubParam(), publishedSchedule.getVersion());
						logger.debug("searchScheduleIntermediary - found methods - Found a published schedule candidate");
						myPublishedSchedule = publishedSchedule;
					}
					
				} else {
					effectivitiesFromMethod.put(listMethod.get(0).getValueSynthPubParam(), effectivity);
					methodEntityMap.put(listMethod.get(0).getValueSynthPubParam(), publishedSchedule.getVersion());
					logger.debug("searchScheduleIntermediary - found methods but not applicable - Found a published schedule candidate");
					myPublishedSchedule = publishedSchedule;
				}

			} else {
				//No methods
				logger.debug("searchScheduleIntermediary - No methods - Using the last version of published schedule");
				myPublishedSchedule = PublishedScheduleUtils.getLastVersionPublishedSchedule(publishedSchedules);
				break;
			}
		}
		
		logger.debug("searchScheduleIntermediary - Schedule found, using : " + myPublishedSchedule.toString());
		scheduleCreation.setMethodLastVersion(methodEntityMap);
		
		if(!context.isExternalCreation() && !variant.isEmpty() && context.getScheduleType().equals(Domain.ASSEMBLY.toString())
				&& context.getContext().equals(Constants.SCHEDULE_NEW_CONTEXT)){
			scheduleCreation.setVariant(variant);
		} 

		List<String> methods = new ArrayList<String>(effectivitiesFromMethod.keySet());
		List<String> listLevels = PublishedScheduleUtils.getParametersValuesFromSchedule(myPublishedSchedule, ModelGlobalConstants.ATTR_LEVEL);
		for (String level : listLevels) {
			List<String> label = PublishedScheduleUtils.getParametersValuesFromSchedule(myPublishedSchedule, level);
			listResult.put(level, label);
		}
		List<String> listInterventions = PublishedScheduleUtils.getParametersValuesFromSchedule(myPublishedSchedule, ModelGlobalConstants.ATTR_INTERVENTION_TYPE);
		
		String instanceNumber;
		if(myPublishedSchedule.getInstanceNumber()!=null) {
			instanceNumber = myPublishedSchedule.getInstanceNumber().toString();
		} else {
			instanceNumber = DEFAULT_INSTANCES_NUMBER;
		}
		List<String> listInstances = new ArrayList<String>(1);
		listInstances.add(instanceNumber);
		
		listResult.put(ModelGlobalConstants.TAG_LEVELS, listLevels);
		listResult.put(ModelGlobalConstants.TAG_METHODS, methods);
		listResult.put(ModelGlobalConstants.TAG_INTERVENTIONS, listInterventions);
		listResult.put(ModelGlobalConstants.TAG_INSTANCES, listInstances);

		scheduleCreation.setFamily(myPublishedSchedule.getDesignation());

	
		if(myPublishedSchedule.getIndustrialValidation() != null) {
			scheduleCreation.setIndustrialValidation(myPublishedSchedule.getIndustrialValidation());
		} else {
			scheduleCreation.setIndustrialValidation("");
		}

		scheduleCreation.setMaterial(myPublishedSchedule.getSubDesignation());
		scheduleCreation.setLastVersionPublish(myPublishedSchedule.getVersion());
		scheduleCreation.setScheduleIntermediaryFile(myPublishedSchedule.getFileName());
		scheduleCreation.setParameters(listResult);
		scheduleCreation.setEffectivityFileName(myPublishedSchedule.getFileName());
		scheduleCreation.setMethodFromEffectivities(effectivitiesFromMethod);
		scheduleCreation.setIsSapSchedule(myPublishedSchedule.isSapSchedule());
		scheduleCreation.setSuffix(myPublishedSchedule.getSuffix());
		
		return scheduleCreation;
	}

	/**
	 * Retrieve schedule list from dao according to context
	 * @param dto
	 * @param externalCreation
	 * @return
	 */
	private Collection<BoPublishedSchedule> getPublishedScheduleList(ScheduleCreationDTO dto,
			boolean externalCreation) {
		
		Collection<BoPublishedSchedule> publishedSchedules;
		
		// New and external creation context
		if (externalCreation && dto.getContext().equals(Constants.SCHEDULE_NEW_CONTEXT)) {
			
			//Search by language, site, pn
			logger.debug("getPublishedScheduleList - call to daoPublishedSchedule.findBySiteAndLanguageAndPn("+dto.getLanguage().toUpperCase()+","+dto.getSite().toUpperCase()+","+dto.getPn());
			publishedSchedules = daoPublishedSchedule.findBySiteAndLanguageAndPnAndReferential(
					dto.getLanguage().toUpperCase(), dto.getSite().toUpperCase(), dto.getPn(),
					dto.getReferential().toString());
		
		} else {
			// New and no external creation context
			if (dto.getRangeType().equals(AssemblyGlobalServices.SCHEDULE_ASSEMBLY_TYPE) 
					&& dto.getContext().equals(AssemblyGlobalServices.SCHEDULE_ASSEMBLY_NEW)) {
				
				//Search by language, site, pn, va
				logger.debug("getPublishedScheduleList - call to daoPublishedSchedule.findBySiteAndLanguageAndPnAndVa("+dto.getLanguage().toUpperCase()+","+dto.getSite().toUpperCase()+","+dto.getPn()+", "+dto.getConf());
				publishedSchedules = daoPublishedSchedule.findBySiteAndLanguageAndPnAndVaAndReferential(
						dto.getLanguage().toUpperCase(), dto.getSite().toUpperCase(), dto.getPn(), dto.getConf(),
						dto.getReferential().toString());
	
			} else {
				// Modification context
				if (dto.getConf() == null || dto.getConf().length() == 0) {
					
					//Search by language, site, variant, pn with attribut "modification_out" = true
					logger.debug("getPublishedScheduleList - call to daoPublishedSchedule.findBySiteAndLanguageAndVariantAndPnAndModificationOut("+dto.getLanguage().toUpperCase()+","+dto.getSite().toUpperCase()+","+dto.getVariant()+","+dto.getPn());
					publishedSchedules = daoPublishedSchedule
							.findBySiteAndLanguageAndVariantAndPnAndModificationOutAndReferential(
									dto.getLanguage().toUpperCase(), dto.getSite().toUpperCase(), dto.getVariant(),
									dto.getPn(), dto.getReferential().toString());
	
				} else {
					
					//Search by language, site, variant, pn
					logger.debug("getPublishedScheduleList - call to daoPublishedSchedule.findBySiteAndLanguageAndVariantAndPn("+dto.getLanguage().toUpperCase()+","+dto.getSite().toUpperCase()+","+dto.getVariant()+","+dto.getPn());
					publishedSchedules = daoPublishedSchedule.findBySiteAndLanguageAndVariantAndPnAndReferential(dto.getLanguage().toUpperCase(), dto.getSite().toUpperCase(), dto.getVariant(), dto.getPn(), dto.getReferential().toString());
					//Filter by modifications (and mode, or mode)
					List<String> modifications = Arrays.asList(dto.getConf().split(" "));
					publishedSchedules = PublishedScheduleUtils.filterModificationsPublishedSchedule(publishedSchedules, modifications);

				}
			}
		}
		
		publishedSchedules = PublishedScheduleUtils.filterByAffaire(publishedSchedules, DocTypeUtils.getDoctype(dto.getRangeType(), dto.getContext()));
		
		return publishedSchedules;
	}

	@Override
	public List<String> getInformationFromScheduleIntermediary(
			String parentNode, String node, String attrNode,
			File fileIntermediary) throws Exception {
		return null;
	}

}
