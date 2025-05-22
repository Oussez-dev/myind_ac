package turbomeca.gamme.assembly.services.constants;

import turbomeca.gamme.ecran.services.constants.XsltConstantsType;
import turbomeca.gamme.ecran.services.constants.XsltPaths;

/**
 * Enumeration for constants used as XSLT element in Assembly JAVA-side.
 * 
 * An XSLT constant can be a parameter or a root stylesheet (treatment, modal or
 * generic).
 * 
 * @author ademartin
 *
 */
public enum AssemblyXsltConstants {

	/** XSLT parameters */
	XSLT_PARAMETER_URL_IMAGES_REVERSE("urlImageReverse", XsltConstantsType.PARAMETER),
	XSLT_PARAMETER_HMI_CONTEXT("hmiContext", XsltConstantsType.PARAMETER),
	XSLT_PARAMETER_SHEDULE_CONTEXT("scheduleContext", XsltConstantsType.PARAMETER),
	XSLT_PARAMETER_MARK_ID("markId", XsltConstantsType.PARAMETER),
	XSLT_PARAMETER_INSTANCE_ID("instanceId", XsltConstantsType.PARAMETER),
	XSLT_PARAMETER_ALTERNATIVE_ID("alternativeId", XsltConstantsType.PARAMETER),
	XSLT_PARAMETER_PASSING_ID("passingId", XsltConstantsType.PARAMETER),
	XSLT_PARAMETER_LEVEL("level", XsltConstantsType.PARAMETER),
	XSLT_PARAMETER_SUBPHASE_REVERSE("reverseSubPhase", XsltConstantsType.PARAMETER),
	XSLT_PARAMETER_REVERSE_SCHEDULE_VERSION("reverseVersion", XsltConstantsType.PARAMETER),
	XSLT_PARAMETER_CURRENT_NODE_NAME_ID("currentNodeName", XsltConstantsType.PARAMETER),
	XSLT_PARAMETER_NEW_PASSING_COMMENT("newPassingComment", XsltConstantsType.PARAMETER),
	XSLT_PARAMETER_NEW_PASSING_USER("newPassingUser", XsltConstantsType.PARAMETER),
	XSLT_PARAMETER_NEW_PASSING_DATE("newPassingDate", XsltConstantsType.PARAMETER),
	XSLT_PARAMETER_IS_SHOWN_AT_OPENING("isShownAtOpening", XsltConstantsType.PARAMETER),
	XSLT_PARAMETER_MULTI_INSTANCES("multiInstances", XsltConstantsType.PARAMETER),
	XSLT_PARAMETER_INSTANCES("instances", XsltConstantsType.PARAMETER),
	XSLT_PARAMETER_CONTENT("content", XsltConstantsType.PARAMETER),
	XSLT_PARAMETER_TOOL_ID("toolId", XsltConstantsType.PARAMETER),
    
	/** XSLT Pages */
	XSLT_SCHEDULE(AssemblyXsltPaths.XSLT_PATH_PAGE + "schedule.xsl", XsltConstantsType.STYLESHEET),
	XSLT_UPDATE(AssemblyXsltPaths.XSLT_PATH_PAGE + "update.xsl", XsltConstantsType.STYLESHEET),
	XSLT_TASKS_VIEW(AssemblyXsltPaths.XSLT_PATH_PAGE + "tasksView.xsl", XsltConstantsType.STYLESHEET),
	
	/** XSLT Modal Windows */
	XSLT_INFORMATION_IDENTIFICATION(AssemblyXsltPaths.XSLT_PATH_MODAL + "information_schedule_identification.xsl", XsltConstantsType.STYLESHEET_MODAL),
	XSLT_INFORMATION_COMPOUND(AssemblyXsltPaths.XSLT_PATH_MODAL + "information_schedule_compounds.xsl", XsltConstantsType.STYLESHEET_MODAL),
	XSLT_INTERVENTION_CUSTOMIZE_LEVEL(AssemblyXsltPaths.XSLT_PATH_MODAL + "intervention_customize_level.xsl", XsltConstantsType.STYLESHEET_MODAL),
	XSLT_INTERVENTION_MANUAL_LEVEL_INSTRUCTION(AssemblyXsltPaths.XSLT_PATH_MODAL + "intervention_select_level_instruction.xsl", XsltConstantsType.STYLESHEET_MODAL),
	XSLT_INTERVENTION_CHANGE_MARK(AssemblyXsltPaths.XSLT_PATH_MODAL + "intervention_change_mark.xsl", XsltConstantsType.STYLESHEET_MODAL),
	XSLT_INTERVENTION_CHANGE_INPUT_CHOICE_VALUE_MANUAL(AssemblyXsltPaths.XSLT_PATH_MODAL + "intervention_change_inputchoice_manual.xsl", XsltConstantsType.STYLESHEET_MODAL),
	XSLT_INTERVENTION_DIRTY_VIEW(AssemblyXsltPaths.XSLT_PATH_MODAL + "intervention_dirty_view.xsl", XsltConstantsType.STYLESHEET_MODAL),
	XSLT_INFORMATION_SYNTHESIS(AssemblyXsltPaths.XSLT_PATH_MODAL + "information_synthesis.xsl", XsltConstantsType.STYLESHEET_MODAL),
	XSLT_INFORMATION_SYNTHESIS_MARKS(AssemblyXsltPaths.XSLT_PATH_MODAL + "information_synthesis_marks.xsl", XsltConstantsType.STYLESHEET_MODAL),
	XSLT_INFORMATION_SYNTHESIS_TOOLS(AssemblyXsltPaths.XSLT_PATH_MODAL + "information_synthesis_tools.xsl", XsltConstantsType.STYLESHEET_MODAL),
	XSLT_INFORMATION_SUMMARY(AssemblyXsltPaths.XSLT_PATH_MODAL + "information_summary.xsl", XsltConstantsType.STYLESHEET_MODAL),
	XSLT_INFORMATION_SYNTHESIS_NOTIFICATIONS(AssemblyXsltPaths.XSLT_PATH_MODAL + "information_synthesis_notifications.xsl", XsltConstantsType.STYLESHEET_MODAL),
	XSLT_INFORMATION_SYNTHESIS_DOCUMENTS(AssemblyXsltPaths.XSLT_PATH_MODAL + "information_synthesis_documents.xsl", XsltConstantsType.STYLESHEET_MODAL),
	XSLT_INFORMATION_OBSERVATIONS(AssemblyXsltPaths.XSLT_PATH_MODAL + "information_observations.xsl", XsltConstantsType.STYLESHEET_MODAL),
	XSLT_SAP_SYNTHESIS(AssemblyXsltPaths.XSLT_PATH_MODAL + "synthesis_sap.xsl", XsltConstantsType.STYLESHEET_MODAL),
	XSLT_EDITION_SUBPHASES(AssemblyXsltPaths.XSLT_PATH_MODAL + "edition_subphases.xsl", XsltConstantsType.STYLESHEET_MODAL),
	XSLT_INTERVENTION_REWORK_DYNAMIC_LEVEL_SELECTION(AssemblyXsltPaths.XSLT_PATH_MODAL + "intervention_rework_dynamic_level_selection.xsl", XsltConstantsType.STYLESHEET_MODAL),
	XSLT_INTERVENTION_REWORK_DYNAMIC_MANUAL_SELECTION(AssemblyXsltPaths.XSLT_PATH_MODAL + "intervention_rework_dynamic_manual_selection.xsl", XsltConstantsType.STYLESHEET_MODAL),
	XSLT_INTERVENTION_DEROGATION_MARK(AssemblyXsltPaths.XSLT_PATH_MODAL + "intervention_derogation_mark.xsl", XsltConstantsType.STYLESHEET_MODAL),
	XSLT_INFORMATION_DIFFERENCES(AssemblyXsltPaths.XSLT_PATH_MODAL + "information_schedule_differences.xsl", XsltConstantsType.STYLESHEET_MODAL),
	XSLT_INFORMATION_NEW_PASSING(AssemblyXsltPaths.XSLT_PATH_MODAL + "information_schedule_new_passing.xsl", XsltConstantsType.STYLESHEET_MODAL),
	XSLT_INSTRUCTION_ADD_COMMENT_NON_APPLICABLE_SUBPHASE(AssemblyXsltPaths.XSLT_PATH_MODAL + "instruction_add_comment_non_applicable_subphase.xsl", XsltConstantsType.STYLESHEET_MODAL),
	XSLT_PILOTING_TOOL_ACQUIRE(AssemblyXsltPaths.XSLT_PATH_MODAL + "piloting_tool_modal.xsl", XsltConstantsType.STYLESHEET_MODAL),
	/** XSLT PDF stylesheets */
	XSLT_GAMME_PDF_GEE(AssemblyXsltPaths.XSLT_PATH_PDF + "schedule.xsl", XsltConstantsType.STYLESHEET_PDF),
	XSLT_GAMME_PDF_SYNTHESIS_GEE(AssemblyXsltPaths.XSLT_PATH_PDF + "synthesis.xsl", XsltConstantsType.STYLESHEET_PDF),
	XSLT_GAMME_PDF_INDICATRICE_PLATE_GEE(AssemblyXsltPaths.XSLT_PATH_PDF + "indicatrice_plate.xsl", XsltConstantsType.STYLESHEET_PDF),
	XSLT_GAMME_PDF_TRACEABILITY_BOOK(AssemblyXsltPaths.XSLT_PATH_PDF + "traceability_book.xsl", XsltConstantsType.STYLESHEET_PDF),
	XSLT_GAMME_PDF_PVRI(AssemblyXsltPaths.XSLT_PATH_PDF + "pvri.xsl", XsltConstantsType.STYLESHEET_PDF),
	
	/** XSLT Creation stylesheets */
	XSLT_CREATE_RANGE(AssemblyXsltPaths.XSLT_PATH_CREATION + "instanciated/createRange.xsl", XsltConstantsType.STYLESHEET_CREATION);
	
    /** The path value for a XSLT constant */
	private final String value;
	
	/** The XSLT constant type (parameter, stylesheet, modal stylesheet or treatment stylesheet)*/
	private final XsltConstantsType type;
	
	/** Constructor */
	private AssemblyXsltConstants(String value, XsltConstantsType type) {
		this.value = value;
		this.type = type;
	}
	
	public String value() {
		return this.value;
	}
	
	public XsltConstantsType type() {
		return this.type;
	}
}
