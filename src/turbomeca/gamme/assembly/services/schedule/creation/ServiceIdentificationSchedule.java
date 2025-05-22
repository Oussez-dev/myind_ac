package turbomeca.gamme.assembly.services.schedule.creation;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import turbomeca.gamme.assembly.services.schedule.creation.bean.ContextScheduleCreationAssembly;
import turbomeca.gamme.assembly.services.schedule.creation.bean.ContextUserAssemblyServer;
import turbomeca.gamme.assembly.services.schedule.creation.exception.CreateScheduleAssemblyException;
import turbomeca.gamme.ecran.services.common.enumtypes.ScheduleState;
import turbomeca.gamme.ecran.services.schedule.bean.ContextScheduleCreation;
import turbomeca.gamme.ecran.services.schedule.bean.ContextUserServer;
import turbomeca.gamme.ecran.services.schedule.service.creation.IServiceIdentification;
import turbomeca.gamme.ecran.services.schedule.service.creation.IServiceIdentificationDao;
import turbomeca.gamme.ecran.services.schedule.service.creation.ScheduleCreationDTO;


public class ServiceIdentificationSchedule implements IServiceIdentification {

	private static Logger logger = Logger.getLogger(ServiceIdentificationSchedule.class);
	private IServiceIdentificationDao dao;

	@Override
	public ScheduleCreationDTO getSynthesisDirectory(
			ContextUserServer contextUser, ContextScheduleCreation contextSchedule)
			throws Exception {
		
		ScheduleCreationDTO dto = new ScheduleCreationDTO();
		ContextUserAssemblyServer context = (ContextUserAssemblyServer) contextUser;
		if (contextSchedule.getScheduleType().equals("assembly")&& contextSchedule.getContext().equals("new")) {
			dto.setScheduleGenericFile(context.getRepository().getOfficialAssemblyNewPath());
			dto.setSynthesisPublicationScheduleFile(context.getRepository().getSynthesisAssemblyNew());
		} else if (contextSchedule.getScheduleType().equals("assembly")&& contextSchedule.getContext().equals("repaired")) {
			dto.setScheduleGenericFile(context.getRepository().getOfficialAssemblyRepairedPath());
			dto.setSynthesisPublicationScheduleFile(context.getRepository().getSynthesisAssemblyRepaired());
			} else {
			dto.setScheduleGenericFile(context.getRepository().getOfficialDisassemblyPath());
			dto.setSynthesisPublicationScheduleFile(context.getRepository().getSynthesisDisassembly());
		}

		return dto;
	}
	
	
	@Override
	public ScheduleCreationDTO updateEffectivitiesFromPN(ContextUserServer contextUser, ContextScheduleCreation contextSchedule)throws Exception {
		ContextScheduleCreationAssembly schedule = (ContextScheduleCreationAssembly)contextSchedule;
		ScheduleCreationDTO dto = new ScheduleCreationDTO();
		Map<String, List<?>> listParameters = new HashMap<String, List<?>>();
		listParameters = dao.getParametersEffectivitiesFromPN(dto, schedule, contextUser);
			if(listParameters.isEmpty()){
				throw new CreateScheduleAssemblyException(CreateScheduleAssemblyException.EXCEPTION_DATA_MISSING_EFFECTIVITIES);
			}
		dto.setParameters(listParameters);
		return dto;
	}
	
	public void setDao(IServiceIdentificationDao dao) {
		this.dao = dao;
	}

	public IServiceIdentificationDao getDao() {
		return dao;
	}

	@Override
	public ScheduleCreationDTO initParametersEffectivities(ContextUserServer contextUser, ContextScheduleCreation contextSchedule) throws Exception{
		ContextScheduleCreationAssembly schedule = (ContextScheduleCreationAssembly)contextSchedule;
		ScheduleCreationDTO dto = getSynthesisDirectory(contextUser, contextSchedule);
		
		dto.setScheduleDirectoryPath(dto.getScheduleGenericFile());
		dto.setReferential(ScheduleState.valueOf(contextUser.getReferential()));
		logger.debug("initParametersEffectivities - Directory Schedule : "+dto.getScheduleGenericFile());
		Map<String, List<?>> listParameters = new HashMap<String, List<?>>();
		
		// FIXME: dao backend may be BDD (or other...): interface should not require XML synthesis file path to work
		File synthesisPubFile = new File(dto.getSynthesisPublicationScheduleFile());
		listParameters = dao.initParametersEffectivities(contextUser, schedule,synthesisPubFile);
			if(listParameters.isEmpty()){
				throw new CreateScheduleAssemblyException(CreateScheduleAssemblyException.EXCEPTION_DATA_MISSING_EFFECTIVITIES);
			}
		dto.setParameters(listParameters);
		return dto;
	}

}
