package turbomeca.gamme.assembly.services.schedule;

import javax.jws.WebService;

import org.springframework.stereotype.Service;

import turbomeca.gamme.ecran.services.schedule.ScheduleManagerService;

@WebService(serviceName = "ScheduleManagerServiceService",
			targetNamespace = "http://schedule.services.ecran.gamme.turbomeca/",
			endpointInterface = "turbomeca.gamme.ecran.services.schedule.IScheduleManagerService")
@Service(value = "assemblyScheduleManagerService")
public class AssemblyScheduleInterfaceService extends ScheduleManagerService {

}


