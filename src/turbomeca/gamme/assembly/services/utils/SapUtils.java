package turbomeca.gamme.assembly.services.utils;

import turbomeca.gamme.assembly.services.model.data.types.ModuleSapType;
import turbomeca.gamme.ecran.services.common.utils.sap.SapCommonUtils;

public class SapUtils extends SapCommonUtils {
	
	public static final String SAP_INTERVENTION_TYPE_OVERHAUL = "01";
	public static final String SAP_INTERVENTION_TYPE_REPAIRED = "02";

	public static final String INTERVENTION_TYPE_OVERHAUL = "overhaul";
	public static final String INTERVENTION_TYPE_REPAIRED = "repaired";

    /**
     * Convert UFI module to SAP module
     * @param module UFI module
     * @return the associated SAP module
     */
    public static String convertToSapFormat(ModuleSapType module) {
        String sapModule = null;
        switch(module) {
        case MODULE1:
            sapModule = "01";
            break;
        case MODULE2:
            sapModule = "02";
            break;
        case MODULE3:
            sapModule = "03";
            break;
        case MODULE4:
            sapModule = "04";
            break;
        case MODULE5:
            sapModule = "05";
            break;
        case MODULE6:
            sapModule = "06";
            break;
        case OUTMODULE:
            sapModule = "00";
            break;
        }
        return sapModule;
    }
    
    public static String getTypeIntervention(String currentIntervention) {
    	String interventionType = null;
    	if(currentIntervention.equals(SAP_INTERVENTION_TYPE_OVERHAUL)) {
    		interventionType = INTERVENTION_TYPE_OVERHAUL;
    	} else if(currentIntervention.equals(SAP_INTERVENTION_TYPE_REPAIRED)) {
    		interventionType = INTERVENTION_TYPE_REPAIRED;
    	} 
    	return interventionType;
    }


}
