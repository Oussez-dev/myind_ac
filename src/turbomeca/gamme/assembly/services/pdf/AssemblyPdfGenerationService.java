package turbomeca.gamme.assembly.services.pdf;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import turbomeca.gamme.ecran.services.schedule.service.instanciated.InstanciatedScheduleService;
import turbomeca.gamme.ecran.services.schedule.service.pdf.config.ReportConfiguration;
import turbomeca.gamme.ecran.services.schedule.service.pdf.service.impl.APdfInterfaceService;
import turbomeca.gamme.ecran.services.schedule.service.pdf.service.impl.ARangePublisher;

@WebService(endpointInterface="turbomeca.gamme.ecran.services.schedule.service.pdf.IPdfInterfaceService")
@Service(value="assemblyPdfInterfaceService") 
public class AssemblyPdfGenerationService extends APdfInterfaceService {

	@Autowired
	InstanciatedScheduleService instanciatedScheduleService;
	
	@Override
	public ARangePublisher createRangePublisher(ReportConfiguration reportConfig) {
		return new AssemblyRangePublisher(reportConfig, instanciatedScheduleService);
	}

}
