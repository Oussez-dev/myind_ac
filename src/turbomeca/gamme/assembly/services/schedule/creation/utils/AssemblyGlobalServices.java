package turbomeca.gamme.assembly.services.schedule.creation.utils;

public class AssemblyGlobalServices {
	
	
	public static final String VERSION_APPLICATION = "v4.32";

	
	/** Exceptions **/
	public static final int EXCEPTION_INSTANTIATE_SCHEDULE = 0;
	public static final int EXCEPTION_NO_FAMILY_FOUND = 1;
	public static final int EXCEPTION_NO_PN_FOUND= 7;
	public static final int EXCEPTION_EFFECTIVITY_MORE_THAN_ONE_FOUND = 8;
	public static final int EXCEPTION_NO_RIGHT_TO_CREATE_SCHEDULE = 9;
	
	/** XSLT parameters for schedule creation*/
    public static final String RANGE_PARAM_XSL_LEVEL = "param_level";
    public static final String RANGE_PARAM_XSL_PN = "param_pn";
    public static final String RANGE_PARAM_XSL_VAS = "param_vas";
    public static final String RANGE_PARAM_XSL_FICTIF_ENGINE = "param_fictiveEngine";
    public static final String RANGE_PARAM_XSL_ISOLATED_MODULE = "param_isolatedModule";
    public static final String RANGE_PARAM_XSL_INSTANCES="param_instances";
	public static final String RANGE_PARAM_XSL_APPLICABILITIES_MATERIAL = "param_applicabilities_material";
	public static final String RANGE_PARAM_XSL_CONTEXT = "param_context";
	public static final String PARAMETER_METHOD="method";

	/**
     * Property Message Assembly
     */
	public static final String PROPERTY_INFO_USER_CONNECTED = "info.user.connected";
	public static final String PROPERTY_INFO_NO_RANGE_FOUND = "info.no.range.found";
	public static final String PROPERTY_INFO_RANGE_CLOSED = "info.range.closed";
	public static final String PROPERTY_INFO_RANGE_FREE = "info.range.free";
	public static final String PROPERTY_INFO_RANGE_CREATION_IN_PROGRESS = "info.range.create.inProgress";
	public static final String PROPERTY_ERROR_RANGE_INSPECTION_VALIDATION = "error.range.inspection.validation";
	public static final String PROPERTY_ERROR_RANGE_INSPECTION_LOADING = "error.range.inspection.loading";
	public static final String PROPERTY_ERROR_RANGE_INSPECTION_FILE_NOT_FOUND = "error.range.inspection.file.not.found";
	public static final String PROPERTY_ERROR_RANGE_INSPECTION_LOCKED = "error.range.inspection.locked";
	public static final String PROPERTY_ERROR_RANGE_INSPECTION_UNKNOWN = "error.range.inspection.load.unknwown";
	public static final String PROPERTY_ERROR_RANGE_SUMMARY_VALIDATION = "error.range.summary.validation";
	public static final String PROPERTY_ERROR_RANGE_SUMMARY_LOADING = "error.range.summary.loading";
	public static final String PROPERTY_ERROR_RANGE_SUMMARY_FILE_NOT_FOUND = "error.range.summary.file.not.found";
	public static final String PROPERTY_ERROR_RANGE_SUMMARY_LOCKED = "error.range.summary.locked";
	public static final String PROPERTY_ERROR_RANGE_SUMMARY_UNKNOWN = "error.range.summary.load.unknwown";
	public static final String PROPERTY_ERROR_LOAD_RANGE_SUMMARY = "error.load.range.summary";
	public static final String PROPERTY_ERROR_LAUNCH_APPLICATION = "error.launch.application";
	public static final String PROPERTY_ERROR_UNLOCK_RANGE = "error.unlock.range";
	public static final String PROPERTY_ERROR_CLOSE_RANGE = "error.close.range";
	public static final String PROPERTY_ERROR_ADMIN_DELETE_RANGE = "error.admin.delete.range";
	public static final String PROPERTY_ERROR_SAVE_PN_SN = "error.save.pn.sn";
	public static final String PROPERTY_ERROR_UNICITY_PN_SN = "error.unicity.pn.sn";
	public static final String PROPERTY_ERROR_ADD_ITEM = "error.add.items.research";
	public static final String PROPERTY_ERROR_OPEN_INSPECTION_DETAILS = "error.open.inspection.details";
	public static final String PROPERTY_ERROR_SYNCHRONISE = "error.synchronise.inspection.details";
	public static final String PROPERTY_ERROR_USER_UNKNOWN = "error.user.authentification.failed";
	public static final String ERROR_KEY_MESSAGE_TYPE_HEADER = "error.type.header";
	public static final String ERROR_KEY_MESSAGE_TYPE_SEARCH_RANGE_INTERMEDIARY = "error.type.searchIntermediary";
	public static final String MESSAGE_KEY_CREATING= "message.schedule.creating";
	public static final String MESSAGE_KEY_DEBRANCH_SCHEDULE= "message.debranch.schedule";
	public static final String MESSAGE_KEY_CREATED_SUCESS= "message.schedule.created.succes";
	public static final String PROPERTY_ERROR_SEARCH_GENERIC = "error.type.searchGeneric";
	public static final String PROPERTY_ERROR_DATA_MISSING_EFFECTIVITIES = "error.data.missing.effectivities";
	public static final String PROPERTY_ERROR_DATA_RETRIEVE_APPLICABILITIES_FROM_PN = "error.retrieve.applicabilities.from.pn";
	public static final String PROPERTY_ERROR_DATA_MISSING_GENERIC_FILE_EFFECTIVITIES = "error.data.missing.generic.file.effectivities";
	public static final String PROPERTY_ERROR_MORE_THAT_ONE_EFFECTIVITY_FOUND= "error.more.that.one.effectivity.found";
	public static final String PROPERTY_ERROR_NO_PN_FOUND = "error.data.no.pn.found";
	public static final String PROPERTY_INFO_ARCHIVE_SUCCESS = "info.range.archive.success";
	public static final String PROPERTY_ERROR_ARCHIVE_FAILURE = "error.range.archive.failure";
	public static final String PROPERTY_ERROR_CREATE_SCHEDULE = "error.createRange";
	public static final String PROPERTY_ERROR_ORDER_ALPHANUMERIC="error.order.alphanumeric";
	public static final String PROPERTY_ERROR_AFFAIR_ALPHANUMERIC="error.affair.alphanumeric";
	public static final String PROPERTY_ERROR_CREATE_SCHEDULE_PERMISSIONS = "error.createRange.no.permissions";
	public static final String ERROR_NO_EFFECTIVITY_FOUND= "error.no.effectivity.found";
	public static final String ERROR_NO_FAMILY_FOUND= "error.no.family.found";
	public static final String ERROR_NO_MATERIALS_FOUND= "error.no.materials.found";
	public static final String ERROR_SEARCH_MATERIAL_FROM_FAMILY= "error.search.material.from.family";
	public static final String ERROR_NO_RANGE_FOUND= "error.no.range.found";
	public static final String ERROR_CREATE_ORDER_ALREADY_EXISTS ="error.createRange.order.already.exists";
	public static final String ERROR_CREATE_NO_PERMISSION = "error.create.no.permissions";
	public static final String ERROR_SEARCH_SCHEDULE_INTERMEDIARY ="error.createRange.searchIntermediary.file";
	public static final String ERROR_SEARCH_EFFECTIVITY ="error.search.effectivity";
	public static final String ERROR_MAIL_SEND= "error.mail.send";
	public static final String ERROR_ORDER_NOT_EXIST_IN_SAP ="error.order.not.exist.in.sap";
	public static final String ERROR_RETRIEVE_INFORMATIONS_FROM_SAP = "error.retrieve.informations.from.sap";
	public static final String PROPERTY_INFO_EFFECTIVITIES_FOUND ="message.inform.effectivity.found";
	public static final String PROPERTY_ERROR_ELECTRONIC_INIT_NOTIFICATION = "error.validation.init.electronic.notification";
	public static final String PROPERTY_ERROR_ELECTRONIC_NOTIFICATION = "error.validation.electronic.notification";
	public static final String PROPERTY_INFO_NO_ELECTRONIC_FOUND = "info.no.electronic.found";
	public static final String PROPERTY_MESSAGE_SUCCESS_ELECTRONIC_NOTIFICATION = "info.success.electronic.notification";
	public static final String PROPERTY_ERROR_ELECTRONIC_INIT_ELECTRONIC_VALIDATIONS = "error.init.validation.electronic";
	public static final String PROPERTY_ERROR_ELECTRONIC_VALIDATION = "error.validation.electronic";
	public static final String PROPERTY_MESSAGE_SUCCESS_ELECTRONIC_VALIDATION = "info.no.electronic.found";
	public static final String PROPERTY_INFO_NO_VALIDATIONS_FOUND = "info.no.electronic.validation.found";
	public static final String PROPERTY_INFO_SYNCHRONIZE_PROGRESS = "info.synchronize.schedule.inProgress";
	public static final String PROPERTY_INFO_SYNCHRONIZE_SUCCESS = "info.synchronize.schedule.finish";
	public static final String PROPERTY_INFO_NO_SCHEDULE_FOUND_OFFICIAL = "info.synchronize.no.schedule.found.official";
	public static final String PROPERTY_INFO_FOLDER_NOT_FOUND_OFFICIAL = "info.synchronize.folder.not.found.official";
	public static final String PROPERTY_INFO_NO_SCHEDULE_FOUND_OFFICIAL_TEST = "info.synchronize.no.schedule.found.official.test";
	public static final String PROPERTY_INFO_FOLDER_NOT_FOUND_OFFICIAL_TEST = "info.synchronize.folder.not.found.official.test";
	
	/**
	 * Error Message Application
	 */
	public static final String ERROR_INTERNAL = "error.internal";
	public static final String ERROR_COMMUNICATION_SERVER = "error.communication.server";
	public static final String ERROR_CONFIGURATION_XSLT_SERVER = "error.configuration.xslt.creation";
	public static final String ERROR_LOAD_USER = "error.load.user";
	public static final String ERROR_LOAD_SCHEDULE = "error.load.schedule";
	public static final String ERROR_SEARCH_SCHEDULE_SYNTHESIS = "error.search.file.synthesis";
	public static final String ERROR_READ_SCHEDULE_SYNTHESIS = "error.read.file.synthesis";
	
	/**
	 * Fields Required
	 */
	public static final String PROPERTY_ERROR_CREATE_SCHEDULE_ORDER_REQUIRED="error.createRange.order.required";
	public static final String PROPERTY_ERROR_CREATE_SCHEDULE_CONTEXT_REQUIRED="error.createRange.context.required";
	public static final String PROPERTY_ERROR_CREATE_SCHEDULE_INSTANCES_BAD_FORMAT="error.createRange.instances.bad.format";
	public static final String PROPERTY_ERROR_FAMILY_REQUIRED = "error.createRange.family.required";
	public static final String PROPERTY_ERROR_MATERIAL_REQUIRED = "error.createRange.material.required";
	public static final String PROPERTY_ERROR_VARIANT_REQUIRED = "error.createRange.variant.required";
	public static final String PROPERTY_ERROR_METHOD_REQUIRED = "error.createRange.method.required";
	public static final String PROPERTY_ERROR_LEVEL_REQUIRED= "error.createRange.level.required";
	public static final String PROPERTY_ERROR_VA_REQUIRED = "error.createRange.va.required";
	public static final String PROPERTY_ERROR_MODIFICATION_REQUIRED = "error.createRange.modification.required";
	public static final String PROPERTY_ERROR_INTERVENTION_REQUIRED = "error.createRange.intervention.required";
	public static final String PROPERTY_ERROR_PN_REQUIRED = "error.createRange.pn.required";
	/**
	 * Struts Action Servlet
	 */
	public static final String ACTION_STATUS_SUCCESS = "success";
	public static final String ACTION_STATUS_ALL_SUCCESS = "successAll";
	public static final String ACTION_STATUS_INIT_ERROR = "initError";
	public static final String ACTION_STATUS_INIT_WINDOWS = "initWindows";
	public static final String ACTION_ELECTRONIC_NOTIFICATION = "showNotification";
	public static final String ACTION_INSTANCIATE_SCHEDULE = "instanciateSchedule";
	public static final String ACTION_STATUS_END_INSTANCIATION = "endInstanciation";
	public static final String ACTION_STATUS_DEBRANCH_ASSEMBLY = "debranchAssembly";
	public static final String ACTION_STATUS_SEARCH_INTERMEDIARY = "searchScheduleIntermediary";
	public static final String ACTION_STATUS_ERROR_SEARCH_EFFECTIVITY_INSTANCIATION = "errorSearchEffectivity";
	public static final String ACTION_STATUS_ERROR_INSTANCIATION = "errorInstanciation";
	public static final String ACTION_STATUS_WINDOWS_APPLICABILITIES ="showWindowsApplicabilities";
	public static final String ACTION_STATUS_SHOW_USER_LOCKED ="showUserLocked";
	public static final String ACTION_STATUS_FAILURE = "failure";
	public static final String ACTION_STATUS_CANCEL = "cancel";
	public static final String ACTION_STATUS_ERROR = "error";
	public static final String ACTION_STATUS_RELOAD = "reload";
	public static final String ACTION_STATUS_RELOAD_ALL = "reload_all";
	public static final String ACTION_STATUS_HOME = "home";
	public static final String ACTION_STATUS_OPEN = "open";
	public static final String ACTION_STATUS_ANSWER_PAGE = "answer";
	public static final String ACTION_STATUS_DECONNEXION = "deconnexion";
	public static final String ACTION_STATUS_UNLOCK_SUCCESS = "unlockSuccess";
	public static final String ACTION_STATUS_CLEAR_SUCCESS = "clearSuccess";
	public static final String ACTION_STATUS_CHANGE_SUCCESS = "changeStatusSuccess";
	public static final String ACTION_STATUS_ARCHIVE_SUCCESS = "archiveSuccess";
	public static final String ACTION_STATUS_OPEN_LAUNCH = "launch";
	public static final String ACTION_STATUS_OPEN_SYNCHRONISE = "synchronise";
	public static final String ACTION_STATUS_OPEN_PROCCESSES = "open";
	public static final String ACTION_STATUS_ENTER_COMMENT_PROCCESSES = "showModalComment";
	public static final String ACTION_STATUS_COOKIE = "cookie";
	
	
	
	
	
	/**
	 * Specific constants Assembly
	 */
	public static final String SCHEDULE_ASSEMBLY_TYPE="assembly";
	public static final String SCHEDULE_DISASSEMBLY_TYPE="disassembly";
	public static final String SCHEDULE_ASSEMBLY_NEW="new";
	public static final String SCHEDULE_ASSEMBLY_REPAIRED="repaired";
	
	public static final String SCHEDULE_DOCTYPE_ASSEMBLY_REPAIRED="assembly_repaired";
	public static final String SCHEDULE_DOCTYPE_ASSEMBLY_NEW="assembly_new";
	public static final String SCHEDULE_DOCTYPE_DISASSEMBLY="disassembly";
	
	
	
	public static final int SCHEDULE_IDENTIFIER_ASSEMBLY_TYPE = 3;
	public static final int SCHEDULE_IDENTIFIER_DISASSEMBLY_TYPE = 4;
	
}
