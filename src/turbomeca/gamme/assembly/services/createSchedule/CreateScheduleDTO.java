package turbomeca.gamme.assembly.services.createSchedule;


public class CreateScheduleDTO {
	
	// Context Assembly
	private String scheduleType;
	private String contextType = "";
	private String instances;
	private String family;
	private String variant;
	private String material;
	private String suffix;
	private String affair;
	private String order;
	private String pn;
	private String va;
	private String[] modification;
	private String[] level;
	private String methodAssembly;
	private String applicabilitiesMaterial;
	private String intervention;
	private boolean canEditInstances;
	private String displayInput = "block";
	private String showLevels;
	private String showInterventions;
	private String showMethods;
	private String effectivity;
	private String version;
	private String language;
	private String name;
	private String roles;
	private String site;
	private String referential;
	private String	instantiationVersion;
	
	public String getInstantiationVersion() {
		return instantiationVersion;
	}
	public void setInstantiationVersion(String instantiationVersion) {
		this.instantiationVersion = instantiationVersion;
	}
	// Context User
	private String login;
	
	public String getScheduleType() {
		return scheduleType;
	}
	public void setScheduleType(String scheduleType) {
		this.scheduleType = scheduleType;
	}
	public String getContextType() {
		return contextType;
	}
	
	public void setContextType(String contextType) {
		this.contextType = contextType;
	}
	
	public String getInstances() {
		return instances;
	}
	public void setInstances(String instances) {
		this.instances = instances;
	}
	public String getFamily() {
		return family;
	}
	public void setFamily(String family) {
		this.family = family;
	}
	public String getVariant() {
		return variant;
	}
	public void setVariant(String variant) {
		this.variant = variant;
	}
	public String getMaterial() {
		return material;
	}
	public void setMaterial(String material) {
		this.material = material;
	}
	public String getAffair() {
		return affair;
	}
	public void setAffair(String affair) {
		this.affair = affair;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public String getPn() {
		return pn;
	}
	public void setPn(String pn) {
		this.pn = pn;
	}
	public String getVa() {
		return va;
	}
	public void setVa(String va) {
		this.va = va;
	}
	public String[] getModification() {
		return modification;
	}
	public void setModification(String[] modification) {
		this.modification = modification;
	}
	public String[] getLevel() {
		return level;
	}
	public void setLevel(String[] level) {
		this.level = level;
	}
	public String getMethodAssembly() {
		return methodAssembly;
	}
	public void setMethodAssembly(String methodAssembly) {
		this.methodAssembly = methodAssembly;
	}
	public String getApplicabilitiesMaterial() {
		return applicabilitiesMaterial;
	}
	public void setApplicabilitiesMaterial(String applicabilitiesMaterial) {
		this.applicabilitiesMaterial = applicabilitiesMaterial;
	}
	public String getIntervention() {
		return intervention;
	}
	public void setIntervention(String intervention) {
		this.intervention = intervention;
	}
	public boolean isCanEditInstances() {
		return canEditInstances;
	}
	public void setCanEditInstances(boolean canEditInstances) {
		this.canEditInstances = canEditInstances;
	}
	public String getDisplayInput() {
		return displayInput;
	}
	public void setDisplayInput(String displayInput) {
		this.displayInput = displayInput;
	}
	public String getShowLevels() {
		return showLevels;
	}
	public void setShowLevels(String showLevels) {
		this.showLevels = showLevels;
	}
	public String getShowInterventions() {
		return showInterventions;
	}
	public void setShowInterventions(String showInterventions) {
		this.showInterventions = showInterventions;
	}
	public String getShowMethods() {
		return showMethods;
	}
	public void setShowMethods(String showMethods) {
		this.showMethods = showMethods;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getEffectivity() {
		return effectivity;
	}
	public void setEffectivity(String effectivity) {
		this.effectivity = effectivity;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRoles() {
		return roles;
	}
	public void setRoles(String roles) {
		this.roles = roles;
	}
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getSuffix() {
		return suffix;
	}
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	public String getReferential() {
		return referential;
	}
	public void setReferential(String referential) {
		this.referential = referential;
	}
	
}
