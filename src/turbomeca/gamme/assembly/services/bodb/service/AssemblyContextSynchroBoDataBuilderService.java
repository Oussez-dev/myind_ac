package turbomeca.gamme.assembly.services.bodb.service;

import org.springframework.stereotype.Service;

import turbomeca.gamme.assembly.services.enumeration.AssemblyScheduleType;
import turbomeca.gamme.ecran.services.bodb.service.ISpecificContextSynchroBoDataBuilderService;
import turbomeca.gamme.ecran.services.common.bean.ContextUser;
import turbomeca.gamme.ecran.services.runtime.bean.ContextSynchronizeBoData;
import turbomeca.gamme.ecran.services.schedule.bo.BoInstanciatedSchedule;

@Service
public class AssemblyContextSynchroBoDataBuilderService implements ISpecificContextSynchroBoDataBuilderService {

	@Override
	public void updateContextSynchronizeBoDataWithSpecificInfos(
			ContextSynchronizeBoData contextToUpdate, BoInstanciatedSchedule boInstanciatedSchedule) {
		// Insert Assembly specific data in the context
		String scheduleTypeString = boInstanciatedSchedule.getScheduleIdentificationAssembly().getType();
		if (scheduleTypeString.equalsIgnoreCase(AssemblyScheduleType.ASSEMBLY.toString())) {
			contextToUpdate.setDocumentDoctype(AssemblyScheduleType.ASSEMBLY.toString());
		} else if (scheduleTypeString.equalsIgnoreCase(AssemblyScheduleType.DISASSEMBLY.toString())) {
			contextToUpdate.setDocumentDoctype(AssemblyScheduleType.DISASSEMBLY.toString());
		}
	}

	@Override
	public void updateContextSynchronizeBoDataWithSpecificInfos(
			ContextSynchronizeBoData contextToUpdate, ContextUser contextUser) {
		// Insert Assembly specific data in the context
		AssemblyScheduleType scheduleType = AssemblyScheduleType.values()[contextUser.getRangeType()];
		contextToUpdate.setDocumentDoctype(scheduleType.toString());
	}
	
}
