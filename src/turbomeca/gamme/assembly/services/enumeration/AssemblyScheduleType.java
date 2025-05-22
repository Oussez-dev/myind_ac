package turbomeca.gamme.assembly.services.enumeration;

public enum AssemblyScheduleType {
	
	/**
	 * Schedule Assembly type
	 */
	ASSEMBLY("assembly"),
	/**
	 * Schedule Assembly type
	 */
	DISASSEMBLY("disassembly");
	
	private String type;
	
	private AssemblyScheduleType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
	
}
