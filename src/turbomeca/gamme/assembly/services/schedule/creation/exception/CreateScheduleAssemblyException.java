
package turbomeca.gamme.assembly.services.schedule.creation.exception;

import turbomeca.gamme.assembly.services.schedule.creation.utils.AssemblyGlobalServices;
import turbomeca.gamme.ecran.services.schedule.constants.Globals;

public class CreateScheduleAssemblyException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public static final int EXCEPTION_EFFECTIVITY_NOT_FOUND = 0;
	public static final int EXCEPTION_NO_FAMILY_FOUND = 1;
	public static final int EXCEPTION_NO_MATERIALS_FOUND = 2;
	public static final int EXCEPTION_NO_RANGE_FOUND = 3;
	public static final int EXCEPTION_XML_GENERIC_NOT_FOUND = 4;
	public static final int EXCEPTION_DATA_MISSING_EFFECTIVITIES= 5;
	public static final int EXCEPTION_DATA_MISSING_GENERIC_FILE_EFFECTIVITIES= 6;
	public static final int EXCEPTION_NO_PN_FOUND= 7;
	public static final int EXCEPTION_EFFECTIVITY_MORE_THAN_ONE_FOUND = 8;
	public static final int EXCEPTION_NO_RIGHT_TO_CREATE_SCHEDULE = 9;
	public static final int EXCEPTION_ORDER_NOT_EXIST_IN_SAP = 10;
	public static final int EXCEPTION_RETRIEVE_INFORMATION_FROM_SAP = 11;
	
	private int codeErreur = 0;
	
	public CreateScheduleAssemblyException() {
		super();
	}
	
	public CreateScheduleAssemblyException(String message) {
		super(message);
	}
	
	public CreateScheduleAssemblyException(int code) {
		super();
		this.codeErreur = code;
	}
	public int getCodeErreur() {
		return codeErreur;
	}
	public void setCodeErreur(int codeErreur) {
		this.codeErreur = codeErreur;
	}
	
	public String getExceptionMessage(){
		String message;
		switch (getCodeErreur()){
		case EXCEPTION_EFFECTIVITY_NOT_FOUND:
			message=AssemblyGlobalServices.ERROR_NO_EFFECTIVITY_FOUND;
			break;	
		case EXCEPTION_NO_FAMILY_FOUND:
			message=AssemblyGlobalServices.ERROR_NO_FAMILY_FOUND;
			break;	
		case EXCEPTION_NO_MATERIALS_FOUND:
			message=AssemblyGlobalServices.ERROR_NO_MATERIALS_FOUND;
			break;	
		case EXCEPTION_NO_RANGE_FOUND:
			message=AssemblyGlobalServices.ERROR_NO_RANGE_FOUND;
			break;	
		case EXCEPTION_XML_GENERIC_NOT_FOUND:
			message=AssemblyGlobalServices.ERROR_SEARCH_SCHEDULE_SYNTHESIS;
			break;	
		case EXCEPTION_DATA_MISSING_EFFECTIVITIES:
			message=AssemblyGlobalServices.PROPERTY_ERROR_DATA_MISSING_EFFECTIVITIES;
			break;
		case EXCEPTION_DATA_MISSING_GENERIC_FILE_EFFECTIVITIES:
			message=AssemblyGlobalServices.PROPERTY_ERROR_DATA_MISSING_GENERIC_FILE_EFFECTIVITIES;
			break;
		case EXCEPTION_EFFECTIVITY_MORE_THAN_ONE_FOUND:
			message=AssemblyGlobalServices.PROPERTY_ERROR_MORE_THAT_ONE_EFFECTIVITY_FOUND;
			break;
		case EXCEPTION_NO_PN_FOUND:
			message=AssemblyGlobalServices.PROPERTY_ERROR_NO_PN_FOUND;
			break;
		case EXCEPTION_NO_RIGHT_TO_CREATE_SCHEDULE:
			message=AssemblyGlobalServices.ERROR_CREATE_NO_PERMISSION;
			break;
		case EXCEPTION_RETRIEVE_INFORMATION_FROM_SAP:
			message= AssemblyGlobalServices.ERROR_RETRIEVE_INFORMATIONS_FROM_SAP;
			break;
		case EXCEPTION_ORDER_NOT_EXIST_IN_SAP:
			message= AssemblyGlobalServices.ERROR_ORDER_NOT_EXIST_IN_SAP;
			break;
		default:
			message=Globals.ERROR_INTERNAL;
			break;		
		}
		return message;
	}
}
