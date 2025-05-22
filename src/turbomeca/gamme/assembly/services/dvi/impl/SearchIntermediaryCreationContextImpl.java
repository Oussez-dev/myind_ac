package turbomeca.gamme.assembly.services.dvi.impl;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Node;

import turbomeca.gamme.assembly.services.constants.Constants;
import turbomeca.gamme.assembly.services.dvi.ISearchIntermediaryCreationContext;
import turbomeca.gamme.assembly.services.dvi.SearchCreationContextException;
import turbomeca.gamme.assembly.services.dvi.SearchCreationContextException.CreationContextStatusCode;
import turbomeca.gamme.assembly.services.schedule.config.AssemblyRepositoryConfig;
import turbomeca.gamme.assembly.services.schedule.creation.IAssemblyServiceScheduleIntermediary;
import turbomeca.gamme.assembly.services.schedule.creation.bean.ContextScheduleCreationAssembly;
import turbomeca.gamme.assembly.services.schedule.creation.bean.ContextUserAssemblyServer;
import turbomeca.gamme.assembly.services.schedule.creation.utils.AssemblyGlobalServices;
import turbomeca.gamme.ecran.services.common.utils.misc.FormatUtils;
import turbomeca.gamme.ecran.services.common.utils.misc.PublishedScheduleUtils;
import turbomeca.gamme.ecran.services.common.utils.string.StringUtil;
import turbomeca.gamme.ecran.services.common.utils.xml.BinderUtils;
import turbomeca.gamme.ecran.services.dvi.RepositoryType;
import turbomeca.gamme.ecran.services.dvi.impl.ASearchIntermediaryCreationContext;
import turbomeca.gamme.ecran.services.model.constants.ModelGlobalConstants;
import turbomeca.gamme.ecran.services.publication.bo.BoPublishedSchedule;
import turbomeca.gamme.ecran.services.schedule.service.creation.IServiceIdentification;
import turbomeca.gamme.ecran.services.schedule.service.creation.ScheduleCreationDTO;
import turbomeca.gamme.ecran.services.schedule.service.exception.CreateScheduleIntermediaryException;


/***
 * Service used to build a schedule creation context allowing its instantiation
 * from provided parameter
 * 
 * Note that intermediary schedule are retrieved only for assembly new schedules
 * 
 * 
 * @author ahermo
 *
 */
@Service(value = "turbomeca.gamme.assembly.services.dvi.ISearchIntermediaryCreationContext")
public class SearchIntermediaryCreationContextImpl extends ASearchIntermediaryCreationContext
		implements ISearchIntermediaryCreationContext {

	private static final Logger logger = Logger.getLogger(SearchIntermediaryCreationContextImpl.class);

	private static final String FORCED_CONTEXT= Constants.SCHEDULE_NEW_CONTEXT;
	private static final String FORCED_INTERVENTION_TYPE= AssemblyGlobalServices.SCHEDULE_ASSEMBLY_NEW;
	private static final String FORCED_SCHEDULE_TYPE = AssemblyGlobalServices.SCHEDULE_ASSEMBLY_TYPE;
	
	// Null value implies that this applicability is not filtered on instantiation
	private static final Boolean FORCED_ISOLATED = null;
	
	@Autowired
	private IServiceIdentification serviceIdentification;
	
	@Autowired
	private IAssemblyServiceScheduleIntermediary serviceIntermediary;
	
	
	@Override
	public ContextScheduleCreationAssembly getIntermediaryCreationContextFromGeode(String geodeReference, String geodeVersion,
			RepositoryType referential) throws SearchCreationContextException {
		BoPublishedSchedule boSynthesisPublication = null;
		
		List<BoPublishedSchedule> bosPublishedSchedule = daoPublishedSchedule
			.findByGeodeReferenceAndGeodeVersionAndStatus(geodeReference, geodeVersion, referential.toString());
		
		if (bosPublishedSchedule != null && bosPublishedSchedule.size() > 1) {
			logger.warn("Multiples Geode Ref/Version/State found for " + geodeReference + "/" + geodeVersion + "/" + referential.toString());
		}
		
		if (bosPublishedSchedule == null || bosPublishedSchedule.isEmpty()) {
			throw new SearchCreationContextException(CreationContextStatusCode.ERROR_INTERMEDIARY_SCHEDULE_NOT_FOUND,
					"No Schedule found with Geode reference = " + geodeReference + "/" + geodeVersion);
		} else {
			boSynthesisPublication = 
					PublishedScheduleUtils.getLastVersionPublishedSchedule(bosPublishedSchedule);
		}
		
		/**
		 * PN is mandatory for instantiation process, but 
		 */
		List<String> pns = PublishedScheduleUtils.getParametersValuesFromSchedule(boSynthesisPublication, ModelGlobalConstants.TAG_PN);
		if( pns==null || pns.isEmpty() ) {
			throw new SearchCreationContextException(CreationContextStatusCode.ERROR_INTERMEDIARY_SCHEDULE_NOT_FOUND,
					"No PN found for " + boSynthesisPublication);
		}
		/**
		 * Va is not mandatory, if found, all va are associated with the same
		 * intermediary file (because form the same PublishedSchedule), so only select
		 * one is enough to perform instantiation process
		 */
		String vasForInstanciation = "";
		List<String> vas = PublishedScheduleUtils.getParametersValuesFromSchedule(boSynthesisPublication, ModelGlobalConstants.TAG_VA);
		if( vas!=null && !vas.isEmpty()) {
			vasForInstanciation = vas.get(0);
		}
		
		List<String> sites = PublishedScheduleUtils.getParametersValuesFromSchedule(boSynthesisPublication, ModelGlobalConstants.TAG_SITE);
		if( sites== null || sites.isEmpty()) {
			throw new SearchCreationContextException(CreationContextStatusCode.ERROR_INTERMEDIARY_SCHEDULE_NOT_FOUND,
					"No Site found for " + boSynthesisPublication);
		} else if ( sites.size() == 1) {
			sites = Arrays.asList(sites.get(0).split(" "));
		}
		
		List<String> langs = PublishedScheduleUtils.getParametersValuesFromSchedule(boSynthesisPublication, ModelGlobalConstants.TAG_LANGUAGE);

		/**
		 * Algorithm implemented on <code>getIntermediaryCreationContext</code> is
		 * similar to the one of instantiation page. So a single PN/VA/site has to be
		 * provided for this service (internal implementation will fall back to find
		 * associated effectivity) <br>
		 * To keep information they are restored inside the creation context after
		 * processing
		 * 
		 */
		ContextScheduleCreationAssembly contextScheduleCreation = 
				getIntermediaryCreationContext(pns.get(0), vasForInstanciation, null, null, null, FORCED_ISOLATED, referential,
						langs.get(0),
						sites.get(0));

		contextScheduleCreation.setSite(FormatUtils.getStringValues(sites, " ").trim());
		contextScheduleCreation.setPartNumber(FormatUtils.getStringValues(pns, " ").trim());
		if( vas!=null && !vas.isEmpty()) {
			contextScheduleCreation.setConfiguration(FormatUtils.getStringValues(vas, " ").trim());
		}
		String variants = PublishedScheduleUtils.extractVariantListFromSchedules(boSynthesisPublication);
		contextScheduleCreation.setVariant(variants);
		contextScheduleCreation.setLanguage(FormatUtils.getStringValues(langs, " ").trim());
		
		return contextScheduleCreation;
	}
	
	@Override
	public ContextScheduleCreationAssembly getIntermediaryCreationContext(String articlePn, String va, 
			@Deprecated String family,
			@Deprecated String variant,
			@Deprecated String material,
			Boolean isolated, RepositoryType referential, String lang, String site)
			throws SearchCreationContextException {
		
		
		ContextUserAssemblyServer contextUser = new ContextUserAssemblyServer();
		setupContextUser(contextUser, referential, site);
		contextUser.setLanguage(lang);
		ContextScheduleCreationAssembly contextScheduleCreation =  setupContextCreation(referential, contextUser);

		/**
		 * This method follow the same logic of CreateScheduleAction JSP page
		 * 
		 */
		try {
			// JSP page: CreateScheduleAction.identificationSchedule()
			contextScheduleCreation.setContext(FORCED_CONTEXT);
			
			ScheduleCreationDTO scheduleDto = serviceIdentification.initParametersEffectivities(contextUser, contextScheduleCreation);
			contextScheduleCreation.setDirectoryScheduleType(scheduleDto.getScheduleGenericFile());
			contextScheduleCreation.setDirectorySchedulePath(scheduleDto.getScheduleDirectoryPath());
			
			// JSP page: CreateScheduleAction.updateParametersEffectivitiesFromPn()
			contextScheduleCreation.setPartNumber(articlePn);
			scheduleDto = serviceIdentification.updateEffectivitiesFromPN(contextUser, contextScheduleCreation);
			updateVariantFromContextSchedule(scheduleDto, contextScheduleCreation);
			
			// JSP page: CreateScheduleAction.searchEffectivity()
			// Only Assembly New schedules are managed for DVI
			// Assembly VA (variant) is not managed for schedule search
			// Otherwise same algo as MapperCreateSchedule.mapperApplicabilitiesForSearchEffectivities() must be implemented here
			contextScheduleCreation.setConfiguration(va);
			contextScheduleCreation.setExternalCreation(true);
			scheduleDto = serviceIntermediary.searchScheduleIntermediary(contextScheduleCreation, contextUser);
			// CreateScheduleAction.searchEffectivity()/MapperCreateSchedule.mapperParametersInstanciation()
			contextScheduleCreation.setInterventionType(FORCED_INTERVENTION_TYPE);
			contextScheduleCreation.setEffecivitiesFromMethods(scheduleDto.getMethodFromEffectivities());
			contextScheduleCreation.setEffectivity(scheduleDto.getEffectivityFileName());
			contextScheduleCreation.setIndustrialValidation(scheduleDto.getIndustrialValidation());
			contextScheduleCreation.setSapSchedule(scheduleDto.getIsSapSchedule());

			contextScheduleCreation.setMaterial(scheduleDto.getMaterial());
			contextScheduleCreation.setFamily(scheduleDto.getFamily());

			checkParamConsistency(variant, family, material, scheduleDto);
			
			List<String> methodsForm = BinderUtils.convertParametersToList(scheduleDto.getParameters().get(ModelGlobalConstants.TAG_METHODS));
			if(methodsForm!=null && !methodsForm.isEmpty()){
				contextScheduleCreation.setMethod(methodsForm.get(0));
				contextScheduleCreation.setVersion(scheduleDto.getMethodLastVersion().get(methodsForm.get(0)));
			} else {
				contextScheduleCreation.setVersion(scheduleDto.getLastVersionPublish());
			}
			
			// JSP page: CreateScheduleAction.validateFieldForInstantiateSchedule()
			contextScheduleCreation.setIsolatedModule(isolated);
			contextScheduleCreation.setInstances(FORCED_INSTANCES_NUMBER);
			contextScheduleCreation.setLevel(computeLevelsFromDto(scheduleDto.getParameters().get(ModelGlobalConstants.TAG_LEVELS)));
			
			File intermediaryFile = serviceIntermediary.buildScheduleIntermediary(contextScheduleCreation);
			if (intermediaryFile == null) {
				throw new CreateScheduleIntermediaryException(CreateScheduleIntermediaryException.EXCEPTION_SCHEDULE_NOT_FOUND);
			}
			contextScheduleCreation.setDirectorySchedulePath(intermediaryFile.getPath());
			
		}  catch (CreateScheduleIntermediaryException e) {
			throw new SearchCreationContextException(CreationContextStatusCode.ERROR_INTERMEDIARY_SCHEDULE_NOT_FOUND, e.getExceptionMessage());
		} catch (Exception e) {
			throw new SearchCreationContextException(CreationContextStatusCode.ERROR_INTERNAL, e.getMessage(), e);
		}
		return contextScheduleCreation;
	}

	private void checkParamConsistency(String variant, String family, String material,
			ScheduleCreationDTO scheduleDto) throws SearchCreationContextException {
		
		if( variant != null && !variant.isEmpty() && !variant.equals(scheduleDto.getVariant())) {
			SearchCreationContextException e =  new SearchCreationContextException(
					CreationContextStatusCode.ERROR_INTERMEDIARY_SCHEDULE_NOT_FOUND, "Provided variant (" + variant
							+ ") not consistent: expected one is " + scheduleDto.getVariant());
			logger.error("Unconsistency", e);
		}
		
		if( family != null && !family.isEmpty() &&  !family.equals(scheduleDto.getFamily())) {
			SearchCreationContextException e =  new SearchCreationContextException(
					CreationContextStatusCode.ERROR_INTERMEDIARY_SCHEDULE_NOT_FOUND, "Provided family (" + family
							+ ") not consistent: expected one is " + scheduleDto.getFamily());
			logger.error("Unconsistency", e);
		}
		
		if( material != null && !material.isEmpty() && !material.equals(scheduleDto.getMaterial())) {
			SearchCreationContextException e =  new SearchCreationContextException(
					CreationContextStatusCode.ERROR_INTERMEDIARY_SCHEDULE_NOT_FOUND, "Provided material (" + material
							+ ") not consistent: expected one is " + scheduleDto.getMaterial());
			logger.error("Unconsistency", e);
		}
	}

	/**
	 * Return the list of levels for the selected intermediary schedule
	 * <p>
	 * Technical note: <p> 
	 *  the dto has not a similar management according to synthesis used:
	 *  <li> When using XML synthesis, dto holds a list of the levels nodes
	 *  <li> when using BDD synthesis, dto holds a string
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Set<String> computeLevelsFromDto(List<?> levelsDto) {
		Set<String> convertedDtoList = null;
		if (levelsDto != null && !levelsDto.isEmpty()) {
			if( levelsDto.get(0) instanceof String) {
				convertedDtoList = new HashSet<String>((List<String>)levelsDto);
			} else {
				convertedDtoList = new HashSet<String>(levelsDto.size());
				for(Object level : levelsDto) {
					Node currentLevel = (Node)level;
					convertedDtoList.add(currentLevel.getNodeValue());
				}
			}
		}
		return convertedDtoList;
	}
	
	/***
	 * Set the correct variant in context parameter according to creation context
	 * and variant existing in current DTO
	 * <p>
	 * 
	 * Note that this algo is similar to server side one: <code>MapperCreateSchedule.buildVariantFromContextSchedule()</code>
	 * 
	 * @param dto
	 * @param context
	 */
	public static void updateVariantFromContextSchedule(ScheduleCreationDTO dto, ContextScheduleCreationAssembly context) {
		String variantResult = dto.getVariant();
		if(context.getContext().equals(AssemblyGlobalServices.SCHEDULE_ASSEMBLY_REPAIRED) 
				|| context.getScheduleType().equals(AssemblyGlobalServices.SCHEDULE_DISASSEMBLY_TYPE) ){
			if(!dto.getVariant().isEmpty()) {
				String [] variants  = new HashSet<String>(Arrays.asList(dto.getVariant().split(" "))).toArray(new String[0]);
				if( variants.length <= 1){
					variantResult = variants[0];
				}
			}
		} else {
			String[] listVariantsDto = dto.getVariant().split(" ");
			variantResult = FormatUtils.convertListString(StringUtil.removeDuplicateValue(listVariantsDto));
		}
		context.setVariant(variantResult);
	}
	
	/**
	 * Initialize a creation context suitable for DVI services
	 * 
	 * @return
	 */
	public ContextScheduleCreationAssembly setupContextCreation(RepositoryType referential, ContextUserAssemblyServer contextUser) {
		ContextScheduleCreationAssembly contextScheduleCreation =  new ContextScheduleCreationAssembly();
		contextUser.setRepository((AssemblyRepositoryConfig) getRepositoryFromReferential(referential.toString()));
		contextScheduleCreation.setScheduleType(FORCED_SCHEDULE_TYPE);
		contextScheduleCreation.setAffair(FORCED_AFFAIR);
		contextScheduleCreation.setOrder(FORCED_ORDER);
		return contextScheduleCreation;
	}
}
