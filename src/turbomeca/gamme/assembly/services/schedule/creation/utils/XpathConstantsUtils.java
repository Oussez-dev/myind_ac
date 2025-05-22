package turbomeca.gamme.assembly.services.schedule.creation.utils;

public class XpathConstantsUtils {
	
	public static final String XPATH_SEARCH_RETURN_CAUSES = "./parameters/returnCauses/returnCause/@returnCause";
	public static final String XPATH_SEARCH_VARIANT = "//parameters[languages/language/@language='%s' and sites/site/@site='%s']/effectivities/effectivity/effectivityVariant[pns/pn='%s']/@variant";
	public static final String XPATH_SEARCH_EFFECTIVITIES = "/*/parameters/effectivities";
	public static final String XPATH_SEARCH_EFFECTIVITY_VARIANT = "./parameters/effectivities/effectivity/effectivityVariant";
	public static final String XPATH_SEARCH_PN_FROM_EFFECTIVITY_VARIANT = "./pns/pn";
	public static final String XPATH_SEARCH_LEVEL = "./parameters/levels/level/@level";
	public static final String XPATH_SEARCH_METHOD = "./parameters/methods/method/@method";
	public static final String XPATH_SEARCH_INTERVENTION = "./parameters/interventionTypes/interventionType/@interventionType";
	public static final String XPATH_SEARCH_PN = "//parameters[languages/language/@language='%s' and sites/site/@site='%s']/effectivities/effectivity/effectivityVariant/pns/pn";
	public static final String XPATH_SEARCH_IS_SAP_SCHEDULE = "./parameters/isSapSchedule/@isSapSchedule";
	
	public static final String XPATH_SEARCH_VA = "//parameters[languages/language/@language='%s' and sites/site/@site='%s']/effectivities/effectivity/effectivityVariant[pns/pn='%s']/articles/va";
	public static final String XPATH_SEARCH_MODIFICATION = "//parameters[languages/language/@language='%s' and sites/site/@site='%s']/effectivities/effectivity/effectivityVariant[pns/pn='%s']/modifications/modification";
	public static final String XPATH_EXTERNAL_CREATION_NEW ="publicationSynthesis/entity[parameters/languages/language/@language='%s' and parameters/sites/site/@site='%s' and parameters/effectivities/effectivity/effectivityVariant/pns/pn='%s']"; 
	public static final String XPATH_SEARCH_INSTANCES = "./@instanceNumber";
	public static final String XPATH_ASSEMBLY_NEW ="publicationSynthesis/entity[parameters/languages/language/@language='%s' and parameters/sites/site/@site='%s' and parameters/effectivities/effectivity/effectivityVariant/pns/pn='%s' and parameters/effectivities/effectivity/effectivityVariant/articles/va='%s']"; 
	public static final String XPATH_ASSEMBLY_DISASSEMBLY = "publicationSynthesis/entity[parameters[languages/language/@language='%s' and sites/site/@site='%s' and effectivities/effectivity/effectivityVariant[@variant[matches(.,concat('(\\s|^)','%s','(\\s|$)'))] and pns/pn='%s' and (modifications[((not(@andMode) or @andMode='false') and ";
	public static final String XPATH_OUT_MODIFICATION = "publicationSynthesis/entity[parameters[languages/language/@language='%s' and sites/site/@site='%s' and  effectivities/effectivity/effectivityVariant[@variant[matches(.,concat('(\\s|^)','%s','(\\s|$)'))] and pns/pn='%s' and modifications/@out='true']]]";
	public static final String XPATH_COUNT_MODIFICATION_AND_MODE = "and count(./modification)=%s";
	public static final String XPATH_COUNT_MODIFICATION_OR_MODE = "and '%s'='1'";
	public static final String XPATH_END_DYNAMIC_MODIFICATION="))";
	public static final String XPATH_END_XPATH="])]]]";
	public static final String XPATH_CONDITION_AND_MODE="[@andMode and @andMode='true']";
	public static final String XPATH_CONDITION_NO_AND_MODE="[not(@andMode) or @andMode='false']";
	public static final String XPATH_AND_OPERATOR="and";
	public static final String XPATH_OR_OPERATOR="or";
	
	public static final String XPATH_SETTING_BENCH_STATUS_KO = "//Evaluation_du_Resultat[text()='ERROR']";
	
	
}
