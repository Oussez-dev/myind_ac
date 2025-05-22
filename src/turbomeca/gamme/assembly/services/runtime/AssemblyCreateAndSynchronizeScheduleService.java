package turbomeca.gamme.assembly.services.runtime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;

import javax.jws.WebService;

import org.jdom.JDOMException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import turbomeca.gamme.assembly.services.model.AssemblyModelConstants;
import turbomeca.gamme.assembly.services.schedule.config.AssemblyRepositoryConfig;
import turbomeca.gamme.assembly.services.schedule.creation.bean.ContextScheduleCreationAssembly;
import turbomeca.gamme.assembly.services.schedule.creation.bean.ContextUserAssemblyServer;
import turbomeca.gamme.ecran.services.common.bean.ContextUser;
import turbomeca.gamme.ecran.services.common.utils.schedulestate.ScheduleStateUtils;
import turbomeca.gamme.ecran.services.common.utils.xml.XmlDocumentEditor;
import turbomeca.gamme.ecran.services.runtime.service.impl.CreateAndSynchronizeScheduleService;
import turbomeca.gamme.ecran.services.schedule.bean.ContextScheduleCreation;
import turbomeca.gamme.ecran.services.schedule.bean.ContextUserServer;

@WebService(serviceName = "CreateAndSynchronizeScheduleService",
targetNamespace = "http://impl.service.runtime.services.ecran.gamme.turbomeca/",
endpointInterface = "turbomeca.gamme.ecran.services.runtime.ICreateAndSynchronizeScheduleService")
@Service(value = "assemblyCreateAndSynchronizeScheduleService")
public class AssemblyCreateAndSynchronizeScheduleService extends CreateAndSynchronizeScheduleService {
	
	@Value("${applet.config.create.checkbox.taskMark}")
	private String isCheckBoxTaskMark;
	
	private static final String[] TAGS = {AssemblyModelConstants.TAG_MARK, AssemblyModelConstants.TAG_TASK_ACTION, 
										AssemblyModelConstants.TAG_TOOL, AssemblyModelConstants.TAG_DOCUMENT_JOINED};
	
	@Override
	protected ContextUserServer createContextUser(String site, ContextUser contextUser, String language, String referential) {
		ContextUserAssemblyServer cuas = new ContextUserAssemblyServer();
		cuas.setSite(site);
		cuas.setName(contextUser.getLogin());
		cuas.setLogin(contextUser.getUserName());
		cuas.setLanguage(language);
		cuas.setDataContext(ScheduleStateUtils.getScheduleStateFromString(referential));
		cuas.setRepository((AssemblyRepositoryConfig) getRepositoryService().getRepository(contextUser));
		return cuas;
	}
	
	@Override
	protected ContextScheduleCreation createContextScheduleCreation(String language, String interventionType,
			String variant, String order, String affair, String partNumber, String configuration, String material,
			Long scheduleDatabaseId, String method, String[] level, String applicabilityMaterial, Boolean isFictiveEngine,
			Boolean isIsolatedModule, long instances, String context, Calendar dateCreation, String scheduleType, 
			String version, String effectivity, String family, String folderFamily) {
		ContextScheduleCreationAssembly csca = new ContextScheduleCreationAssembly();
		csca.setDateCreation(dateCreation);
		csca.setMethod(method);
		csca.setLanguage(language);
		csca.setMaterial(material);
		csca.setLevel(level != null ? new HashSet<String>(Arrays.asList(level)) : null);
		csca.setInterventionType(interventionType);
		csca.setApplicabilityMaterial(applicabilityMaterial);
		csca.setIsolatedModule(isIsolatedModule || isFictiveEngine ? isIsolatedModule : null);
		csca.setVariant(variant);
		csca.setOrder(order);
		csca.setAffair(affair);
		csca.setPartNumber(partNumber);
		csca.setInstances(instances);
		csca.setContext(scheduleType);
		csca.setConfiguration(configuration);
		csca.setScheduleDatabaseId(scheduleDatabaseId);
		csca.setCreateCheckboxTaskMark(Boolean.valueOf(isCheckBoxTaskMark));
		csca.setScheduleType(context);
		csca.setVersion(version);
		csca.setEffectivity(effectivity);
		csca.setFamily(family);
		csca.setFolderFamily(folderFamily);
		return csca;
	}

	@Override
	protected String[] getTagToMerge() {
		return TAGS;
	}

	@Override
	protected String[] getIdToMerge(XmlDocumentEditor editor, String effectivity, String variant) throws JDOMException {
		List<String> ids = new ArrayList<String>();
		cleanNotCompliantElements(editor, AssemblyModelConstants.TAG_OPERATION, effectivity, variant);
		cleanNotCompliantElements(editor, AssemblyModelConstants.TAG_SUBPHASE, effectivity, variant);
		cleanNotCompliantElements(editor, AssemblyModelConstants.TAG_TASK, effectivity, variant);
		cleanNotCompliantElements(editor, AssemblyModelConstants.TAG_TOOL, effectivity, variant);
		
		for (String tag : TAGS) {
			cleanNotCompliantElements(editor, tag, effectivity, variant);
			ids.addAll(getElementsId(editor, tag, effectivity, variant));
		}
		
		// Remove unused eletronic notification on new effectivity
		removeElementsRefId(editor, AssemblyModelConstants.TAG_ELECTRONIC_NOTIFICATION, ids);
		
		return ids.toArray(new String[ids.size()]);
	}

	@Override
	protected String[] getIdToRemove(XmlDocumentEditor editor, String effectivity, String variant) {
		return null;
	}

}
