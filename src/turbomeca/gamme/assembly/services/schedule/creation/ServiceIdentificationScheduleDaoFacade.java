package turbomeca.gamme.assembly.services.schedule.creation;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import turbomeca.gamme.ecran.services.schedule.bean.ContextScheduleCreation;
import turbomeca.gamme.ecran.services.schedule.bean.ContextUserServer;
import turbomeca.gamme.ecran.services.schedule.service.creation.IServiceIdentificationDao;
import turbomeca.gamme.ecran.services.schedule.service.creation.ScheduleCreationDTO;


/**
 * This Facade allow external configuration of backend persistency for access to publication synthesis
 * 
 * The switch is performed through a configuration key injected by spring
 * 
 * 
 * @author ahermo
 *
 */
@Service(value="turbomeca.gamme.ecran.services.schedule.service.creation.IServiceIdentificationDao")
public class ServiceIdentificationScheduleDaoFacade implements IServiceIdentificationDao {

	// Spring injected properties
	@Autowired
	@Qualifier("IServiceIdentificationDaoXml")
	private IServiceIdentificationDao xmlDao;
	
	@Autowired
	@Qualifier("IServiceIdentificationDaoBdd")
	private IServiceIdentificationDao bddDao;
	
	@Value("${server.config.instanciated.synthesis.bdd}")
	private Boolean isBddBackend;
	
	@Value("${server.config.instanciated.synthesis.bdd}")
	private String isBddBackendString;
	// -- Spring

	private IServiceIdentificationDao serviceIdentificationDao;
	
	@PostConstruct
	public void postConstruct() {
		if(isBddBackend) {
			serviceIdentificationDao = bddDao;
		} else {
			serviceIdentificationDao = xmlDao;
		}
	}

	@Override
	public Map<String, List<?>> initParametersEffectivities(ContextUserServer contextUser,
			ContextScheduleCreation contextSchedule, File synthesis) throws Exception {
		return serviceIdentificationDao.initParametersEffectivities(contextUser, contextSchedule, synthesis);
	}

	@Override
	public Map<String, List<?>> getParametersEffectivitiesFromPN(ScheduleCreationDTO dto,
			ContextScheduleCreation contextSchedule, ContextUserServer contextUser) throws Exception {
		return serviceIdentificationDao.getParametersEffectivitiesFromPN(dto, contextSchedule, contextUser);
	}
	
	public IServiceIdentificationDao getXmlDao() {
		return xmlDao;
	}

	public void setXmlDao(IServiceIdentificationDao xmlDao) {
		this.xmlDao = xmlDao;
	}

	public IServiceIdentificationDao getBddDao() {
		return bddDao;
	}

	public void setBddDao(IServiceIdentificationDao bddDao) {
		this.bddDao = bddDao;
	}

	public Boolean getIsBddBackend() {
		return isBddBackend;
	}

	public void setIsBddBackend(Boolean isBddBackend) {
		this.isBddBackend = isBddBackend;
	}

}
