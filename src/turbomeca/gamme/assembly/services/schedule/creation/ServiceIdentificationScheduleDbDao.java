package turbomeca.gamme.assembly.services.schedule.creation;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import turbomeca.gamme.assembly.services.model.AssemblyModelConstants;
import turbomeca.gamme.assembly.services.schedule.creation.utils.AssemblyGlobalServices;
import turbomeca.gamme.ecran.services.common.utils.misc.PublishedScheduleUtils;
import turbomeca.gamme.ecran.services.model.constants.ModelGlobalConstants;
import turbomeca.gamme.ecran.services.publication.bo.BoPublishedSchedule;
import turbomeca.gamme.ecran.services.publication.dao.DaoPublishedSchedule;
import turbomeca.gamme.ecran.services.schedule.bean.ContextScheduleCreation;
import turbomeca.gamme.ecran.services.schedule.bean.ContextUserServer;
import turbomeca.gamme.ecran.services.schedule.service.creation.IServiceIdentificationDao;
import turbomeca.gamme.ecran.services.schedule.service.creation.ScheduleCreationDTO;

/**
 * Database implementation for identification schedule DAO
 * @author  gbiet
 *
 */
@Service(value="IServiceIdentificationDaoBdd")
public class ServiceIdentificationScheduleDbDao implements IServiceIdentificationDao
{
	@Autowired
	DaoPublishedSchedule daoPublishedSchedule;
		
	/**
	 * Effectivities parameters init
	 */
	@Override
	public Map<String, List<?>> initParametersEffectivities(ContextUserServer contextUser,
			ContextScheduleCreation contextSchedule, File synthesis) throws Exception {
		
		// First we need to retrieve effectivities matching language and site
		Collection<BoPublishedSchedule> publishedSchedules = daoPublishedSchedule.findBySiteAndLanguage(contextUser.getLanguage().toUpperCase(), contextUser.getSite().toUpperCase());
		
		HashMap<String, List<?>> parameters = new HashMap<String, List<?>>();
		List<String> pnList = PublishedScheduleUtils.extractParametersFromSchedules(publishedSchedules, ModelGlobalConstants.TAG_PN);
		
		parameters.put(AssemblyModelConstants.TAG_PNS, pnList);
		
		return parameters;
		
	}

	/**
	 * Compute VA, MODIFICATION and VARIANT parameters according to lang/site/PN params
	 */
	@Override
	public Map<String, List<?>> getParametersEffectivitiesFromPN(ScheduleCreationDTO dto,
			ContextScheduleCreation contextSchedule, ContextUserServer contextUser) throws Exception {
		
		HashMap<String,List<?>> listResult=new HashMap<String,List<?>>();

		// Retrieve schedules from database
		Collection<BoPublishedSchedule> publishedSchedules = daoPublishedSchedule.findBySiteAndLanguageAndPnAndReferential(contextUser.getLanguage().toUpperCase(),
				contextUser.getSite().toUpperCase(), contextSchedule.getPartNumber(), contextUser.getReferential());
		
		// Process parameters
		if(!contextSchedule.getScheduleType().equals(AssemblyGlobalServices.SCHEDULE_DISASSEMBLY_TYPE) 
				&& contextSchedule.getContext().equals(AssemblyGlobalServices.SCHEDULE_ASSEMBLY_NEW)){

			// Process VAs parameters
			// //parameters[languages/language/@language='%s' and sites/site/@site='%s']/effectivities/effectivity/effectivityVariant[pns/pn='%s']/articles/va
			List<String> vaList = PublishedScheduleUtils.extractParametersFromSchedules(publishedSchedules, ModelGlobalConstants.TAG_VA);
			listResult.put(AssemblyModelConstants.TAG_VAS, vaList);
			
		}else{

			// Process MODIFICATIONS parameters
			// //parameters[languages/language/@language='%s' and sites/site/@site='%s']/effectivities/effectivity/effectivityVariant[pns/pn='%s']/modifications/modification
			List<String> modificationList = PublishedScheduleUtils.extractParametersFromSchedules(publishedSchedules, ModelGlobalConstants.TAG_MODIFICATION);
			listResult.put(ModelGlobalConstants.TAG_MODIFICATIONS, modificationList);
			
		}
		
		// Process VARIANTS parameters
		// //parameters[languages/language/@language='%s' and sites/site/@site='%s']/effectivities/effectivity/effectivityVariant[pns/pn='%s']/@variant
		dto.setVariant(PublishedScheduleUtils.extractVariantListFromSchedules(publishedSchedules));
		
		return listResult;
	}
	
}
