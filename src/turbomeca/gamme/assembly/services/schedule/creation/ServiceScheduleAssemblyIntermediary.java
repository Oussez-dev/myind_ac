package turbomeca.gamme.assembly.services.schedule.creation;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import turbomeca.gamme.assembly.services.schedule.creation.utils.FileAssemblyUtils;
import turbomeca.gamme.ecran.services.common.enumtypes.ScheduleState;
import turbomeca.gamme.ecran.services.publication.utils.IFolderService;
import turbomeca.gamme.ecran.services.schedule.bean.ContextScheduleCreation;
import turbomeca.gamme.ecran.services.schedule.bean.ContextUserServer;
import turbomeca.gamme.ecran.services.schedule.service.creation.IServiceScheduleIntermediaryDao;
import turbomeca.gamme.ecran.services.schedule.service.creation.ScheduleCreationDTO;
import turbomeca.gamme.ecran.services.schedule.service.exception.CreateScheduleIntermediaryException;


public class ServiceScheduleAssemblyIntermediary implements IAssemblyServiceScheduleIntermediary {
	private static Logger logger = Logger.getLogger(ServiceScheduleAssemblyIntermediary.class);

	private IServiceScheduleIntermediaryDao dao;
	
	@Autowired
	private IFolderService folderService;

	@Override
	public ScheduleCreationDTO searchScheduleIntermediary(ContextScheduleCreation scheduleCreation, ContextUserServer contextUser) throws Exception {
		logger.debug("ServiceScheduleIntermediary - searchScheduleIntermediary");
		ScheduleCreationDTO dto = new ScheduleCreationDTO();
		dto.setVariant(scheduleCreation.getVariant());
		dto.setRangeType(scheduleCreation.getScheduleType());
		dto.setContext(scheduleCreation.getContext());
		dto.setConf(scheduleCreation.getConfiguration());
		dto.setPn(scheduleCreation.getPartNumber());
		dto.setScheduleGenericFile(scheduleCreation.getDirectorySchedulePath());
		dto.setLanguage(contextUser.getLanguage());
		dto.setSite(contextUser.getSite());
		dto.setReferential(ScheduleState.valueOf(contextUser.getReferential()));
		dto.setSuffix(scheduleCreation.getSuffix());
		dto = getDao().searchScheduleIntermediary(dto, scheduleCreation);
		return dto;
	}

	public void setDao(IServiceScheduleIntermediaryDao dao) {
		this.dao = dao;
	}

	public IServiceScheduleIntermediaryDao getDao() {
		return dao;
	}

	@Override
	public File buildScheduleIntermediary(ContextScheduleCreation contextSchedule) throws CreateScheduleIntermediaryException {
		String intermediaryPath = FileAssemblyUtils.buildPathIntermediary(folderService, contextSchedule);
		File intermediaryFile = new File(intermediaryPath);
		logger.debug("ServiceScheduleIntermediary - Search Schedule Intermediary"+ intermediaryFile.getPath());
		if (!intermediaryFile.exists()) {
			throw new CreateScheduleIntermediaryException(CreateScheduleIntermediaryException.EXCEPTION_SCHEDULE_NOT_FOUND,
					"Unable to load intermediary file", new IOException("File " + intermediaryFile + " does not exists"));
		} 
		return intermediaryFile;
	}

}
