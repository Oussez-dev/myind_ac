package turbomeca.gamme.assembly.services.schedule.creation;

import java.io.File;

import turbomeca.gamme.ecran.services.schedule.bean.ContextScheduleCreation;
import turbomeca.gamme.ecran.services.schedule.service.creation.IServiceScheduleIntermediary;
import turbomeca.gamme.ecran.services.schedule.service.exception.CreateScheduleIntermediaryException;

public interface IAssemblyServiceScheduleIntermediary extends IServiceScheduleIntermediary {
	
	@Override
	public File buildScheduleIntermediary(ContextScheduleCreation contextSchedule) throws CreateScheduleIntermediaryException;
}
