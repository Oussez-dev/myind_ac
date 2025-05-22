package turbomeca.gamme.assembly.services.bodb.service;

import java.io.File;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.Future;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.exolab.castor.xml.Unmarshaller;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;

import turbomeca.gamme.assembly.services.bodb.bobuilder.BoDbScheduleBuilder;
import turbomeca.gamme.assembly.services.enumeration.AssemblyScheduleType;
import turbomeca.gamme.assembly.services.model.data.Assembly;
import turbomeca.gamme.assembly.services.model.data.Disassembly;
import turbomeca.gamme.assembly.services.model.data.ScheduleType;
import turbomeca.gamme.ecran.services.bodb.bo.BoSchedule;
import turbomeca.gamme.ecran.services.bodb.cache.ABoDataCache;
import turbomeca.gamme.ecran.services.bodb.service.ASynchronizationScheduleDatabaseService;
import turbomeca.gamme.ecran.services.bodb.service.BoDbException;
import turbomeca.gamme.ecran.services.bodb.service.ISynchronizationScheduleDatabaseService;
import turbomeca.gamme.ecran.services.common.constants.GlobalConstants;
import turbomeca.gamme.ecran.services.common.utils.misc.PerformanceTraces;
import turbomeca.gamme.ecran.services.runtime.bean.ContextSynchronizeBoData;

@Service
@EnableAsync
public class AssemblySynchronizationScheduleDatabaseService extends ASynchronizationScheduleDatabaseService 
	implements ISynchronizationScheduleDatabaseService {

	private static Logger logger = Logger.getLogger(AssemblySynchronizationScheduleDatabaseService.class);

	@Override
	@Async
	public Future<Boolean> synchronizeScheduleBODataInDatabase(ContextSynchronizeBoData contextSynchroBOData) {
		logger.debug("Begin synchronizeSchedule");
		boolean success = false;
		
		if (contextSynchroBOData == null) {
			logger.error("ContextSynchronizeBoData can not be null to synchronize a schedule !");
			return new AsyncResult<Boolean>(success);
		}
		
		try {
			PerformanceTraces.enable();
			PerformanceTraces perfo = new PerformanceTraces();
			
			// Get editing context : list of subPhases for Assembly
			ArrayList<String> listIdSubPhases = null;
			if(contextSynchroBOData.getContextEditing() != null && contextSynchroBOData.getContextEditing().getObjectEdited() != null) {
				listIdSubPhases = new ArrayList<String>();
				Collections.addAll(listIdSubPhases, contextSynchroBOData.getContextEditing().getObjectEdited());
			}
			
			// Read XML schedule file
			perfo.startPerfTrace();
			File scheduleFile = new File(contextSynchroBOData.getScheduleFilePath());
			String inputStream = FileUtils.readFileToString(scheduleFile, GlobalConstants.MODEL_ENCODING);
			StringReader scheduleReader = new StringReader(inputStream);
			perfo.stopPerfTrace("Read XML schedule file");
			
			// Unmarshall file and build Bo
			perfo.startPerfTrace();
			AssemblyScheduleType scheduleType = AssemblyScheduleType.valueOf(contextSynchroBOData.getDocumentDoctype());
		    ScheduleType schedule = unmarshallSchedule(scheduleReader, scheduleType);
		    perfo.stopPerfTrace("Unmarshall schedule file");
			perfo.startPerfTrace();
			BoSchedule boSchedule = BoDbScheduleBuilder.buildScheduleBean(schedule, null, listIdSubPhases, contextSynchroBOData);
			perfo.stopPerfTrace("Build Bo");
			perfo.displayResults();
			
		    if (boSchedule != null) {
		    	getBoDbScheduleManager().createOrUpdate(boSchedule);
		    	success = true;
		    }
		    
		} catch (BoDbException e) {
			logger.error("Error during BO data create or update schedule."
					+ " Schedule file path : " + contextSynchroBOData.getScheduleFilePath(), e);
		} catch (Exception e) {
			logger.error("Error during BO data create or update schedule."
					+ " Schedule file path : " + contextSynchroBOData.getScheduleFilePath(), e);
		} finally {
			ABoDataCache.resetAllCaches();
		}
		return new AsyncResult<Boolean>(success);
	}
	
	/**
	 * Manage Assembly and Disassembly schedule type
	 * @param reader
	 * @param isAssemblySchedule
	 * @return
	 */
	private ScheduleType unmarshallSchedule(Reader reader, AssemblyScheduleType scheduleType) {
		ScheduleType schedule = null;
		if (scheduleType.equals(AssemblyScheduleType.ASSEMBLY)) {
			schedule = (ScheduleType) unmarshall(reader, Assembly.class);
		} else if (scheduleType.equals(AssemblyScheduleType.DISASSEMBLY)) {
			schedule = (ScheduleType) unmarshall(reader, Disassembly.class);
		}
		return schedule;
	}
	
	@Override
	public BoSchedule loadSchedule(File scheduleFile, ContextSynchronizeBoData contextSynchroBOData, String sn) {
		BoSchedule boSchedule = null;
		try {
			String inputStream = FileUtils.readFileToString(scheduleFile, GlobalConstants.MODEL_ENCODING);
			boSchedule = loadSchedule(inputStream, contextSynchroBOData, sn);
		} catch (Exception e) {
			logger.error("Error during load schedule data", e);
		}
		return boSchedule;
	}
	
	@Override
	public BoSchedule loadSchedule(String scheduleText, ContextSynchronizeBoData contextSynchroBOData, String sn) {
		BoSchedule boSchedule = null;
		logger.debug("Begin loadSchedule Data");
		try {
			// Read XML schedule file
			StringReader scheduleReader = new StringReader(scheduleText);
		    
			// Unmarshall file and build Bo
			AssemblyScheduleType scheduleType = AssemblyScheduleType.valueOf(contextSynchroBOData.getDocumentDoctype());
		    ScheduleType schedule = unmarshallSchedule(scheduleReader, scheduleType);
 		    boSchedule = BoDbScheduleBuilder.buildScheduleBean(schedule, null, null, contextSynchroBOData);
 			
		} catch (Exception e) {
			logger.error("Error during load schedule data", e);
		} finally {
			ABoDataCache.resetAllCaches();
		}
		
		return boSchedule;
	}
	
	/**
	 * 
	 * @param reader
	 * @param objectClass
	 * @return
	 */
	private Object unmarshall(Reader reader, Class<?> objectClass) {
		Object objectModel = null;
		try {
			InputSource xmlSource = new InputSource(reader);
			Unmarshaller unmar = new Unmarshaller(objectClass);
			unmar.setWhitespacePreserve(false);
			objectModel = unmar.unmarshal(xmlSource);
		} catch (Exception e) {
			logger.error("Error : convertion from xml to model failed", e);
		} 
		return objectModel;
	}

}
