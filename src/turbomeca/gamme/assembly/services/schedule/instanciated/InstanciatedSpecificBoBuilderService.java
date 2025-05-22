package turbomeca.gamme.assembly.services.schedule.instanciated;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.springframework.stereotype.Service;

import turbomeca.gamme.assembly.services.schedule.model.types.StatusType;
import turbomeca.gamme.ecran.services.common.enumtypes.ScheduleState;
import turbomeca.gamme.ecran.services.common.enumtypes.StatusRangeType;
import turbomeca.gamme.ecran.services.common.utils.comparator.SimpleObjectComparator;
import turbomeca.gamme.ecran.services.common.utils.xml.XmlDocumentEditor;
import turbomeca.gamme.ecran.services.schedule.bo.BoInstanciatedSchedule;
import turbomeca.gamme.ecran.services.schedule.bo.BoInstanciatedScheduleAssembly;
import turbomeca.gamme.ecran.services.schedule.service.instanciated.IInstanciatedSpecificBoBuilderService;

@Service
public class InstanciatedSpecificBoBuilderService implements IInstanciatedSpecificBoBuilderService {
	
	private static Logger logger = Logger.getLogger(InstanciatedSpecificBoBuilderService.class);

	@Override
	public boolean buildSpecificInstanciatedBo(XmlDocumentEditor xmlEditor,
			String filePath, ScheduleState scheduleState, BoInstanciatedSchedule boInstanciated) {
		boolean success = false;
		BoInstanciatedScheduleAssembly instanciatedSchedule = null;
		
		try {
			// Build specific BoInstanciatedAssembly
			instanciatedSchedule = constructInstanciatedScheduleFromInstanciationElement(
					xmlEditor, filePath, scheduleState, boInstanciated);
		
		} catch (JDOMException e) {
			logger.error("Problem during database rebuild (specific bo building) for schedule file : " + filePath, e);
		} catch (Exception e) {
			logger.error("Problem during database rebuild (specific bo building) for schedule file : " + filePath, e);
		}
		
		if (instanciatedSchedule != null) {
			boInstanciated.setScheduleIdentificationAssembly(instanciatedSchedule);
			success = true;
		}
		
		return success;
	}

	@Override
	public String buildScheduleReference(XmlDocumentEditor xmlEditor) throws JDOMException {
		String reference = "";
		Element instanciationElement = xmlEditor.find("/*/instanciation");
		if (instanciationElement != null) {
			reference = instanciationElement.getChildText("pn");
		}
		return reference;
	}
	
	@Override
	public String buildScheduleGeodeReference(XmlDocumentEditor xmlEditor) throws JDOMException {
		String reference = "";
		Element referenceGeodeElement = xmlEditor
				.find("/*/identification/geode/reference");
		if (referenceGeodeElement != null) {
			reference = referenceGeodeElement.getText();
		}
		return reference;
	}

	@Override
	public List<String> getScheduleVersionList(XmlDocumentEditor xmlEditor, ScheduleState scheduleState) throws JDOMException {
		List<String> scheduleVersionList = new ArrayList<String>();
		
		if (scheduleState.equals(ScheduleState.OFFICIAL)) {
			List<Element> issueElements = xmlEditor.finds("/*/historical/issue");
			for (Element issueElement : issueElements) {
				scheduleVersionList.add(issueElement.getChildText("version"));
			}
		} else {
			List<Element> subIssueElements = xmlEditor.finds("/*/historical/issue/sub-issue");
			for (Element subIssueElement : subIssueElements) {
				scheduleVersionList.add(subIssueElement.getChildText("version"));
			}
		}
		
		return scheduleVersionList;
	}

	@Override
	public String buildIndustrialValidation(XmlDocumentEditor xmlEditor) throws JDOMException {
		String industrialValidation = "";
		Element geodeElement = xmlEditor.find("/*/identification/geode");
		if (geodeElement != null) {
			industrialValidation = geodeElement.getChildText("industrialValidation");
		}
		if (industrialValidation == null) {
			industrialValidation = "";
		}
		return industrialValidation;
	}
	
	@Override
	public StatusRangeType buildUfiStatus(String status) {
		StatusRangeType ufiStatus = null;
		
		if(status.equals(StatusType.DONE.value())) {
			ufiStatus = StatusRangeType.CLOSE;
		} else if(status.equals(StatusType.TODO.value()) || status.equals(StatusType.CREATION.value())) {
			ufiStatus = StatusRangeType.INSTANTIATED;
		} else if(status.equals(StatusType.TO_SIGN.value())) {
			ufiStatus = StatusRangeType.TO_VALIDATE;
		} else if(status.equals(StatusRangeType.PDF_GENERATION.value())) {
			ufiStatus = StatusRangeType.PDF_GENERATION;
		} else if(status.equals(StatusRangeType.PDF_GENERATION_ERROR.value())) {
			ufiStatus = StatusRangeType.PDF_GENERATION_ERROR;
		}
		
		return ufiStatus;
	}


	private BoInstanciatedScheduleAssembly constructInstanciatedScheduleFromInstanciationElement(
			XmlDocumentEditor xmlEditor, String filePath, ScheduleState scheduleState, BoInstanciatedSchedule boInstanciated) throws JDOMException {
		
		// Get instanciation data
		Element instanciationElement = xmlEditor.find("/*/instanciation");
		Element identificationElement = xmlEditor.find("/*/identification");
		
		String affair = instanciationElement.getChildText("affair");
		String context = instanciationElement.getChildText("context");
		String family = identificationElement.getChildText("family");
		String material = identificationElement.getChildText("material");
		String variants = instanciationElement.getChildText("variant");
		
		String type = "";
		Element assemblyElement = xmlEditor.find("/assembly");
		Element disassemblyElement = xmlEditor.find("/disassembly");
		if (assemblyElement != null) {
			type = "assembly";
		} else if (disassemblyElement != null) {
			type = "disassembly";
		}
		
		// Set Schedule Instanciated Assembly bo data
		BoInstanciatedScheduleAssembly scheduleInstanciated = null;
		// if no existing Bo, create a new one
		if (boInstanciated.getScheduleIdentificationAssembly() == null) {
			scheduleInstanciated = new BoInstanciatedScheduleAssembly();
		// else, use existing one and erase its data
		} else {
			scheduleInstanciated = boInstanciated.getScheduleIdentificationAssembly();
		}
		scheduleInstanciated.setAffair(affair);
		scheduleInstanciated.setContext(context);
		scheduleInstanciated.setFamily(family);
		scheduleInstanciated.setMaterial(material);
		scheduleInstanciated.setType(type);
		scheduleInstanciated.setVariants(variants);
		scheduleInstanciated.setInstanciatedSchedule(boInstanciated);
		
		return scheduleInstanciated;
	}
	
	@Override
	public boolean checkDifferencesBetweenBosInstanciated(
			BoInstanciatedSchedule bo, BoInstanciatedSchedule otherBo) {
		boolean bosAreDifferent = false;
		
		if (bo.getScheduleIdentificationAssembly() != null 
				&& otherBo.getScheduleIdentificationAssembly() != null) {
			// Check differences between specific Bo
			BoInstanciatedScheduleAssembly boAssembly = bo.getScheduleIdentificationAssembly();
			BoInstanciatedScheduleAssembly otherBoAssembly = otherBo.getScheduleIdentificationAssembly();
			
			bosAreDifferent = SimpleObjectComparator.checkDifferencesBetweenObjects(boAssembly.getAffair(), otherBoAssembly.getAffair())
					|| SimpleObjectComparator.checkDifferencesBetweenObjects(boAssembly.getContext(), otherBoAssembly.getContext())
					|| SimpleObjectComparator.checkDifferencesBetweenObjects(boAssembly.getFamily(), otherBoAssembly.getFamily())
					|| SimpleObjectComparator.checkDifferencesBetweenObjects(boAssembly.getMaterial(), otherBoAssembly.getMaterial())
					|| SimpleObjectComparator.checkDifferencesBetweenObjects(boAssembly.getType(), otherBoAssembly.getType())
					|| SimpleObjectComparator.checkDifferencesBetweenObjects(boAssembly.getVariants(), otherBoAssembly.getVariants());
		} else {
			bosAreDifferent = true;
		}
		
		return bosAreDifferent;
	}

	@Override
	public String buildScheduleGeodeVersion(XmlDocumentEditor xmlEditor) throws JDOMException {
		// TODO Auto-generated method stub
		return null;
	}

}
