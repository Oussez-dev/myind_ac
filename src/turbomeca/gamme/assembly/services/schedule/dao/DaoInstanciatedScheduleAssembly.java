/**
 * 
 */
package turbomeca.gamme.assembly.services.schedule.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import turbomeca.gamme.ecran.services.schedule.bo.BoInstanciatedScheduleAssembly;

/**
 * @author nladuguie
 *
 */
public interface DaoInstanciatedScheduleAssembly extends JpaRepository<BoInstanciatedScheduleAssembly, Long> {
	
	@Query("select instanciatedScheduleAssembly from "
			+ "BoInstanciatedScheduleAssembly instanciatedScheduleAssembly "
			+ "where instanciatedScheduleAssembly.instanciatedSchedule.id = ?1 ")
	public BoInstanciatedScheduleAssembly findByInstanciatedScheduleId(Long instanciatedScheduleId);
	
//	@Query("select scheduleIdentificationAssembly from "
//			+ "BoInstanciatedScheduleAssembly scheduleIdentificationAssembly "
//			+ "where scheduleIdentificationAssembly.instanciatedSchedule.scheduleState like ?1 ")
//	public List<BoInstanciatedScheduleAssembly> findByScheduleState(String scheduleState);
	
//	@Query("select scheduleIdentificationAssembly from "
//			+ "BoInstanciatedScheduleAssembly scheduleIdentificationAssembly "
//			+ "where scheduleIdentificationAssembly.instanciatedSchedule.scheduleState like ?1 "
//			+ "and scheduleIdentificationAssembly.instanciatedSchedule.site like ?2")
//	public List<BoInstanciatedScheduleAssembly> findByScheduleStateAndSite(String scheduleState, String site);
	
//	@Query("select instanciatedScheduleAssembly from "
//			+ "BoInstanciatedScheduleAssembly instanciatedScheduleAssembly "
//			+ "where instanciatedScheduleAssembly.instanciatedSchedule = ?1 ")
//	public BoInstanciatedScheduleAssembly findByFk(String fkinstanciatedSchedule);
	
//	@Query("select instanciatedScheduleAssembly from "
//			+ "BoInstanciatedScheduleAssembly instanciatedScheduleAssembly "
//			+ "where instanciatedScheduleAssembly.instanciatedSchedule.orderNumber = ?1 ")
//	public List<BoInstanciatedScheduleAssembly>	findByOrderNumber(String orderNumber);
	
	@Query("select instanciatedScheduleAssembly from "
			+ "BoInstanciatedScheduleAssembly instanciatedScheduleAssembly "
			+ "where instanciatedScheduleAssembly.instanciatedSchedule.orderNumber = ?1 "
			+ "and instanciatedScheduleAssembly.instanciatedSchedule.scheduleState = ?2")
	public List<BoInstanciatedScheduleAssembly> findByOrderNumberAndScheduleState(String orderNumber, String scheduleState);

//	@Query("select instanciatedScheduleAssembly from "
//			+ "BoInstanciatedScheduleAssembly instanciatedScheduleAssembly "
//			+ "where instanciatedScheduleAssembly.instanciatedSchedule.filePath in :instanciatedScheduleFilePaths and instanciatedScheduleAssembly.instanciatedSchedule.scheduleInstance.ufiStatus <> :ufiStatus")
//	public List<BoInstanciatedScheduleAssembly> findByUfiStatusNotAndInstanciatedFilePathIn(
//			@Param("ufiStatus") StatusRangeType ufiStatus,
//			@Param("instanciatedScheduleFilePaths") List<String> listInstanciatedScheduleFilePaths);
	
//	@Query("select instanciatedScheduleAssembly from "
//			+ "BoInstanciatedScheduleAssembly instanciatedScheduleAssembly "
//			+ "where instanciatedScheduleAssembly.instanciatedSchedule.scheduleInstance.ufiStatus in :listUfiStatus")
//	public List<BoInstanciatedScheduleAssembly> findByUfiStatusIn(
//			@Param("listUfiStatus") List<StatusRangeType> listUfiStatus);
	
//	@Query("select instanciatedScheduleAssembly from "
//			+ "BoInstanciatedScheduleAssembly instanciatedScheduleAssembly "
//			+ "where instanciatedScheduleAssembly.instanciatedSchedule.scheduleInstance.ufiStatus = ?1 and instanciatedScheduleAssembly.instanciatedSchedule.scheduleState= ?2")
//	public List<BoInstanciatedScheduleAssembly> findByUfiStatus(
//			StatusRangeType ufiStatus, String scheduleState);
	
//	@Query("select instanciatedScheduleAssembly from "
//			+ "BoInstanciatedScheduleAssembly instanciatedScheduleAssembly "
//			+ "where instanciatedScheduleAssembly.instanciatedSchedule.scheduleInstance.ufiStatus in :listUfiStatus and instanciatedScheduleAssembly.instanciatedSchedule.scheduleState = :currentScheduleState")
//	public List<BoInstanciatedScheduleAssembly> findByScheduleStateAndUfiStatusIn(
//			@Param("listUfiStatus") List<StatusRangeType> listUfiStatus,
//			@Param("currentScheduleState") String scheduleState);
	
	@Query("select scheduleIdentificationAssembly from "
			+ "BoInstanciatedScheduleAssembly scheduleIdentificationAssembly "
			+ "where scheduleIdentificationAssembly.instanciatedSchedule.orderNumber like :orderNumber "
			+ "and scheduleIdentificationAssembly.affair like :affair "
			+ "and scheduleIdentificationAssembly.type like :type "
			+ "and scheduleIdentificationAssembly.family like :family "
			+ "and scheduleIdentificationAssembly.variants like :variants "
			+ "and scheduleIdentificationAssembly.material like :material "
			+ "and cast(scheduleIdentificationAssembly.instanciatedSchedule.scheduleModification.modificationDate as string) like :modificationDate "
			+ "and scheduleIdentificationAssembly.instanciatedSchedule.scheduleCreation.creationUser like :creationUser "
			+ "and cast(scheduleIdentificationAssembly.instanciatedSchedule.scheduleInstance.ufiStatus as string) like :ufiStatus "
			+ "and scheduleIdentificationAssembly.context like :context "
			+ "and scheduleIdentificationAssembly.instanciatedSchedule.scheduleState like :scheduleState "
			+ "and scheduleIdentificationAssembly.instanciatedSchedule.site like :site "
			+ "and ((COALESCE(NULL, :familyMaterialContextVariantPermissionList) IS NULL) "
			+ "or (scheduleIdentificationAssembly.family IN (:familyMaterialContextVariantPermissionList)) "
			+ "or (CONCAT(scheduleIdentificationAssembly.family, :permissionDelimiter, scheduleIdentificationAssembly.material, :permissionDelimiter, scheduleIdentificationAssembly.context) IN (:familyMaterialContextVariantPermissionList)) "
			+ "or (CONCAT(scheduleIdentificationAssembly.family, :permissionDelimiter, scheduleIdentificationAssembly.material, :permissionDelimiter, scheduleIdentificationAssembly.context, :permissionDelimiter, scheduleIdentificationAssembly.variants) IN (:familyMaterialContextVariantPermissionList))) ") 
	public Page<BoInstanciatedScheduleAssembly> findByCriteriasAndPermissions(
			@Param("orderNumber") String orderNumber, 
			@Param("affair") String affair, 
			@Param("type") String type, 
			@Param("family") String family, 
			@Param("variants") String variants, 
			@Param("material") String material, 
			@Param("modificationDate") String modificationDate, 
			@Param("creationUser") String creationUser, 
			@Param("ufiStatus") String ufiStatus, 
			@Param("context") String context, 
			@Param("scheduleState") String scheduleState, 
			@Param("site") String site, 
			@Param("familyMaterialContextVariantPermissionList") List<String> familyMaterialContextVariantList,
			@Param("permissionDelimiter") String permissionDelimiter,
			Pageable pagination);

	@Query("select scheduleIdentificationAssembly from "
			+ "BoInstanciatedScheduleAssembly scheduleIdentificationAssembly "
			+ "where scheduleIdentificationAssembly.instanciatedSchedule.orderNumber like :orderNumber "
			+ "and scheduleIdentificationAssembly.affair like :affair "
			+ "and scheduleIdentificationAssembly.type like :type "
			+ "and scheduleIdentificationAssembly.family like :family "
			+ "and scheduleIdentificationAssembly.variants like :variants "
			+ "and scheduleIdentificationAssembly.material like :material "
			+ "and cast(scheduleIdentificationAssembly.instanciatedSchedule.scheduleModification.modificationDate as date) > cast(:modificationDate as date) "
			+ "and scheduleIdentificationAssembly.instanciatedSchedule.scheduleCreation.creationUser like :creationUser "
			+ "and cast(scheduleIdentificationAssembly.instanciatedSchedule.scheduleInstance.ufiStatus as string) like :ufiStatus "
			+ "and scheduleIdentificationAssembly.context like :context "
			+ "and scheduleIdentificationAssembly.instanciatedSchedule.scheduleState like :scheduleState "
			+ "and scheduleIdentificationAssembly.instanciatedSchedule.site like :site "
			+ "and ((COALESCE(NULL, :familyMaterialContextVariantPermissionList) IS NULL) "
			+ "or (scheduleIdentificationAssembly.family IN (:familyMaterialContextVariantPermissionList)) "
			+ "or (CONCAT(scheduleIdentificationAssembly.family, :permissionDelimiter, scheduleIdentificationAssembly.material, :permissionDelimiter, scheduleIdentificationAssembly.context) IN (:familyMaterialContextVariantPermissionList)) "
			+ "or (CONCAT(scheduleIdentificationAssembly.family, :permissionDelimiter, scheduleIdentificationAssembly.material, :permissionDelimiter, scheduleIdentificationAssembly.context, :permissionDelimiter, scheduleIdentificationAssembly.variants) IN (:familyMaterialContextVariantPermissionList))) ") 
	public Page<BoInstanciatedScheduleAssembly> findByCriteriasAndPermissionsModificationDate(
			@Param("orderNumber") String orderNumber, 
			@Param("affair") String affair, 
			@Param("type") String type, 
			@Param("family") String family, 
			@Param("variants") String variants, 
			@Param("material") String material, 
			@Param("modificationDate") String modificationDate,
			@Param("creationUser") String creationUser, 
			@Param("ufiStatus") String ufiStatus, 
			@Param("context") String context, 
			@Param("scheduleState") String scheduleState, 
			@Param("site") String site, 
			@Param("familyMaterialContextVariantPermissionList") List<String> familyMaterialContextVariantList,
			@Param("permissionDelimiter") String permissionDelimiter,
			Pageable pagination);
	
	@Query("select scheduleIdentificationAssembly from "
			+ "BoInstanciatedScheduleAssembly scheduleIdentificationAssembly "
			+ "where scheduleIdentificationAssembly.instanciatedSchedule.orderNumber like :orderNumber "
			+ "and scheduleIdentificationAssembly.affair like :affair "
			+ "and scheduleIdentificationAssembly.type like :type "
			+ "and scheduleIdentificationAssembly.family like :family "
			+ "and scheduleIdentificationAssembly.variants like :variants "
			+ "and scheduleIdentificationAssembly.material like :material "
			+ "and cast(scheduleIdentificationAssembly.instanciatedSchedule.scheduleModification.modificationDate as string) like :modificationDate "
			+ "and scheduleIdentificationAssembly.instanciatedSchedule.scheduleCreation.creationUser like :creationUser "
			+ "and cast(scheduleIdentificationAssembly.instanciatedSchedule.scheduleInstance.ufiStatus as string) like :ufiStatus "
			+ "and scheduleIdentificationAssembly.context like :context "
			+ "and scheduleIdentificationAssembly.instanciatedSchedule.scheduleState like :scheduleState "
			+ "and scheduleIdentificationAssembly.instanciatedSchedule.site like :site "
			+ "and ((COALESCE(NULL, :listInstanciatedScheduleFilePath) IS NULL) "
			+ "or (scheduleIdentificationAssembly.instanciatedSchedule.filePath NOT IN (:listInstanciatedScheduleFilePath))) ")
	public Page<BoInstanciatedScheduleAssembly> findByCriteriasAndNotInEditionList(
			@Param("orderNumber") String orderNumber, 
			@Param("affair") String affair, 
			@Param("type") String type, 
			@Param("family") String family, 
			@Param("variants") String variants, 
			@Param("material") String material, 
			@Param("modificationDate") String modificationDate, 
			@Param("creationUser") String creationUser, 
			@Param("ufiStatus") String ufiStatus, 
			@Param("context") String context, 
			@Param("scheduleState") String scheduleState, 
			@Param("site") String site, 
			@Param("listInstanciatedScheduleFilePath") List<String> listInstanciatedScheduleFilePath,
			Pageable pagination);
	
	@Query("select scheduleIdentificationAssembly from "
			+ "BoInstanciatedScheduleAssembly scheduleIdentificationAssembly "
			+ "where scheduleIdentificationAssembly.instanciatedSchedule.orderNumber like :orderNumber "
			+ "and scheduleIdentificationAssembly.affair like :affair "
			+ "and scheduleIdentificationAssembly.type like :type "
			+ "and scheduleIdentificationAssembly.family like :family "
			+ "and scheduleIdentificationAssembly.variants like :variants "
			+ "and scheduleIdentificationAssembly.material like :material "
			+ "and cast(scheduleIdentificationAssembly.instanciatedSchedule.scheduleModification.modificationDate as string) like :modificationDate "
			+ "and scheduleIdentificationAssembly.instanciatedSchedule.scheduleCreation.creationUser like :creationUser "
			+ "and cast(scheduleIdentificationAssembly.instanciatedSchedule.scheduleInstance.ufiStatus as string) like :ufiStatus "
			+ "and scheduleIdentificationAssembly.context like :context "
			+ "and scheduleIdentificationAssembly.instanciatedSchedule.scheduleState like :scheduleState "
			+ "and scheduleIdentificationAssembly.instanciatedSchedule.site like :site "
			+ "and ((COALESCE(NULL, :listUfiStatus) IS NOT NULL) "
			+ "and (scheduleIdentificationAssembly.instanciatedSchedule.scheduleInstance.ufiStatus IN (:listUfiStatus))) ")
	public Page<BoInstanciatedScheduleAssembly> findByCriteriasAndUfiStatusInList(
			@Param("orderNumber") String orderNumber, 
			@Param("affair") String affair, 
			@Param("type") String type, 
			@Param("family") String family, 
			@Param("variants") String variants, 
			@Param("material") String material, 
			@Param("modificationDate") String modificationDate, 
			@Param("creationUser") String creationUser, 
			@Param("ufiStatus") String ufiStatus, 
			@Param("context") String context, 
			@Param("scheduleState") String scheduleState, 
			@Param("site") String site, 
			@Param("listUfiStatus") List<String> listUfiStatus,
			Pageable pagination);
	
	@Query("select scheduleIdentificationAssembly from "
			+ "BoInstanciatedScheduleAssembly scheduleIdentificationAssembly "
			+ "where scheduleIdentificationAssembly.instanciatedSchedule.orderNumber like :orderNumber "
			+ "and scheduleIdentificationAssembly.affair like :affair "
			+ "and scheduleIdentificationAssembly.type like :type "
			+ "and scheduleIdentificationAssembly.family like :family "
			+ "and scheduleIdentificationAssembly.variants like :variants "
			+ "and scheduleIdentificationAssembly.material like :material "
			+ "and cast(scheduleIdentificationAssembly.instanciatedSchedule.scheduleModification.modificationDate as string) like :modificationDate "
			+ "and scheduleIdentificationAssembly.instanciatedSchedule.scheduleCreation.creationUser like :creationUser "
			+ "and cast(scheduleIdentificationAssembly.instanciatedSchedule.scheduleInstance.ufiStatus as string) like :ufiStatus "
			+ "and scheduleIdentificationAssembly.context like :context "
			+ "and scheduleIdentificationAssembly.instanciatedSchedule.scheduleState like :scheduleState "
			+ "and scheduleIdentificationAssembly.instanciatedSchedule.site like :site "
			+ "and ((COALESCE(NULL, :listUfiStatus) IS NULL) "
			+ "or (scheduleIdentificationAssembly.instanciatedSchedule.scheduleInstance.ufiStatus NOT IN (:listUfiStatus))) "
			+ "and ((COALESCE(NULL, :listInstanciatedScheduleFilePath) IS NOT NULL) "
			+ "and (scheduleIdentificationAssembly.instanciatedSchedule.filePath IN (:listInstanciatedScheduleFilePath))) ")
	public Page<BoInstanciatedScheduleAssembly> findByCriteriasAndUfiStatusNotInListAndEditionInList(
			@Param("orderNumber") String orderNumber, 
			@Param("affair") String affair, 
			@Param("type") String type, 
			@Param("family") String family, 
			@Param("variants") String variants, 
			@Param("material") String material, 
			@Param("modificationDate") String modificationDate, 
			@Param("creationUser") String creationUser, 
			@Param("ufiStatus") String ufiStatus, 
			@Param("context") String context, 
			@Param("scheduleState") String scheduleState, 
			@Param("site") String site, 
			@Param("listUfiStatus") List<String> listUfiStatus,
			@Param("listInstanciatedScheduleFilePath") List<String> listInstanciatedScheduleFilePath,
			Pageable pagination);
	
}