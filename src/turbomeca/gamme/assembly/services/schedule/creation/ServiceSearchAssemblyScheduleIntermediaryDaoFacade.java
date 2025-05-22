package turbomeca.gamme.assembly.services.schedule.creation;

import java.io.File;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;

import turbomeca.gamme.ecran.services.schedule.bean.ContextScheduleCreation;
import turbomeca.gamme.ecran.services.schedule.service.creation.IServiceScheduleIntermediaryDao;
import turbomeca.gamme.ecran.services.schedule.service.creation.ScheduleCreationDTO;

public class ServiceSearchAssemblyScheduleIntermediaryDaoFacade implements IServiceScheduleIntermediaryDao {

	// Spring injected properties
	private IServiceScheduleIntermediaryDao xmlSearchService;
	private IServiceScheduleIntermediaryDao bddSearchService;
	private Boolean isBddBackend;
	// -- Spring
	
	private static Logger logger = Logger.getLogger(ServiceSearchAssemblyScheduleIntermediaryDaoFacade.class);
	
	private IServiceScheduleIntermediaryDao searchService;
	
	@PostConstruct
	public void postConstruct() {
		if(isBddBackend) {
			logger.info("Using BDD persistence for Intermediary search service");
			searchService = bddSearchService;
		} else {
			logger.info("Using XML persistence for Intermediary search service");
			searchService = xmlSearchService;
		}
	}
	
	@Override
	public ScheduleCreationDTO searchScheduleIntermediary(ScheduleCreationDTO scheduleCreation,
			ContextScheduleCreation context) throws Exception {
		return searchService.searchScheduleIntermediary(scheduleCreation, context);
	}

	@Override
	public List<String> getInformationFromScheduleIntermediary(String parentNode, String node, String attrNode,
			File fileIntermediary) throws Exception {
		return searchService.getInformationFromScheduleIntermediary(parentNode, node, attrNode, fileIntermediary);
	}

	public IServiceScheduleIntermediaryDao getXmlSearchService() {
		return xmlSearchService;
	}

	public void setXmlSearchService(IServiceScheduleIntermediaryDao xmlSearchService) {
		this.xmlSearchService = xmlSearchService;
	}

	public IServiceScheduleIntermediaryDao getBddSearchService() {
		return bddSearchService;
	}

	public void setBddSearchService(IServiceScheduleIntermediaryDao bddSearchService) {
		this.bddSearchService = bddSearchService;
	}
	
	public Boolean getIsBddBackend() {
		return isBddBackend;
	}

	public void setIsBddBackend(Boolean isBddBackend) {
		this.isBddBackend = isBddBackend;
	}
}
