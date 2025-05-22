package turbomeca.gamme.assembly.services.schedule.creation;

import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import turbomeca.gamme.assembly.services.constants.AssemblyXsltConstants;
import turbomeca.gamme.assembly.services.schedule.creation.bean.ContextScheduleCreationAssembly;
import turbomeca.gamme.assembly.services.schedule.creation.utils.AssemblyGlobalServices;
import turbomeca.gamme.assembly.services.schedule.creation.utils.FileAssemblyUtils;
import turbomeca.gamme.ecran.services.common.utils.misc.FormatUtils;
import turbomeca.gamme.ecran.services.publication.utils.IFolderService;
import turbomeca.gamme.ecran.services.schedule.bean.ContextScheduleCreation;
import turbomeca.gamme.ecran.services.schedule.bean.ContextUserServer;
import turbomeca.gamme.ecran.services.schedule.constants.Globals;
import turbomeca.gamme.ecran.services.schedule.service.creation.ATransformationXSLTService;



@Service
public class AssemblyTransformationXSLTService extends  ATransformationXSLTService {
	
	@Autowired
	private IFolderService folderService;
	
	public AssemblyTransformationXSLTService() {
		super(AssemblyXsltConstants.XSLT_CREATE_RANGE.value());
	}

	@Override
	protected void addParameters(ContextUserServer contextuser,
			ContextScheduleCreation contextCreation) {
		
		ContextScheduleCreationAssembly contextCreationdAssembly = (ContextScheduleCreationAssembly) contextCreation;
		
		SimpleDateFormat dateFormat = new SimpleDateFormat(Globals.RANGE_DATE_FORMAT_FILENAME);
		String dateString = dateFormat.format(contextCreationdAssembly.getDateCreation().getTime());
		/**
		 * The site value overridden on the context creation supersede the one defined by the user
		 */
		String site = contextCreationdAssembly.getSite();
		if( site == null) {
			site = contextuser.getSite();
		}
		modelTransformer.addParameter(Globals.RANGE_PARAM_XSL_SITE, site);
		modelTransformer.addParameter(Globals.RANGE_PARAM_XSL_METHOD, contextCreationdAssembly.getMethod());
		modelTransformer.addParameter(Globals.RANGE_PARAM_XSL_LANGUAGE, contextCreationdAssembly.getLanguage());
		modelTransformer.addParameter(Globals.RANGE_PARAM_XSL_MATERIAL, contextCreationdAssembly.getMaterial());
		modelTransformer.addParameter(AssemblyGlobalServices.RANGE_PARAM_XSL_LEVEL, FormatUtils.convertListString(contextCreationdAssembly.getLevelAsArray()));
		modelTransformer.addParameter(Globals.RANGE_PARAM_XSL_INTERVENTION_TYPE, contextCreationdAssembly.getInterventionType() == null ? "none" : contextCreationdAssembly.getInterventionType());
		modelTransformer.addParameter(AssemblyGlobalServices.RANGE_PARAM_XSL_APPLICABILITIES_MATERIAL, contextCreationdAssembly.getApplicabilityMaterial());
		modelTransformer.addParameter(AssemblyGlobalServices.RANGE_PARAM_XSL_ISOLATED_MODULE, contextCreationdAssembly.isIsolateModule());
		modelTransformer.addParameter(Globals.RANGE_PARAM_XSL_VARIANT, contextCreationdAssembly.getVariant());
		modelTransformer.addParameter(Globals.RANGE_PARAM_XSL_ORDER, contextCreationdAssembly.getOrder());
		modelTransformer.addParameter(Globals.RANGE_PARAM_XSL_AFFAIR, contextCreationdAssembly.getAffair());
		modelTransformer.addParameter(Globals.RANGE_PARAM_XSL_PN, contextCreationdAssembly.getPartNumber());
		modelTransformer.addParameter(Globals.RANGE_PARAM_XSL_INSTANTIATION_VERSION, contextCreationdAssembly.getInstantiationVersion());
		/**
		 * When not set, isIsolateModule and  isFictiveEngine shall not be applied as applicability filter
		 */
		String isolatedModuleParam = "";
		if(contextCreationdAssembly.isIsolateModule()!=null ) {
			isolatedModuleParam = Boolean.toString(contextCreationdAssembly.isIsolateModule());
		}
		String fictiveEngineParam = "";
		if(contextCreationdAssembly.isFictiveEngine()!=null ) {
			fictiveEngineParam = Boolean.toString(contextCreationdAssembly.isFictiveEngine());
		}
		modelTransformer.addParameter(AssemblyGlobalServices.RANGE_PARAM_XSL_FICTIF_ENGINE, fictiveEngineParam);
		modelTransformer.addParameter(AssemblyGlobalServices.RANGE_PARAM_XSL_ISOLATED_MODULE, isolatedModuleParam);
		modelTransformer.addParameter(Globals.RANGE_PARAM_XSL_INSTANCES, contextCreationdAssembly.getInstances());
		modelTransformer.addParameter(AssemblyGlobalServices.RANGE_PARAM_XSL_CONTEXT, contextCreationdAssembly.getContext());
		if (contextCreationdAssembly.getScheduleType().equals("assembly")
				&& contextCreationdAssembly.getContext().equals("new")) {
			modelTransformer.addParameter(AssemblyGlobalServices.RANGE_PARAM_XSL_VAS,
					contextCreationdAssembly.getConfiguration());
		} else {
			modelTransformer.addParameter(Globals.RANGE_PARAM_XSL_MODIFICATIONS,
					contextCreationdAssembly.getConfiguration());
		}
		modelTransformer.addParameter(Globals.RANGE_PARAM_XSL_CURRENT_TIME_STAMP, dateString);
		modelTransformer.addParameter(Globals.RANGE_PARAM_XSL_USER, contextuser.getName());
		modelTransformer.addParameter(Globals.RANGE_PARAM_XSL_LOGIN, contextuser.getLogin());
		modelTransformer.addParameter(Globals.RANGE_PARAM_XSL_SCHEDULE_DATABASE_ID, contextCreationdAssembly.getScheduleDatabaseId());
		modelTransformer.addParameter(Globals.RANGE_PARAM_XSL_IS_CREATE_CHECKBOX_TASK_MARK, contextCreationdAssembly.isCreateCheckboxTaskMark());
		modelTransformer.addParameter(Globals.RANGE_PARAM_XSL_KEEP_FULL_HISTORY, getKeepFullHistory());
	}


	@Override
	protected String getInstantiatedXmlResultPath(ContextUserServer contextUser, ContextScheduleCreation contextCreation) {
		return FileAssemblyUtils.buildInstanciatedPath(contextUser, contextCreation);
	}
}