package turbomeca.gamme.assembly.services.schedule.bean;

import java.util.ArrayList;
import java.util.List;

import turbomeca.gamme.ecran.services.common.constants.GlobalConstants;
import turbomeca.gamme.ecran.services.schedule.bean.ContextSearchSchedule;

public class ContextSearchScheduleAssembly extends ContextSearchSchedule {
	
	private String affair;
	private String material;
	private String family;
	private String variants;
	private String type;
	private String context;
	
	/**
	 * this list contains permission keys
	 * a key could be :
	 * 	"family"
	 * 	"family" + DELIMITER + "material" + DELIMITER + "variant"
	 * 	"family" + DELIMITER + "material" + DELIMITER + "variant" + DELIMITER + "variant"
	 */
	private List<String> familyMaterialContextVariantPermissionList = null;
	
	public static final String PERMISSION_DELIMITER = "-";
	
	public ContextSearchScheduleAssembly() {
		initializeAllSpecificFields();
	}
	
	/**
	 * Creates an Assembly specific context holding all generic context fields
	 * and specific Assembly context initialized to SEPARATOR_REQUEST_LIKE (%) for MySQL Search
	 * 
	 * @param contextGeneric
	 */
	public ContextSearchScheduleAssembly(ContextSearchSchedule contextGeneric) {
		setCreationUser(contextGeneric.getCreationUser());
		setCurrentOperation(contextGeneric.getCurrentOperation());
		setModificationDate(contextGeneric.getModificationDate());
		setOrderNumber(contextGeneric.getOrderNumber());
		setReference(contextGeneric.getReference());
		setScheduleState(contextGeneric.getScheduleState());
		setUfiStatus(contextGeneric.getUfiStatus());
		setScheduleSite(contextGeneric.getScheduleSite());
		setPagination(contextGeneric.getPagination());
		
		initializeAllSpecificFields();
	}
	
	private void initializeAllSpecificFields(){
		setAffair(GlobalConstants.SEPARATOR_REQUEST_LIKE);
		setMaterial(GlobalConstants.SEPARATOR_REQUEST_LIKE);
		setFamily(GlobalConstants.SEPARATOR_REQUEST_LIKE);
		setVariants(GlobalConstants.SEPARATOR_REQUEST_LIKE);
		setType(GlobalConstants.SEPARATOR_REQUEST_LIKE);
		setContext(GlobalConstants.SEPARATOR_REQUEST_LIKE);
	}
	
	public String getAffair() {
		return affair;
	}

	public void setAffair(String affair) {
		this.affair = affair;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getFamily() {
		return family;
	}

	public void setFamily(String family) {
		this.family = family;
	}

	public String getVariants() {
		return variants;
	}

	public void setVariants(String variants) {
		this.variants = variants;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}
	
	private void addPermissionToList(String permissionKey) {
		if (familyMaterialContextVariantPermissionList == null) {
			familyMaterialContextVariantPermissionList = new ArrayList<String>();
		}
		familyMaterialContextVariantPermissionList.add(permissionKey);
	}
	
	public void addFamilyPermission(String familyPermission) {
		if (familyPermission != null && !familyPermission.isEmpty()) {
			addPermissionToList(familyPermission);
		}
	}
	
	public void addFamilyMaterialContextPermission(String familyPermission, String materialPermission, 
			String contextPermission) {
		if (familyPermission != null && !familyPermission.isEmpty()
				&& materialPermission != null && !materialPermission.isEmpty()
				&& contextPermission != null && !contextPermission.isEmpty()) {
			addPermissionToList(
					familyPermission + PERMISSION_DELIMITER
					+ materialPermission + PERMISSION_DELIMITER
					+ contextPermission);
		} else {
			addFamilyPermission(familyPermission);
		}
	}

	public void addFamilyMaterialContextVariantPermission(String familyPermission, String materialPermission, 
			String contextPermission, String variantPermission) {
		if (familyPermission != null && !familyPermission.isEmpty()
				&& materialPermission != null && !materialPermission.isEmpty()
				&& contextPermission != null && !contextPermission.isEmpty()
				&& variantPermission != null && !variantPermission.isEmpty()) {
			addPermissionToList(
					familyPermission + PERMISSION_DELIMITER
					+ materialPermission + PERMISSION_DELIMITER
					+ contextPermission + PERMISSION_DELIMITER
					+ variantPermission);
		} else {
			addFamilyMaterialContextPermission(familyPermission, materialPermission, contextPermission);
		}
	}

	public List<String> getFamilyMaterialContextVariantPermissionList() {
		return familyMaterialContextVariantPermissionList;
	}
	
}
