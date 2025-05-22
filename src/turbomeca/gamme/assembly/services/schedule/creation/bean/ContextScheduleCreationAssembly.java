package turbomeca.gamme.assembly.services.schedule.creation.bean;

import java.util.List;
import java.util.Map;
import java.util.Set;

import turbomeca.gamme.ecran.services.schedule.bean.ContextScheduleCreation;


public class ContextScheduleCreationAssembly  extends ContextScheduleCreation {

	private static final long serialVersionUID = 7757684881518232211L;

	private static final  String ISOLATED_MODULE = "IsolatedModule";
	
	private static final  String FICTIVE_ENGINE = "FictiveEngine";
	
	private String synthesis; 
	
	private String applicabilityMaterial; 

	private Set<String> level;
	
	private String method;
	
	private String folderFamily;
	private String site;

	private String currentOp;

	private String fatherOrder;

	private String scheduleStatus;

	private String versionScheduleStatus;

	private String[] snTab;

	private Map<String, Map<String, List<String>>> mapPnSnComponents;
	
	private String	instantiationVersion;
	
	public String getInstantiationVersion() {
		return instantiationVersion;
	}

	public void setInstantiationVersion(String instantiationVersion) {
		this.instantiationVersion = instantiationVersion;
	}

	private boolean isCreateCheckboxTaskMark;
	
	public String getApplicabilityMaterial() {
		return applicabilityMaterial;
	}

	public void setApplicabilityMaterial(String applicabilityMaterial) {
		this.applicabilityMaterial = applicabilityMaterial;
	}

	public Set<String> getLevel() {
		return level;
	}

	public String[] getLevelAsArray() {
		if( level == null ) {
			return null;
		}
		return level.toArray(new String[0]);
	}
	
	public void setLevel(Set<String> level) {
		this.level = level;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getFolderFamily() {
		return folderFamily;
	}

	public void setFolderFamily(String folderFamily) {
		this.folderFamily = folderFamily;
	}
	
	/**
	 * Value must be null if we are in external creation for don't filter schedule on instanciation
	 * @see #3482
	 * @return
	 */
	public Boolean isFictiveEngine(){
		if( applicabilityMaterial!=null && applicabilityMaterial.isEmpty()) {
			return isExternalCreation() ? null : false;
		}
		return applicabilityMaterial!=null && applicabilityMaterial.equals(FICTIVE_ENGINE);
	}
	
	/**
	 * Value must be null if we are in external creation for don't filter schedule on instanciation
	 * @see #3482
	 * @return
	 */
	public Boolean isIsolateModule(){
		if( applicabilityMaterial!=null && applicabilityMaterial.isEmpty()) {
			return isExternalCreation() ? null : false;
		}
		return applicabilityMaterial!=null && applicabilityMaterial.equals(ISOLATED_MODULE);
	}
	
	public void setIsolatedModule(Boolean isIsolatedModule) {
		/**
		 * If null, no material applicability is selected. Setting it to an empty string
		 * allow instantiation with both isolated module and fictive engine
		 * applicabilities enable
		 */
		if(isIsolatedModule == null ) {
			setApplicabilityMaterial("");
		}
		else if(isIsolatedModule) {
			setApplicabilityMaterial(ISOLATED_MODULE);
		} else {
			setApplicabilityMaterial(FICTIVE_ENGINE);
		}
	}
	
	public String getDirectoryMaterial(){
		return getFolderFamily()+"/"+getMaterial();
	}

	public void setSynthesis(String synthesis) {
		this.synthesis = synthesis;
	}

	public String getSynthesis() {
		return synthesis;
	}

	public boolean isCreateCheckboxTaskMark() { 
		return isCreateCheckboxTaskMark;
	}

	public void setCreateCheckboxTaskMark(boolean isCreateCheckboxTaskMark) {
		this.isCreateCheckboxTaskMark = isCreateCheckboxTaskMark;
	}

	public String getCurrentOp() {
		return this.currentOp;
	}
	
	public void setCurrentOp(String currentOp) {
		this.currentOp = currentOp;
	}

	public String getFatherOrder() {
		return fatherOrder;
	} 
	
	public void setFatherOrder(String trimLeft) {
		this.fatherOrder = trimLeft;
	}

	public String getScheduleStatus() {
		return scheduleStatus;
	}
	
	public void setScheduleStatus(String char3) {
		this.scheduleStatus = char3;
	}

	public String getVersionScheduleStatus() {
		return versionScheduleStatus;
	}
	
	public void setVersionScheduleStatus(String string) {
		this.versionScheduleStatus = string;
	}

	public String[] getSnTab() {
		return snTab;
	}
	
	public void setSnTab(String[] snTabComponents) {
		this.snTab = snTabComponents;
	}

	public Map<String, Map<String, List<String>>> getMapPnSnComponents() {
		return mapPnSnComponents;
	}
	
	public void setMapPnSnComponents(
			Map<String, Map<String, List<String>>> mapSplitComponents) {
		this.mapPnSnComponents = mapSplitComponents;
	}

	/**
	 * Use this site to override the context user one for schedule instantiation
	 * 
	 * @return
	 */
	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}
	
}
