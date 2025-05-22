package turbomeca.gamme.assembly.services.constants;

public class Constants {

	/** Roles constants */
	public static final int ROLE_CONSULT = 0;
	public static final int ROLE_OPERATOR_ASSEMBLY = 1;
	public static final int ROLE_OPERATOR_DISASSEMBLY = 2;
	public static final int ROLE_CONTROLLER_ASSEMBLY = 3;
	public static final int ROLE_CONTROLLER_DISASSEMBLY = 4;
	public static final int ROLE_REDACTOR = 5;
	public static final int ROLE_ADMIN = 6;
	public static final int ROLE_ADMIN_USER = 7;
	public static final int ROLE_NOTIFICATION_QUALITY = 8;
	public static final int ROLE_NOTIFICATION_METHOD = 9;
	public static final int ROLE_OPERATOR_ASSEMBLY_ST = 10;
	public static final int ROLE_OPERATOR_DISASSEMBLY_ST = 11;
	
	/** Schedules types constants */
	public static final int SCHEDULE_TYPE_ASSEMBLY = 0;
	public static final int SCHEDULE_TYPE_DISASSEMBLY = 1;
	
	/** Schedules types constants */
	public static final String SCHEDULE_STATUS_TODO = "TODO";
	public static final String SCHEDULE_STATUS_TO_SIGN = "TO_SIGN";
	public static final String SCHEDULE_STATUS_DONE = "DONE";
	
	@Deprecated // Use Domain
	public static final String ASSEMBLY = "assembly";
	@Deprecated // Use Domain
	public static final String DISASSEMBLY = "disassembly";
	
	public static final String SCHEDULE_NEW_CONTEXT = "new";
}
