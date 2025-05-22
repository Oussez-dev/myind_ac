package turbomeca.gamme.assembly.services.schedule.search;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import turbomeca.gamme.assembly.services.schedule.bean.ContextSearchScheduleAssembly;
import turbomeca.gamme.assembly.services.schedule.dao.DaoInstanciatedScheduleAssembly;
import turbomeca.gamme.ecran.services.common.enumtypes.StatusRangeType;
import turbomeca.gamme.ecran.services.editing.bo.BoScheduleEdition;
import turbomeca.gamme.ecran.services.editing.dao.DaoScheduleEdition;
import turbomeca.gamme.ecran.services.schedule.bean.ContextSearchSchedule;
import turbomeca.gamme.ecran.services.schedule.bo.BoInstanciatedSchedule;
import turbomeca.gamme.ecran.services.schedule.bo.BoInstanciatedScheduleAssembly;
import turbomeca.gamme.ecran.services.schedule.service.search.ISearchScheduleInstantiatedService;

/**
 * 
 * @author Sopra Steria Group
 *
 */
@Service(value = "turbomeca.gamme.ecran.services.schedule.service.search.ISearchScheduleInstantiatedService(BoInstanciatedSchedule)")
public class SearchScheduleInstantiatedService implements ISearchScheduleInstantiatedService<BoInstanciatedSchedule> {

	private Logger logger = Logger.getLogger(SearchScheduleInstantiatedService.class);
	
	@Autowired
	private DaoScheduleEdition daoScheduleEdition;

	@Autowired
	private DaoInstanciatedScheduleAssembly daoScheduleInstanciatedAssembly;

	@Override
	public Map<BoInstanciatedSchedule, List<BoScheduleEdition>> getAllSchedule(
			ContextSearchSchedule contextSearchSchedule) {
		ContextSearchScheduleAssembly context = getWorkingContext(contextSearchSchedule);
		Map<BoInstanciatedSchedule, List<BoScheduleEdition>> mapScheduleInstanciated = new LinkedHashMap<BoInstanciatedSchedule, List<BoScheduleEdition>>();
		try {
			// Fill the list of instanciated schedule according to pagination applicated
			Page<BoInstanciatedScheduleAssembly> listInstantiated = getDaoScheduleInstanciatedAssembly()
				.findByCriteriasAndPermissions(
						context.getOrderNumber(), 
						context.getAffair(), 
						context.getType(), 
						context.getFamily(), 
						context.getVariants(), 
						context.getMaterial(), 
						context.getModificationDate(), 
						context.getCreationUser(), 
						context.getUfiStatus(), 
						context.getContext(), 
						context.getScheduleState(), 
						context.getScheduleSite(), 
						context.getFamilyMaterialContextVariantPermissionList(),
						ContextSearchScheduleAssembly.PERMISSION_DELIMITER,
						context.getPagination());
			
			if (contextSearchSchedule.getPagination() != null) {
				// update pagination list complete size information
				contextSearchSchedule.getPagination().setScheduleCompleteListSize((int) listInstantiated.getTotalElements());
			}
			
			// Get the corresponding Edition Bo for each instanciated schedule
			for (BoInstanciatedScheduleAssembly schedule : listInstantiated) {
				List<BoScheduleEdition> listEdition = getDaoScheduleEdition()
						.findByInstanciatedScheduleFilePath(schedule.getInstanciatedSchedule().getFilePath());
				mapScheduleInstanciated.put(schedule.getInstanciatedSchedule(), listEdition);
			}
		} catch ( Exception e) {
			logger.error("Its seems that there is no published schedules", e);
		}
		return mapScheduleInstanciated;
	}
	
	@Override
	public Map<BoInstanciatedSchedule, List<BoScheduleEdition>> getAllScheduleModificationDate(
			ContextSearchSchedule contextSearchSchedule) {
		ContextSearchScheduleAssembly context = getWorkingContext(contextSearchSchedule);
		Map<BoInstanciatedSchedule, List<BoScheduleEdition>> mapScheduleInstanciated = new LinkedHashMap<BoInstanciatedSchedule, List<BoScheduleEdition>>();
		try {
			// Fill the list of instanciated schedule according to pagination applicated
			Page<BoInstanciatedScheduleAssembly> listInstantiated = getDaoScheduleInstanciatedAssembly()
				.findByCriteriasAndPermissionsModificationDate(
						context.getOrderNumber(), 
						context.getAffair(), 
						context.getType(), 
						context.getFamily(), 
						context.getVariants(), 
						context.getMaterial(), 
						context.getModificationDate(), 
						context.getCreationUser(), 
						context.getUfiStatus(), 
						context.getContext(), 
						context.getScheduleState(), 
						context.getScheduleSite(), 
						context.getFamilyMaterialContextVariantPermissionList(),
						ContextSearchScheduleAssembly.PERMISSION_DELIMITER,
						context.getPagination());
			
			if (contextSearchSchedule.getPagination() != null) {
				// update pagination list complete size information
				contextSearchSchedule.getPagination().setScheduleCompleteListSize((int) listInstantiated.getTotalElements());
			}
			
			// Get the corresponding Edition Bo for each instanciated schedule
			for (BoInstanciatedScheduleAssembly schedule : listInstantiated) {
				List<BoScheduleEdition> listEdition = getDaoScheduleEdition()
						.findByInstanciatedScheduleFilePath(schedule.getInstanciatedSchedule().getFilePath());
				mapScheduleInstanciated.put(schedule.getInstanciatedSchedule(), listEdition);
			}
		} catch ( Exception e) {
			logger.error("Its seems that there is no published schedules", e);
		}
		return mapScheduleInstanciated;
	}
	
	@Override
	public Map<BoInstanciatedSchedule, List<BoScheduleEdition>> getScheduleByOrderAndScheduleState(
			String order, String scheduleState) {
		Map<BoInstanciatedSchedule, List<BoScheduleEdition>> mapScheduleInstanciated 
			= new LinkedHashMap<BoInstanciatedSchedule, List<BoScheduleEdition>>();
		
		List<BoInstanciatedScheduleAssembly> listInstantiated = getDaoScheduleInstanciatedAssembly()
				.findByOrderNumberAndScheduleState(order, scheduleState);
		
		for (BoInstanciatedScheduleAssembly schedule : listInstantiated) {
			List<BoScheduleEdition> listEdition = getDaoScheduleEdition()
					.findByInstanciatedScheduleFilePath(schedule.getInstanciatedSchedule().getFilePath());
			mapScheduleInstanciated.put(schedule.getInstanciatedSchedule(), listEdition);
		}
		return mapScheduleInstanciated;
	}

	@Override
	public Map<BoInstanciatedSchedule, List<BoScheduleEdition>> getScheduleById(Long scheduleId) {
		BoInstanciatedScheduleAssembly bo = getDaoScheduleInstanciatedAssembly().findByInstanciatedScheduleId(scheduleId);
		return getScheduleByBoInstanciatedSchedule(bo);
	}

	private Map<BoInstanciatedSchedule, List<BoScheduleEdition>> getScheduleByBoInstanciatedSchedule(
			BoInstanciatedScheduleAssembly bo) {
		Map<BoInstanciatedSchedule, List<BoScheduleEdition>> mapScheduleInstanciated = null;
		if (bo != null) {
			List<BoScheduleEdition> listEdition = getDaoScheduleEdition()
					.findByInstanciatedScheduleFilePath(bo.getInstanciatedSchedule().getFilePath());
			mapScheduleInstanciated = new LinkedHashMap<BoInstanciatedSchedule, List<BoScheduleEdition>>();
			mapScheduleInstanciated.put(bo.getInstanciatedSchedule(), listEdition);
		} 
		return mapScheduleInstanciated;
	}

	@Override
	public Map<BoInstanciatedSchedule, List<BoScheduleEdition>> getSchedulesNotInEdition(ContextSearchSchedule contextSearchSchedule) {
		ContextSearchScheduleAssembly context = getWorkingContext(contextSearchSchedule);
		
		// Retrieve all schedules currently in edition
		List<String> listSchedulesInEdition = getDaoScheduleEdition().findInstanciatedScheduleFilePathInEdition();
		// If empty list, set it to null for avoid Hibernate error on IN condition
		if (listSchedulesInEdition != null && listSchedulesInEdition.size() == 0) {
			listSchedulesInEdition = null;
		}
		
		// Retrieve all schedules not in edition
		Page<BoInstanciatedScheduleAssembly> listInstanciatedSchedulesAssemblyNotInEdition = getDaoScheduleInstanciatedAssembly()
				.findByCriteriasAndNotInEditionList(
						context.getOrderNumber(), 
						context.getAffair(), 
						context.getType(), 
						context.getFamily(), 
						context.getVariants(), 
						context.getMaterial(), 
						context.getModificationDate(), 
						context.getCreationUser(), 
						context.getUfiStatus(), 
						context.getContext(), 
						context.getScheduleState(), 
						context.getScheduleSite(),
						listSchedulesInEdition, 
						context.getPagination());
		
		if (contextSearchSchedule.getPagination() != null) {
			// update pagination list complete size information
			contextSearchSchedule.getPagination().setScheduleCompleteListSize(
					(int) listInstanciatedSchedulesAssemblyNotInEdition.getTotalElements());
		}
		
		// Construct map for display purpose
		Map<BoInstanciatedSchedule, List<BoScheduleEdition>> mapSchedules = 
				new LinkedHashMap<BoInstanciatedSchedule, List<BoScheduleEdition>>();
		if (listInstanciatedSchedulesAssemblyNotInEdition != null && listInstanciatedSchedulesAssemblyNotInEdition.getTotalElements() > 0){
			for (BoInstanciatedScheduleAssembly BoInstanciatedScheduleAssembly : listInstanciatedSchedulesAssemblyNotInEdition) {
				mapSchedules.put(BoInstanciatedScheduleAssembly.getInstanciatedSchedule(), null);
			}
		}

		return mapSchedules;
	}

	@Override
	public Map<BoInstanciatedSchedule, List<BoScheduleEdition>> getSchedulesInUfiStatus(
			List<String> listUfiStatus, ContextSearchSchedule contextSearchSchedule) {
		ContextSearchScheduleAssembly context = getWorkingContext(contextSearchSchedule);
		
		// Retrieve all schedules instances according to ufi status
		Page<BoInstanciatedScheduleAssembly> listInstantiated = getDaoScheduleInstanciatedAssembly()
			.findByCriteriasAndUfiStatusInList(
					context.getOrderNumber(), 
					context.getAffair(), 
					context.getType(), 
					context.getFamily(), 
					context.getVariants(), 
					context.getMaterial(), 
					context.getModificationDate(), 
					context.getCreationUser(), 
					context.getUfiStatus(), 
					context.getContext(), 
					context.getScheduleState(), 
					context.getScheduleSite(), 
					listUfiStatus,
					context.getPagination());
		
		if (contextSearchSchedule.getPagination() != null) {
			// update pagination list complete size information
			contextSearchSchedule.getPagination().setScheduleCompleteListSize((int) listInstantiated.getTotalElements());
		}
		
		// Construct map for display purpose
		Map<BoInstanciatedSchedule, List<BoScheduleEdition>> mapSchedules = new LinkedHashMap<BoInstanciatedSchedule, List<BoScheduleEdition>>();
		for (BoInstanciatedScheduleAssembly boSchedule : listInstantiated) {
			mapSchedules.put(boSchedule.getInstanciatedSchedule(), null);
		}

		return mapSchedules;
	}

	@Override
	public Map<BoInstanciatedSchedule, List<BoScheduleEdition>> getSchedulesNotClosedInEdition(ContextSearchSchedule contextSearchSchedule) {
		ContextSearchScheduleAssembly context = getWorkingContext(contextSearchSchedule);
		
		// Retrieve all schedules currently in edition
		List<BoScheduleEdition> listSchedulesInEdition = getDaoScheduleEdition().findAll();
		Map<String, List<BoScheduleEdition>> mapEditions = new LinkedHashMap<String, List<BoScheduleEdition>>();
		
		for (BoScheduleEdition boScheduleEdition : listSchedulesInEdition) {
			if (mapEditions.get(boScheduleEdition.getInstanciatedScheduleFilePath()) == null) {
				mapEditions.put(boScheduleEdition.getInstanciatedScheduleFilePath(),
						new ArrayList<BoScheduleEdition>());
			}
			mapEditions.get(boScheduleEdition.getInstanciatedScheduleFilePath()).add(
					boScheduleEdition);
		}
		
		List<String> listUfiStatus = new ArrayList<String>();
		listUfiStatus.add(StatusRangeType.CLOSE.toString());
		List<String> instanciatedScheduleInEditionFilePath = new ArrayList<String>(mapEditions.keySet());
		// If empty list, set it to null for avoid Hibernate error on IN condition
		if (instanciatedScheduleInEditionFilePath != null && instanciatedScheduleInEditionFilePath.size() == 0) {
			instanciatedScheduleInEditionFilePath = null;
		}
		
		// Retrieve all schedules not closed and in edition
		Page<BoInstanciatedScheduleAssembly> listInstanciatedSchedulesAssemblyNotClosedAndInEdition = getDaoScheduleInstanciatedAssembly()
				.findByCriteriasAndUfiStatusNotInListAndEditionInList(
						context.getOrderNumber(), 
						context.getAffair(), 
						context.getType(), 
						context.getFamily(), 
						context.getVariants(), 
						context.getMaterial(), 
						context.getModificationDate(), 
						context.getCreationUser(), 
						context.getUfiStatus(), 
						context.getContext(), 
						context.getScheduleState(), 
						context.getScheduleSite(), 
						listUfiStatus,
						instanciatedScheduleInEditionFilePath,
						context.getPagination());
		
		if (contextSearchSchedule.getPagination() != null) {
			// update pagination list complete size information
			contextSearchSchedule.getPagination().setScheduleCompleteListSize((int) listInstanciatedSchedulesAssemblyNotClosedAndInEdition.getTotalElements());
		}
		
		// Construct map for display purpose
		Map<BoInstanciatedSchedule, List<BoScheduleEdition>> mapSchedules = new LinkedHashMap<BoInstanciatedSchedule, List<BoScheduleEdition>>();
		for (BoInstanciatedScheduleAssembly BoInstanciatedScheduleAssembly : listInstanciatedSchedulesAssemblyNotClosedAndInEdition) {
			mapSchedules.put(BoInstanciatedScheduleAssembly.getInstanciatedSchedule(), mapEditions
					.get(BoInstanciatedScheduleAssembly.getInstanciatedSchedule().getFilePath()));	
		}
		return mapSchedules;
	}

	private ContextSearchScheduleAssembly getWorkingContext(ContextSearchSchedule context) {
		if (context instanceof ContextSearchScheduleAssembly) {
			return (ContextSearchScheduleAssembly) context;
		} else {
			return new ContextSearchScheduleAssembly(context);
		}
	}

	public DaoScheduleEdition getDaoScheduleEdition() {
		return daoScheduleEdition;
	}

	public void setDaoScheduleEdition(DaoScheduleEdition daoScheduleEdition) {
		this.daoScheduleEdition = daoScheduleEdition;
	}

	public DaoInstanciatedScheduleAssembly getDaoScheduleInstanciatedAssembly() {
		return daoScheduleInstanciatedAssembly;
	}

	public void setDaoScheduleInstanciatedAssembly(
			DaoInstanciatedScheduleAssembly daoScheduleInstanciatedAssembly) {
		this.daoScheduleInstanciatedAssembly = daoScheduleInstanciatedAssembly;
	}

}
