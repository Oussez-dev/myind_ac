package turbomeca.gamme.assembly.services.runtime;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import turbomeca.gamme.assembly.services.constants.AssemblyXsltConstants;
import turbomeca.gamme.assembly.services.schedule.config.AssemblyRepositoryConfig;
import turbomeca.gamme.assembly.services.schedule.creation.bean.ContextScheduleCreationAssembly;
import turbomeca.gamme.assembly.services.schedule.creation.exception.CreateScheduleAssemblyException;
import turbomeca.gamme.assembly.services.schedule.creation.utils.AssemblyGlobalServices;
import turbomeca.gamme.ecran.services.common.bean.ContextRange;
import turbomeca.gamme.ecran.services.common.bean.ContextUser;
import turbomeca.gamme.ecran.services.common.bean.ResultData;
import turbomeca.gamme.ecran.services.common.constants.GlobalConstants;
import turbomeca.gamme.ecran.services.common.constants.PropertyConstant;
import turbomeca.gamme.ecran.services.common.constants.ResultStatus;
import turbomeca.gamme.ecran.services.common.enumtypes.StatusType;
import turbomeca.gamme.ecran.services.common.exceptions.RangeIoException;
import turbomeca.gamme.ecran.services.common.utils.transformer.ModelTransformation;
import turbomeca.gamme.ecran.services.common.utils.xml.XmlDocumentEditor;
import turbomeca.gamme.ecran.services.runtime.bean.ContextApplicabilities;
import turbomeca.gamme.ecran.services.runtime.bean.ContextData;
import turbomeca.gamme.ecran.services.runtime.service.impl.RuntimeInterfaceService;
import turbomeca.gamme.ecran.services.schedule.constants.XsltConstants;
import turbomeca.gamme.ecran.services.schedule.service.creation.IServiceScheduleIntermediaryDao;
import turbomeca.gamme.ecran.services.schedule.service.creation.ScheduleCreationDTO;
import turbomeca.gamme.ecran.services.schedule.service.pdf.exception.ReportGenerationException;

@WebService(serviceName = "RuntimeInterfaceServiceService",
			targetNamespace = "http://impl.service.runtime.services.ecran.gamme.turbomeca/",
			endpointInterface = "turbomeca.gamme.ecran.services.runtime.IRuntimeInterfaceService"
		)
@Service(value = "assemblyRuntimeInterfaceService")
public class AssemblyRuntimeInterfaceService extends RuntimeInterfaceService {

	private static Logger logger = Logger.getLogger(AssemblyRuntimeInterfaceService.class);
	
	@Autowired (required = false)
	@Qualifier (value = "IServiceScheduleIntermediaryDaoFacade")
	private IServiceScheduleIntermediaryDao instanciateService;


	//FIXME : Adapt with BDD When publication will be ready
	@Override
	public ContextData getExternalContextData(ContextUser cu, ContextApplicabilities contextApp, int scheduleType) {
		ScheduleCreationDTO scheduleCreation=null;
		ContextData contextData = new ContextData();
		ResultData resultData = new ResultData();
		ContextRange contextRange = new ContextRange();
		try {
			
			contextRange.setFamily(contextApp.getMaterial());
			ModelTransformation modelTransformation = new ModelTransformation(AssemblyXsltConstants.XSLT_CREATE_RANGE.value());
	
			//Get Synthesis 
			AssemblyRepositoryConfig repo = (AssemblyRepositoryConfig) getRepository(cu);
			String directoryPath = repo.getOfficialDirectory(scheduleType, contextApp.getContext());
			String synthesisPath = repo.getSynthesisPath(scheduleType, contextApp.getContext());
	
			//Schedule Creation Dto 
			File synthesisFile = new File(synthesisPath);
			if(synthesisFile.exists()){
				logger.debug("externalScheduleData - Synthesis File Found "+synthesisFile.getPath());
				// Initialize Dto 
				scheduleCreation = initCreationDto(cu, contextApp, scheduleType);
				// Initialize Context
				ContextScheduleCreationAssembly contextSchedule = new ContextScheduleCreationAssembly();
				contextSchedule.setSynthesis(FileUtils.readFileToString(synthesisFile, GlobalConstants.MODEL_ENCODING));
	
				boolean disassemblyScheduleFound = true;
				//Call Service Search Range Intermediary
				try {
					scheduleCreation = getInstanciateService().searchScheduleIntermediary(scheduleCreation, contextSchedule);
				} catch (CreateScheduleAssemblyException cse) {
					disassemblyScheduleFound = false;
				}
				if (disassemblyScheduleFound) {
					String effectivityFileName = scheduleCreation.getMethodFromEffectivities().get(contextApp.getMethod());
					if((effectivityFileName == null || effectivityFileName.isEmpty()) 
							&& scheduleCreation.getMethodFromEffectivities().values() != null){
						effectivityFileName = scheduleCreation.getMethodFromEffectivities().values().iterator().next();
					}
					if(effectivityFileName == null || effectivityFileName.isEmpty()){
						logger.debug("externalScheduleData - no effectivity found from method :"+contextApp.getMethod());
						throw new Exception("externalScheduleData - File not found");
					}
	
					//Build path Intermediary 
					StringBuilder buf = new StringBuilder();
					buf.append(directoryPath);
					buf.append("/");
					buf.append(scheduleCreation.getFamily());
					buf.append("/");
					buf.append(scheduleCreation.getFamily());
					buf.append("-");
					buf.append(scheduleCreation.getLastVersionPublish());
					buf.append("/");
					buf.append(scheduleCreation.getMaterial());
					buf.append("/");
					buf.append(effectivityFileName);
					String intermediaryPath = buf.toString();
					logger.debug("externalScheduleData - intermediary path found "+intermediaryPath);
	
					//Aply xslt Transformation parameter 
					modelTransformation.addParameter(XsltConstants.RANGE_PARAM_XSL_LANGUAGE, contextApp.getLanguage());
					modelTransformation.addParameter(XsltConstants.RANGE_PARAM_XSL_SITE, contextApp.getSite());
					modelTransformation.addParameter(XsltConstants.RANGE_PARAM_XSL_METHOD, contextApp.getMethod());
					modelTransformation.addParameter(XsltConstants.RANGE_PARAM_XSL_MATERIAL, contextApp.getMaterial());
					modelTransformation.addParameter(XsltConstants.RANGE_PARAM_XSL_LEVEL, contextApp.getLevels());
					modelTransformation.addParameter(XsltConstants.RANGE_PARAM_XSL_INTERVENTION_TYPE, contextApp.getInterventionType() == null ? "none" : contextApp.getInterventionType());
					modelTransformation.addParameter(XsltConstants.RANGE_PARAM_XSL_VARIANT, contextApp.getVariant());
					modelTransformation.addParameter(XsltConstants.RANGE_PARAM_XSL_ORDER, contextApp.getOrder());
					modelTransformation.addParameter(XsltConstants.RANGE_PARAM_XSL_AFFAIR, contextApp.getAffair());
					modelTransformation.addParameter(XsltConstants.RANGE_PARAM_XSL_PN, contextApp.getPartNumber());
					modelTransformation.addParameter(XsltConstants.RANGE_PARAM_XSL_INSTANCES, Integer.parseInt(contextApp.getInstances()));
					modelTransformation.addParameter(XsltConstants.RANGE_PARAM_XSL_CONTEXT, contextApp.getContext());
					modelTransformation.addParameter(XsltConstants.RANGE_PARAM_XSL_FICTIF_ENGINE, Boolean.toString(contextApp.isFictiveEngine()));
					modelTransformation.addParameter(XsltConstants.RANGE_PARAM_XSL_ISOLATED_MODULE, Boolean.toString(contextApp.isIsolateModule()));
					if(!modelTransformation.transformFileToStream(intermediaryPath)) {
						throw new Exception("externalScheduleData - Error during research intermediary file : "+intermediaryPath);	
					}
					String scheduleFlow= modelTransformation.getResultStream();
					contextRange.setRange(scheduleFlow);
					contextRange.setHistory(scheduleCreation.getLastVersionPublish());
				}
			}
			contextData.setRangeContext(contextRange);
			resultData.setStatus(ResultStatus.STATUS_OK);
		
		} catch (ReportGenerationException e) {
			logger.error("Error : XML file parsing failed", e);
			resultData.setStatus(ResultStatus.STATUS_ERROR);
			resultData.setMessage(PropertyConstant.PROPERTY_INTERNAL_NOT_FOUND_XML_FILE);
		} catch (RangeIoException e) {
			logger.error("Error : XML file parsing failed", e);
			resultData.setStatus(ResultStatus.STATUS_ERROR);
			resultData.setMessage(PropertyConstant.PROPERTY_INTERNAL_NOT_FOUND_XML_FILE);
		} catch (IOException e) {
			logger.error("Error : XML file parsing failed", e);
			resultData.setStatus(ResultStatus.STATUS_ERROR);
			resultData.setMessage(PropertyConstant.PROPERTY_INTERNAL_NOT_FOUND_XML_FILE);
		} catch (Exception e) {
			logger.error("Error : XML file parsing failed", e);
			resultData.setStatus(ResultStatus.STATUS_ERROR);
			resultData.setMessage(PropertyConstant.PROPERTY_INTERNAL_NOT_FOUND_XML_FILE);
		} finally {
			contextData.setResult(resultData);
		}
		
		return contextData;
	}
	
	/**
	 * Get the last picture from Server
	 */
	@Override
	public String getLastPictureUploaded(String path, String destFile) {
		File myDirPictures = new File(path);
		
		File[] listPictures = myDirPictures.listFiles();
		File myPicture = null;
		
		boolean first = true;
				
		if(listPictures != null) {
			// Get the last modified picture
			for(File lastPic : listPictures){
				if(first){
					myPicture = lastPic;
					first = false;
					continue;
				}
				if(myPicture.lastModified() < lastPic.lastModified()){
					myPicture = lastPic;
				}
			}
			
			if(myPicture != null) {
				String uploadName = new Date().getTime()+"-"+ myPicture.getName();
				copyPicture(myPicture, new File(destFile + uploadName));
				return uploadName;
			}
		}
		return null;
	}
	
	public boolean copyPicture(File srcFile, File destFile) {
		boolean success = false;
		try {
			FileUtils.copyFile(srcFile, destFile, true);
			success = true;
			logger.info("Image copiée dans :" + destFile.getPath());
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("Image non copiée dans :" + destFile.getPath());
		}
		return success;
	}

	private ScheduleCreationDTO initCreationDto (ContextUser cu, ContextApplicabilities contextApp, int scheduleType) {
		ScheduleCreationDTO scheduleCreation = new ScheduleCreationDTO();

		scheduleCreation.setSite(cu.getWorkingSite());
		if (scheduleType == AssemblyGlobalServices.SCHEDULE_IDENTIFIER_ASSEMBLY_TYPE) {
			scheduleCreation.setRangeType(AssemblyGlobalServices.SCHEDULE_ASSEMBLY_TYPE);
		}else{
			scheduleCreation.setRangeType(AssemblyGlobalServices.SCHEDULE_DISASSEMBLY_TYPE);
		}

		scheduleCreation.setVariant(contextApp.getVariant());
		scheduleCreation.setContext(contextApp.getContext());
		scheduleCreation.setLanguage(contextApp.getLanguage());
		scheduleCreation.setPn(contextApp.getPartNumber());

		if(contextApp.getContext().equals(AssemblyGlobalServices.SCHEDULE_ASSEMBLY_NEW) && scheduleType==AssemblyGlobalServices.SCHEDULE_IDENTIFIER_ASSEMBLY_TYPE){
			scheduleCreation.setConf(contextApp.getArticles());
		}else{
			scheduleCreation.setConf(contextApp.getModifications());
		}
		return scheduleCreation;
	}


	/**
	 * @return the instanciateService
	 */
	public IServiceScheduleIntermediaryDao getInstanciateService() {
		return instanciateService;
	}


	/**
	 * @param instanciateService the instanciateService to set
	 */
	public void setInstanciateService(IServiceScheduleIntermediaryDao instanciateService) {
		this.instanciateService = instanciateService;
	}
	
	/**
	 * Get the Xml path for bench
	 */
	@Override
	@WebMethod
	public StatusType getXmlBenchPath(String destFile, String statusKoXPath) {
		try {
			logger.debug("Info Bench files : " + destFile);
			File myFile = new File(destFile);
			if(myFile != null && myFile.exists()) {
				XmlDocumentEditor xmlDocument= new XmlDocumentEditor(myFile);
				if(xmlDocument.find(statusKoXPath) != null) {
					return StatusType.KO;
				} else {
					return StatusType.OK;
				}	
			}
		} catch (Exception e) {
			logger.error("Error : XML file parsing failed", e);
			return StatusType.KO;
		}
		
		return StatusType.KO;
	}
	
	
}


