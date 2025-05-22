package turbomeca.gamme.assembly.services.schedule.creation;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Attribute;
import org.jdom.Element;
import org.springframework.stereotype.Service;

import turbomeca.gamme.assembly.services.model.AssemblyModelConstants;
import turbomeca.gamme.assembly.services.schedule.creation.bean.ContextScheduleCreationAssembly;
import turbomeca.gamme.assembly.services.schedule.creation.exception.CreateScheduleAssemblyException;
import turbomeca.gamme.assembly.services.schedule.creation.utils.AssemblyGlobalServices;
import turbomeca.gamme.assembly.services.schedule.creation.utils.FileAssemblyUtils;
import turbomeca.gamme.assembly.services.schedule.creation.utils.XpathConstantsUtils;
import turbomeca.gamme.ecran.services.common.utils.xml.JDomUtils;
import turbomeca.gamme.ecran.services.model.constants.ModelGlobalConstants;
import turbomeca.gamme.ecran.services.schedule.bean.ContextScheduleCreation;
import turbomeca.gamme.ecran.services.schedule.bean.ContextUserServer;
import turbomeca.gamme.ecran.services.schedule.model.dao.ScheduleLoader;
import turbomeca.gamme.ecran.services.schedule.service.creation.IServiceIdentificationDao;
import turbomeca.gamme.ecran.services.schedule.service.creation.ScheduleCreationDTO;

/***
 * XML pub synthesis DAO
 * 
 * @author ahermo
 *
 */
@Service(value="IServiceIdentificationDaoXml")
public class ServiceIdentificationScheduleDao implements IServiceIdentificationDao
{
	@Override
	public Map<String, List<?>> initParametersEffectivities(ContextUserServer contextUser, ContextScheduleCreation contextSchedule, final File synthesisFile) throws Exception {
		ContextScheduleCreationAssembly contextScheduleAssembly = (ContextScheduleCreationAssembly)contextSchedule;
		HashMap<String,List<?>> listResult=new HashMap<String,List<?>>();
		if(synthesisFile.exists()){
			String flowSynthesis = FileAssemblyUtils.getSynthesisFlow(synthesisFile);
			contextScheduleAssembly.setSynthesis(flowSynthesis);
			ScheduleLoader scheduleLoader = new ScheduleLoader(flowSynthesis);
			String xpath =String.format(XpathConstantsUtils.XPATH_SEARCH_PN,  contextUser.getLanguage().toUpperCase(), contextUser.getSite().toUpperCase());
			List<Element> listPn = scheduleLoader.findList(xpath);
			listResult.put(AssemblyModelConstants.TAG_PNS, listPn);
		} else {
			throw new CreateScheduleAssemblyException(CreateScheduleAssemblyException.EXCEPTION_XML_GENERIC_NOT_FOUND);
		}
		return listResult;
	}
	
	@Override
	public Map<String, List<?>> getParametersEffectivitiesFromPN(ScheduleCreationDTO dto, ContextScheduleCreation contextSchedule, ContextUserServer contextUser) throws Exception {
		HashMap<String,List<?>> listResult=new HashMap<String,List<?>>();
		ContextScheduleCreationAssembly contextScheduleAssembly = (ContextScheduleCreationAssembly)contextSchedule;
		ScheduleLoader scheduleLoader = new ScheduleLoader(contextScheduleAssembly.getSynthesis());
		String xpath;
		if(!contextSchedule.getScheduleType().equals(AssemblyGlobalServices.SCHEDULE_DISASSEMBLY_TYPE) && contextSchedule.getContext().equals(AssemblyGlobalServices.SCHEDULE_ASSEMBLY_NEW)){
			xpath=String.format(XpathConstantsUtils.XPATH_SEARCH_VA, contextUser.getLanguage().toUpperCase(), contextUser.getSite().toUpperCase(), contextSchedule.getPartNumber());
			List<Element> listArticles=  scheduleLoader.findList(xpath);
			listResult.put(AssemblyModelConstants.TAG_VAS, listArticles);
		
		}else{
			xpath= String.format(XpathConstantsUtils.XPATH_SEARCH_MODIFICATION, contextUser.getLanguage().toUpperCase(), contextUser.getSite().toUpperCase(), contextSchedule.getPartNumber());
			List<Element> listModifications = scheduleLoader.findList(xpath);
			listResult.put(ModelGlobalConstants.TAG_MODIFICATIONS, listModifications);
		
		}
		xpath = String.format(XpathConstantsUtils.XPATH_SEARCH_VARIANT, contextUser.getLanguage().toUpperCase(), contextUser.getSite().toUpperCase(), contextSchedule.getPartNumber());
		List<Attribute> listVariants =  scheduleLoader.findAttributes(xpath);
		dto.setVariant(JDomUtils.convertAttributeListToBlancSeparatedString(listVariants));
		return listResult;
	}
}
