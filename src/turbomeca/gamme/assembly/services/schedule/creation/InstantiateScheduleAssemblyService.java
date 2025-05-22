package turbomeca.gamme.assembly.services.schedule.creation;

import java.io.File;
import java.util.Calendar;
import java.util.Map;

import javax.xml.transform.TransformerException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import turbomeca.gamme.assembly.services.schedule.creation.bean.ContextScheduleCreationAssembly;
import turbomeca.gamme.assembly.services.schedule.creation.exception.CreateScheduleAssemblyException;
import turbomeca.gamme.assembly.services.schedule.creation.utils.FileAssemblyUtils;
import turbomeca.gamme.ecran.services.common.enumtypes.StatusRangeType;
import turbomeca.gamme.ecran.services.common.utils.schedulestate.ScheduleStateUtils;
import turbomeca.gamme.ecran.services.publication.constants.PublicationResultStatus;
import turbomeca.gamme.ecran.services.publication.exception.PublicationException;
import turbomeca.gamme.ecran.services.schedule.bean.ContextScheduleCreation;
import turbomeca.gamme.ecran.services.schedule.bean.ContextUserServer;
import turbomeca.gamme.ecran.services.schedule.bo.BoInstanciatedSchedule;
import turbomeca.gamme.ecran.services.schedule.bo.BoInstanciatedScheduleAssembly;
import turbomeca.gamme.ecran.services.schedule.bo.BoScheduleCreation;
import turbomeca.gamme.ecran.services.schedule.bo.BoScheduleInstance;
import turbomeca.gamme.ecran.services.schedule.bo.BoScheduleModification;
import turbomeca.gamme.ecran.services.schedule.dao.DaoInstanciatedSchedule;
import turbomeca.gamme.ecran.services.schedule.service.creation.IInstantiateScheduleService;

/**
 * @author lrosique
 *
 */ 
@Service(value = "turbomeca.gamme.ecran.services.schedule.service.creation.IInstantiateScheduleService")
@Transactional
public class InstantiateScheduleAssemblyService implements IInstantiateScheduleService {

	private static final Logger logger = Logger.getLogger(InstantiateScheduleAssemblyService.class);

	@Autowired
	private AssemblyTransformationXSLTService xsltDao;

	@Autowired
	private DaoInstanciatedSchedule daoScheduleInstanciated;
	

	/**
	 * Instantiate a schedule and return true if succeeded
	 * 
	 * @param contextScheduleCreation
	 * @return boolean
	 * @throws TransformerException 
	 * @throws Exception
	 */
	@Override
	public boolean instantiateSchedule(ContextUserServer contextUserServer, ContextScheduleCreation contextScheduleCreation) throws CreateScheduleAssemblyException, TransformerException {
		logger.debug("InstantiateScheduleService - instantiateSchedule");
		boolean success = true;
		boolean successSaveInDatabase = false;
		BoInstanciatedSchedule boInstanciated = null;
		try {
			// Save new schedule in DBB and get instanciatedBO
			// Save Schedule ID, schedule path and schedule creation date in creation context			
			boInstanciated = saveInstantiatedScheduleInDataBase(contextUserServer, contextScheduleCreation);
			successSaveInDatabase = true;
			
			// Create instantiated schedule XML
			xsltDao.applyTransformation(contextUserServer, contextScheduleCreation);
		} catch (Exception e) {
			logger.error("Error during instanciate schedule ", e);
			success = false;
			try {
				// if error during xsl transfo, delete bo in database
				if (boInstanciated != null) {
					daoScheduleInstanciated.delete(boInstanciated);
				}
			} catch (Exception e1) {
				logger.error("Error during delete of false instanciated bo in database, after error during instanciate schedule ", e1);
			}
			// Throw exception corresponding to error : XSL treatment or dataBase
			if (successSaveInDatabase) {
				throw new TransformerException(e.getMessage(), e);
			} else {
				throw new CreateScheduleAssemblyException(e.getMessage());
			}
		}
		return success;
	}

	
	
	@Override
	public String instantiateScheduleAsStream(
			ContextUserServer contextUserServer,
			ContextScheduleCreation contextScheduleCreation) {
		
		logger.debug("InstantiateScheduleService - instantiateSchedule");
		String instantiatedStream = null;
		try {
			instantiatedStream = xsltDao.applyTransformationToStream(contextUserServer, contextScheduleCreation);
		} catch (Exception e) {
			logger.error("InstantiateScheduleExtendedService - instantiateSchedule - ", e);
			return null;
		}
		return instantiatedStream;
	}
	
	
	/***
	 * @Deprecated as not yet implemented nor used for assembly but useful for JUNIT
	 */
	@Deprecated
	@Override
	public String instantiateScheduleAsStream(String rawIntermediaryXmlContent,
			Map<String, Object> instanciationParameters) throws PublicationException {
		String instantiatedStream = null;
		try {
			// FIXME : for JUnit
			if (xsltDao == null) {
				xsltDao = new AssemblyTransformationXSLTService();
			}
		
			instantiatedStream = xsltDao.applyTransformationToStream(rawIntermediaryXmlContent, instanciationParameters);
		} catch (TransformerException e) {
			throw new PublicationException(PublicationResultStatus.ERROR_CODE_XML_PUBLICATION,
					"Unable to run Instantiated XSL trasformation", e);
		}
		
		return instantiatedStream;
	}

	
	private BoInstanciatedSchedule saveInstantiatedScheduleInDataBase(ContextUserServer contextUserServer,
			ContextScheduleCreation contextScheduleCreation) {
		// Build schedule instantiation DateTime, save in context
		Calendar now = Calendar.getInstance();
		contextScheduleCreation.setDateCreation(now);
		// Build schedule file and folder path, save in context
		String instanciatedFileAndFolderPath = FileAssemblyUtils.buildInstanciatedFileAndParentFolderPath(contextUserServer, contextScheduleCreation);
		contextScheduleCreation.setScheduleFileAndFolder(instanciatedFileAndFolderPath);
		contextScheduleCreation.setScheduleInstanciatedFile(new File(instanciatedFileAndFolderPath));
		
		ContextScheduleCreationAssembly contextAssembly = (ContextScheduleCreationAssembly) contextScheduleCreation;
		
		BoInstanciatedSchedule boInstanciated = new BoInstanciatedSchedule();
		boInstanciated.setFilePath(instanciatedFileAndFolderPath);
		boInstanciated.setOrderNumber(contextAssembly.getOrder());
		boInstanciated.setScheduleState(ScheduleStateUtils.getScheduleStateString(contextUserServer.getDataContext()));
		boInstanciated.setScheduleVersion(contextAssembly.getVersion());
		boInstanciated.setCurrentOperation("");
		boInstanciated.setReference(contextAssembly.getPartNumber());
		boInstanciated.setHasDoubleValidation(false);
		boInstanciated.setHasNotification(false);
		boInstanciated.setIndustrialValidation(contextAssembly.getIndustrialValidation());
		boInstanciated.setSite(contextUserServer.getSite());
		boInstanciated.setInstances(contextAssembly.getInstances());
		boInstanciated.setIsSapSchedule(contextAssembly.isSapSchedule());

		BoInstanciatedScheduleAssembly boInstanciatedAssembly = new BoInstanciatedScheduleAssembly();
		boInstanciatedAssembly.setInstanciatedSchedule(boInstanciated);
		boInstanciatedAssembly.setContext(contextAssembly.getContext());
		boInstanciatedAssembly.setMaterial(contextAssembly.getMaterial());
		boInstanciatedAssembly.setFamily(contextAssembly.getFamily());
		boInstanciatedAssembly.setVariants(contextAssembly.getVariant());
		boInstanciatedAssembly.setType(contextAssembly.getScheduleType());
		boInstanciatedAssembly.setAffair(contextAssembly.getAffair());
		
		BoScheduleCreation boScheduleCreation = new BoScheduleCreation();
		boScheduleCreation.setCreationDate(contextAssembly.getDateCreation().getTime());
		boScheduleCreation.setCreationLogin(contextUserServer.getLogin());
		boScheduleCreation.setCreationUser(contextUserServer.getName());
		boScheduleCreation.setInstanciatedSchedule(boInstanciated);

		BoScheduleInstance boScheduleInstance = new BoScheduleInstance();
		boScheduleInstance.setEditable(true);
		boScheduleInstance.setUfiStatus(StatusRangeType.INSTANTIATED);
		boScheduleInstance.setInstanciatedSchedule(boInstanciated);

		BoScheduleModification boScheduleModification = new BoScheduleModification();
		boScheduleModification.setInstanciatedSchedule(boInstanciated);
		boScheduleModification.setModificationDate(contextAssembly.getDateCreation().getTime());
		boScheduleModification.setModificationLogin(contextUserServer.getLogin());
		boScheduleModification.setModificationUser(contextUserServer.getName());

		boInstanciated.setScheduleIdentificationAssembly(boInstanciatedAssembly);
		boInstanciated.setScheduleCreation(boScheduleCreation);
		boInstanciated.setScheduleInstance(boScheduleInstance);
		boInstanciated.setScheduleModification(boScheduleModification);
		
		// get the saved instanciated bo
		boInstanciated = daoScheduleInstanciated.save(boInstanciated);
		
		logger.debug("Instanciated result schedule path : "
				+ FileAssemblyUtils.buildInstanciatedPath(contextUserServer, contextScheduleCreation));
		
		// save schedule database Id in context
		contextScheduleCreation.setScheduleDatabaseId(boInstanciated.getId());
		
		return boInstanciated;
	}
}
