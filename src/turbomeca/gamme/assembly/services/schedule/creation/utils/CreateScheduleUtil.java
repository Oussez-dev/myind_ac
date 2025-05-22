package turbomeca.gamme.assembly.services.schedule.creation.utils;

import java.util.HashMap;
import java.util.Map;

import turbomeca.gamme.assembly.services.schedule.creation.bean.ContextScheduleCreationAssembly;
import turbomeca.gamme.ecran.services.common.constants.ConstantsServer;
import turbomeca.gamme.ecran.services.common.utils.transformer.IXslTransformation;
import turbomeca.gamme.ecran.services.constants.XsltConstants;
import turbomeca.gamme.ecran.services.schedule.constants.Globals;

/**
 * Create Range Utils
 * 
 * @author SOPRA GROUP
 * 
 */
public class CreateScheduleUtil {
	
	private CreateScheduleUtil() {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Replace null attribute in contextIntermediary for DAO search, by "%" character : equivalent of "ANY CHARACTER" for SQL query 
	 * @param contextIntermediary
	 * @return contextScheduleCreation
	 * @throws Exception
	 */
	public static boolean setUpContextIntermediaryForDaoSearch(String[] str) {
		boolean modified = false;
		if (str != null){
			for (int i=0; i<str.length; i++){
				if (str[i] == null || str[i].isEmpty()) {
					str[i]="%";
					modified = true;
				}
			}
		}
		return modified;
	}
	
	public static String [] updateTabFromInstancesSelected(String number, String[] snTab) {
		int size = Integer.parseInt(number);
		String [] epuredSn = new String [size];
		for (int index = 0 ; index <= size-1; index ++) {
			if(snTab[index] == null || snTab[index].isEmpty()) {
				epuredSn [index] = ConstantsServer.SPACE;
			} else {
				epuredSn [index] = snTab[index];
			}
		}
		return epuredSn;
	}
	
	public static String convertListString(final String[] listSelectedString) {
		return convertListString(listSelectedString, ConstantsServer.SPACE);
	}
	
	public static String convertListString(final String[] listSelectedString, String separator) {
		StringBuilder listString = new StringBuilder(ConstantsServer.EMPTY_STRING);
		if (listSelectedString != null && listSelectedString.length>0)
			for (String currentString : listSelectedString) {
				listString.append(
						listString.length() > 0 ? separator
								: ConstantsServer.EMPTY_STRING).append(currentString);
			}

		return listString.toString();
	}
	
	public static void setupInstantiationParametersAssembly(IXslTransformation instantiationXslTransformer,
			ContextScheduleCreationAssembly contextCreation) {
		instantiationXslTransformer.addParameter(Globals.RANGE_PARAM_XSL_ORDER, contextCreation.getOrder());
		instantiationXslTransformer.addParameter(Globals.RANGE_PARAM_XSL_SCHEDULE_DATABASE_ID, "0");
		instantiationXslTransformer.addParameter(XsltConstants.XSLT_PARAMETER_LANGUAGE.value(), "");
	}
	
	
	/***
	 * This method provides a Map holding as key an instantiation XSL parameter
	 * value and as value a default valid value.<p>
	 * 
	 * For Assembly, default values are:<ul>
	 * <li> param_order as order number
	 * </ul>
	 * 
	 * @param instances the number of parts to instantiate
	 * @return
	 */
	public static Map<String, Object> getInstantiationDefaultParameters(int instances) {
		Map<String,Object> result = new HashMap<String,Object>();
		
		result.put(Globals.RANGE_PARAM_XSL_ORDER, Globals.RANGE_PARAM_XSL_ORDER);
		result.put(Globals.RANGE_PARAM_XSL_SCHEDULE_DATABASE_ID, "0");
		
		StringBuilder sn = new StringBuilder();
		for(int i = 0 ; i < instances; i++) {
			sn.append("SN").append(i).append(ConstantsServer.DOT_COMMA);
		}
		
		result.put(Globals.RANGE_PARAM_XSL_INSTANCES, instances);
		
		return result;
	}
	
	public static Integer toInteger(String s) {
		Integer i = null;
		try {
			i = Integer.parseInt(s);
		} catch (NumberFormatException nfe) {
		}
		return i;
	}
}
