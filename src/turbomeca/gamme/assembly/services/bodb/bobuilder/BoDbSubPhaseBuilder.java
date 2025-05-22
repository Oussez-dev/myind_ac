package turbomeca.gamme.assembly.services.bodb.bobuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import turbomeca.gamme.assembly.services.model.data.Entry;
import turbomeca.gamme.assembly.services.model.data.MeasureTool;
import turbomeca.gamme.assembly.services.model.data.Operation;
import turbomeca.gamme.assembly.services.model.data.Row;
import turbomeca.gamme.assembly.services.model.data.SubPhase;
import turbomeca.gamme.assembly.services.model.data.SubPhasesItem;
import turbomeca.gamme.assembly.services.model.data.TableElement_type;
import turbomeca.gamme.assembly.services.model.data.Task;
import turbomeca.gamme.assembly.services.model.data.TaskAction;
import turbomeca.gamme.assembly.services.model.data.TaskChoiceItem;
import turbomeca.gamme.assembly.services.model.data.TaskGroup;
import turbomeca.gamme.assembly.services.model.data.TaskPiloting;
import turbomeca.gamme.assembly.services.model.data.Tasks;
import turbomeca.gamme.assembly.services.model.data.TasksItem;
import turbomeca.gamme.assembly.services.model.data.Tgroup;
import turbomeca.gamme.assembly.services.model.data.types.StatusInstType;
import turbomeca.gamme.ecran.services.bodb.bo.BoRecord;
import turbomeca.gamme.ecran.services.bodb.bo.BoSubPhase;
import turbomeca.gamme.ecran.services.bodb.bo.BoTool;
import turbomeca.gamme.ecran.services.bomodel.IBoPart;
import turbomeca.gamme.ecran.services.bomodel.IBoTool;
import turbomeca.gamme.ecran.services.bomodel.IBoUserUfi;
import turbomeca.gamme.ecran.services.common.enumtypes.StatusType;
import turbomeca.gamme.ecran.services.runtime.bean.ContextSynchronizeBoData;

public class BoDbSubPhaseBuilder {

	public static List<BoSubPhase> buildOperationSubphasesBean (Operation operation, Set<? extends IBoPart> listParts, 
			List<IBoUserUfi> listUserUfi, List<String> listIdSubPhases, ContextSynchronizeBoData contextSynchroBOData) {
		List<BoSubPhase> subphasesBean = new ArrayList<BoSubPhase>();
		if (operation.getSubPhases() != null) {
			for (SubPhasesItem subPhaseItem : operation.getSubPhases().getSubPhasesItem()) {
				if (subPhaseItem.getSubPhase() != null) {
					SubPhase subPhase = subPhaseItem.getSubPhase();
					// Check if subPhase is in edition objects list
					if( (listIdSubPhases != null && listIdSubPhases.contains(subPhase.getId()))
							|| listIdSubPhases == null || subPhase.getArchive()){
						subphasesBean.add(buildSubphase(subPhase, listParts, listUserUfi, contextSynchroBOData));
					}
				} else if (subPhaseItem.getSubPhaseGroup() != null) {
					for (SubPhase subPhase : subPhaseItem.getSubPhaseGroup().getSubPhase()) {
						if( (listIdSubPhases != null && listIdSubPhases.contains(subPhase.getId()))
								|| listIdSubPhases == null || subPhase.getArchive()){
							subphasesBean.add(buildSubphase(subPhase, listParts, listUserUfi, contextSynchroBOData));
						}
					}
				}

			}
		}
		return subphasesBean;
	}

	private static BoSubPhase buildSubphase(SubPhase subphase, Set<? extends IBoPart> listParts, List<IBoUserUfi> listUserUfi, 
			ContextSynchronizeBoData contextSynchroBOData) {
		BoSubPhase subphaseBean = new BoSubPhase();
		subphaseBean.setUfiId(subphase.getId());
		
		subphaseBean.setExternalDatas(BoDbExternalDataBuilder.buildExternalData(subphase.getDocumentsJoined(), contextSynchroBOData, subphaseBean));
		buildSubphaseRecordInformations(subphaseBean, subphase, listParts, listUserUfi, contextSynchroBOData);
		
		subphaseBean.setStatus(StatusType.valueOf(subphase.getState().getStatus().toString()));
		subphaseBean.setAlternative(subphase.getIsAlternative());
		subphaseBean.setType(subphase.getType());
		
		if (subphase.getNumber() != 0) {
			subphaseBean.setNumber(subphase.getNumber());
		} else {
			subphaseBean.setNumber(Integer.valueOf(subphase.getDisplayId()));
		}


		if (subphase.getInfoSubPhase() != null) {
			subphaseBean.setMnemonic(subphase.getInfoSubPhase().getMnemonic());
			//			subphaseBean.setWorkstation(subphase.getInfoSubPhase().getWorkstation()); FIXME  TBD Missing for assembly
			if (subphase.getInfoSubPhase().getStandardTime() != null) {
				subphaseBean.setTuningTime(subphase.getInfoSubPhase().getStandardTime().getTuningTime().doubleValue());
				subphaseBean.setUnitTime(subphase.getInfoSubPhase().getStandardTime().getUnitTime().doubleValue());
			}
			
			if (subphase.getInfoSubPhase().getStatusInst() != null) {
				String statusType = "";
				if (subphase.getInfoSubPhase().getStatusInst().getStatusInst() == StatusInstType.FROZEN) {
					statusType = "F";
				} else if (subphase.getInfoSubPhase().getStatusInst().getStatusInst() == StatusInstType.SETUP) {
					statusType = "M";
				}
				subphaseBean.setSubPhaseType(statusType);
			}
		}

		if (subphase.getResources() != null) {
			subphaseBean.setDocuments(BoDbDocumentBuilder.buildDocuments(subphase.getResources().getDocuments()));
			subphaseBean.setTools(BoDbToolBuilder.getOrBuildBoToolFromSubShape(subphase.getResources().getTools()));
			subphaseBean.setIngredients(BoDbIngredientBuilder.buildIngredients(subphase.getResources().getIngredients(), subphaseBean));
			subphaseBean.setMarks(BoDbMarkBuilder.buildMarks(subphase.getResources().getMarks(), listParts, subphaseBean));
		}

		if (subphase.getInstance() != 0) {
			subphaseBean.setPart(BoDbPartBuilder.getPartByInstanceId(subphase.getInstance(), listParts));
		}
		
		subphaseBean.setUserUfi(BoDbUserUfiBuilder.getOrbuildBeanFromUserMark(listUserUfi, subphase.getState().getUserMark()));
		
		if(subphase.getState().getUserMark() != null && 
				StatusType.DONE.equals(StatusType.valueOf(subphase.getState().getStatus().value()))) {
			subphaseBean.setDate(subphase.getState().getUserMark().getDateTime());
		}
		
		return subphaseBean;
	}	

	private static void buildSubphaseRecordInformations(BoSubPhase subphaseBean, SubPhase subphase, Set<? extends IBoPart> listParts, 
			List<IBoUserUfi> listUserUfi, ContextSynchronizeBoData contextSynchroBOData) {
		Tasks tasks = subphase.getTasks();
		List<IBoTool> listBoTool = new ArrayList<IBoTool>();
		if (tasks != null) {
			for (TasksItem taskitem : tasks.getTasksItem()) {
				Object taskChoice = taskitem.getChoiceValue();
				if (taskChoice instanceof Task) {
					addTaskRecordInformations(subphaseBean, subphase, (Task) taskChoice, listParts, listUserUfi, listBoTool, contextSynchroBOData);
				} else if (taskChoice instanceof TaskGroup) {
					TaskGroup taskGroup = (TaskGroup) taskChoice;
					if (taskGroup.getTask() != null) {
						for (Task task : taskGroup.getTask()) {
							addTaskRecordInformations(subphaseBean, subphase, task, listParts, listUserUfi, listBoTool, contextSynchroBOData);
						}
					}
				}
			}
		}
	}

	private static void addTaskRecordInformations(BoSubPhase boSubPhase, SubPhase subPhase, Task task, Set<? extends IBoPart> listBoPart, 
			List<IBoUserUfi> listUserUfi, List<IBoTool> listToolUfi, ContextSynchronizeBoData contextSynchroBOData) {
		if (task.getTaskChoice() != null) {
			for (TaskChoiceItem taskItem : task.getTaskChoice().getTaskChoiceItem()) {
				if (taskItem.getTaskAction() != null) {
					addTaskActionInformations(boSubPhase, subPhase, task, taskItem.getTaskAction(), listBoPart, listUserUfi, contextSynchroBOData);
				} else if (taskItem.getTaskMark() != null) {
					TaskAction taskAction = taskItem.getTaskMark().getTaskAction();
					addTaskActionInformations(boSubPhase, subPhase, task, taskAction, listBoPart, listUserUfi, contextSynchroBOData);
				} else if (taskItem.getTaskActionMeasure() != null) {
					TaskAction taskAction = taskItem.getTaskActionMeasure().getTaskAction();
					// Build record of the TaskActionMeasure
					BoRecord recordBean = addTaskActionInformations(boSubPhase, subPhase, task, taskAction, listBoPart, listUserUfi, contextSynchroBOData);
					// Get tool from taskActionMeasure record
					if (taskItem.getTaskActionMeasure().getMeasureTool() != null && recordBean != null) {
						MeasureTool measureTool = taskItem.getTaskActionMeasure().getMeasureTool();
						BoTool boTool = BoDbToolBuilder.getOrBuildBoToolFromMeasureTool(measureTool);
						if (boTool != null) {
							listToolUfi.add(boTool);
							recordBean.setTool(boTool);
						}
					}
				} else if (taskItem.getTaskPiloting() != null) {
					TaskAction taskAction = taskItem.getTaskPiloting().getTaskAction();
					BoRecord recordBean = addTaskActionInformations(boSubPhase, subPhase, task, taskAction, listBoPart, listUserUfi, contextSynchroBOData);
					// Get tool from taskPiloting record
					if (recordBean != null) {
						TaskPiloting taskPiloting = taskItem.getTaskPiloting();
						BoTool boTool = BoDbToolBuilder.getOrBuildBoToolFromTaskPiloting(taskPiloting);
						if (boTool != null) {
							listToolUfi.add(boTool);
							recordBean.setTool(boTool);
						}
					}
				} else if (taskItem.getTaskActionTable() != null) {
					TableElement_type tableElement = taskItem.getTaskActionTable();
					for (Tgroup group : tableElement.getTgroup()) {
						for (Row row : group.getTbody().getRow()) {
							for (Entry entry : row.getEntry()) {
								if (entry.getTaskAction() != null) {
									for (TaskAction taskAction : entry.getTaskAction()) {
										addTaskActionInformations(boSubPhase, subPhase, task, taskAction, listBoPart, listUserUfi, contextSynchroBOData);
									}
								}
							}
						}
					}
				}
			}
		}
	}

	public static BoRecord addTaskActionInformations(BoSubPhase boSubPhase, SubPhase subPhase, Task task, TaskAction taskAction, 
			Set<? extends IBoPart> boParts, List<IBoUserUfi> listUserUfi, ContextSynchronizeBoData contextSynchroBOData) {
		BoRecord recordBean = null;
		if (taskAction != null) {
			recordBean = BoDbRecordBuilder.buildRecord(boSubPhase, subPhase, task, taskAction, boParts, listUserUfi, contextSynchroBOData);
			if(boSubPhase.getRecords() == null){
				boSubPhase.setRecords(new ArrayList<BoRecord>());
			}
			boSubPhase.getRecords().add(recordBean);
		}
		return recordBean;
	}

}
