package turbomeca.gamme.assembly.services.bodb.bobuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import turbomeca.gamme.assembly.services.bodb.utils.BoDbUtil;
import turbomeca.gamme.assembly.services.model.data.MeasureTool;
import turbomeca.gamme.assembly.services.model.data.TaskAction;
import turbomeca.gamme.assembly.services.model.data.TaskPiloting;
import turbomeca.gamme.assembly.services.model.data.Tool;
import turbomeca.gamme.assembly.services.model.data.Tools;
import turbomeca.gamme.ecran.services.bodb.bo.BoTool;

public class BoDbToolBuilder {
	
	private static Map<String, BoTool> boTools = new HashMap<String, BoTool>();
	
	public static BoTool getOrBuildBoToolFromTaskPiloting(TaskPiloting taskPiloting) {
		BoTool boTool = null;
		if (taskPiloting != null) {
			String sn = getSnValue(taskPiloting.getTaskAction());
			String designation = taskPiloting.getTool();
			boTool = getOrBuildBoTool(sn, designation);
		}
		return boTool;
	}
	
	public static BoTool getOrBuildBoToolFromMeasureTool(MeasureTool measureTool) {
		BoTool boTool = null;
		if (measureTool != null) {
			String sn = getSnValue(measureTool.getTaskAction());
			String designation = measureTool.getType();
			boTool = getOrBuildBoTool(sn, designation);
		}
		return boTool;
	}
	
	private static BoTool getOrBuildBoTool(String sn, String designation) {
		BoTool boTool = null;
		
		// Don't record tool without SN or type
		if (sn != null && designation != null) {
			// Compute identification tool key
			String computeyKey = sn.toLowerCase();
			
			// Search for tool in cache map
			if (getBoTools() != null && !getBoTools().isEmpty()) {
				boTool = getBoTools().get(computeyKey);
			}
			// If bo not present in cache map, create it
			if(boTool == null) {
				boTool = buildBoTool(sn, designation);
			}
			// Add bo tool to cache map
			getBoTools().put(computeyKey, boTool);
		}
		
		return boTool;
	}

	private static BoTool buildBoTool(String sn, String designation) {
		BoTool toolBean = null;
		toolBean = new BoTool();
		toolBean.setSn(sn);
		toolBean.setDesignation(designation);
		return toolBean;
	}
	
	private static String getSnValue(TaskAction taskAction) {
		String sn = null;
		if (taskAction != null) {
			if(taskAction != null && taskAction.getInputAction() != null && taskAction.getInputAction().getInputValue() != null
					&& taskAction.getInputAction().getInputValue().getValue() != null) {
				sn = taskAction.getInputAction().getInputValue().getValue();
			} 
		}
		return sn; 
	}

	private static synchronized Map<String, BoTool> getBoTools() {
		return boTools;
	}

	public static List<BoTool> getOrBuildBoToolFromSubShape(Tools tools) {
		List<BoTool> result = new ArrayList<BoTool>();
		if (tools != null) {
			for (Tool tool : tools.getTool()) {
				result.add(buildBoTool(tool));
			}
		}
		return result;
	}
	
	private static BoTool buildBoTool(Tool tool) {
		BoTool boTool = new BoTool();
		boTool.setUfiId(tool.getId());
		if (tool.getPN() != null && tool.getPN().getTaskAction() != null
				&& tool.getPN().getTaskAction().getInputAction() != null
				&& tool.getPN().getTaskAction().getInputAction().getInputValue() != null) {
			boTool.setPn(tool.getPN().getTaskAction().getInputAction().getInputValue().getValue());
		} else if (tool.getPN() != null && tool.getPN().getValue()!=null) {
			boTool.setPn(tool.getPN().getValue());
		}
		
		if (tool.getSN() != null && tool.getSN().getTaskAction() != null
				&& tool.getSN().getTaskAction().getInputAction() != null
				&& tool.getSN().getTaskAction().getInputAction().getInputValue() != null) {
			boTool.setSn(tool.getSN().getTaskAction().getInputAction().getInputValue().getValue());
		} else if (tool.getSN() != null && tool.getSN().getValue() != null) {
			boTool.setSn(tool.getSN().getValue());
		}
		
		if (tool.getDesignation() != null) {
			boTool.setDesignation(BoDbUtil.buildTextFromParaList(tool.getDesignation().getPara()));
		}
		boTool.setQuantity(tool.getQuantity());
		if (tool.getSubstitute() != null) {
			boTool.setAlternative(Boolean.valueOf(tool.getSubstitute().value()));
//			boTool.setAlternatives(tool.getToolRefId()); FIXME TBD Alternatives
		}
		return boTool;
	}
}
