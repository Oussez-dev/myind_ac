package turbomeca.gamme.assembly.services.dvi;

import turbomeca.gamme.ecran.services.common.bean.AResultDataException;

public class SearchCreationContextException extends AResultDataException {

	private static final long serialVersionUID = -7765942283646687405L;
	
	public enum  CreationContextStatusCode {
		
		/**
		 * Raised on an unknown and unexpected stack raised during processing
		 * 
		 */
		ERROR_INTERNAL,
		/**
		 * Raised when no schedule can be found according to provided parameters
		 * For example on assembly, when the provided VA does not exist for the provided PN
		 * 
		 */
		ERROR_INTERMEDIARY_SCHEDULE_NOT_FOUND,
	}

	public SearchCreationContextException(CreationContextStatusCode statusCode, String message) {
		super(statusCode.ordinal(), message);
	}
	
	public SearchCreationContextException(CreationContextStatusCode statusCode, String message, Throwable e) {
		super(statusCode.ordinal(), message, e);
	}

}
