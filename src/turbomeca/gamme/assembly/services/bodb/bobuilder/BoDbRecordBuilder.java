package turbomeca.gamme.assembly.services.bodb.bobuilder;

import java.math.BigDecimal;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.log4j.Logger;

import turbomeca.gamme.assembly.services.model.data.DataValue;
import turbomeca.gamme.assembly.services.model.data.InputChoice;
import turbomeca.gamme.assembly.services.model.data.InputValue;
import turbomeca.gamme.assembly.services.model.data.Interval;
import turbomeca.gamme.assembly.services.model.data.SubPhase;
import turbomeca.gamme.assembly.services.model.data.Task;
import turbomeca.gamme.assembly.services.model.data.TaskAction;
import turbomeca.gamme.assembly.services.model.data.Test;
import turbomeca.gamme.assembly.services.model.data.UserMarkType;
import turbomeca.gamme.ecran.services.bodb.bo.BoPart;
import turbomeca.gamme.ecran.services.bodb.bo.BoRecord;
import turbomeca.gamme.ecran.services.bodb.bo.BoSubPhase;
import turbomeca.gamme.ecran.services.bomodel.IBoPart;
import turbomeca.gamme.ecran.services.bomodel.IBoUserUfi;
import turbomeca.gamme.ecran.services.common.enumtypes.StatusType;
import turbomeca.gamme.ecran.services.runtime.bean.ContextSynchronizeBoData;

public class BoDbRecordBuilder {

	private static Logger logger = Logger.getLogger(BoDbRecordBuilder.class);

	public static BoRecord buildRecord(BoSubPhase boSubPhase, SubPhase subPhase, Task task, TaskAction taskAction, 
			Set<? extends IBoPart> boParts, List<IBoUserUfi> listUserUfi, ContextSynchronizeBoData contextSynchroBOData) {
		BoRecord recordBean = new BoRecord();

		if (taskAction.getInputAction() != null && taskAction.getInputAction().getInputActionChoice().getInputDocument() != null){
			recordBean.setBinaryValue(BodbRecordBinaryBuilder.buildRecordBinary(taskAction.getInputAction(), contextSynchroBOData, recordBean));
		}

		// FIXME save measure ?? inputdocument measure ??
		//		if (taskAction.getInputAction() != null && taskAction.getInputAction().getInputActionChoice().getInputDocument() != null 
		//				&& taskAction.getIdMeasureDataFile().isEmpty()) {
		//			recordBean.setBinaryValue(BodbRecordBinaryUtil.buildRecordBinary(taskAction.getIdMeasureDataFile(), contextConfig, contextUser, recordBean));
		//		}

		if (boParts != null) {
			int instanceIndex = BoDbPartBuilder.FIRST_INSTANCE_INDEX;
			if (taskAction.getInstance() != 0) {
				instanceIndex = taskAction.getInstance();
			} else if (task.getInstance() != 0) {
				instanceIndex = task.getInstance();
			} else if (subPhase.getInstance() != 0) {
				instanceIndex = subPhase.getInstance();
			}
			BoPart resultPart = BoDbPartBuilder.getPartByInstanceId(instanceIndex, boParts);
			if(resultPart !=null) {
				recordBean.setPart(resultPart);
			}
		}

		if(subPhase.getResources() != null && subPhase.getResources().getTools() != null
				&& subPhase.getResources().getTools().getTool().length > 0 && subPhase.getResources().getTools().getTool(0) != null) {
			// FIXME tool by record ? tool record ?
			// FIXME first tool only ? for each record of subPhase ?????
			//			recordBean.setTool(BoDbToolBuilder.getOrbuildBeanFromTool(subPhase.getResources().getTools().getTool(0), taskAction));
		}


		if (taskAction.getInputAction().getInputValue() != null) {
			InputValue inputValue = taskAction.getInputAction().getInputValue();

			// inputValue.getValueStatus() may be null on newly created schedules (on inputConstant for example)
			// FIXME: it may be a good idea to update XSL publication in order to setup inputConstant/@valueStatus during publication process 
			if(inputValue.getValueStatus()!=null && !inputValue.getValueStatus().toString().isEmpty()) {
				recordBean.setStatus(StatusType.valueOf(inputValue.getValueStatus().toString()));
				if (taskAction.getInputAction().getInputActionChoice().getInputChoice() != null) {
					InputChoice inputChoice = taskAction.getInputAction().getInputActionChoice().getInputChoice();
					Integer choiceIndex = Integer.parseInt(inputValue.getValue()) - 1;
					if (inputChoice.getVal().length > choiceIndex) {
						recordBean.setTextValue(inputChoice.getVal(choiceIndex).toString());
					} else if (inputChoice.getStringValue().length > choiceIndex) {
						recordBean.setTextValue(inputChoice.getStringValue(choiceIndex).getContent());
					} else if (inputChoice.getItemPara().length > choiceIndex) {
						recordBean.setTextValue(inputChoice.getItemPara(choiceIndex).getPara(0).getContent());
					}
				} else {
					recordBean.setTextValue(inputValue.getValue());
				}
			}
		}
		recordBean.setUfiId(taskAction.getId());
		recordBean.setMandatory(!taskAction.isOptional());

		if (taskAction.getValue() != null) {
			recordBean.setValueUnit(taskAction.getValue().getUnit());
		}

		if (taskAction.getValue() != null && taskAction.getValue().getName() != null
				&& !taskAction.getValue().getName().isEmpty()) {
			recordBean.setName(taskAction.getValue().getName());
		}


		if (taskAction.getInputAction().getInputValue() != null && taskAction.getInputAction().getInputValue().getUserMark() != null) {
			recordBean.setUserUfi(BoDbUserUfiBuilder.getOrbuildBeanFromUserMark(listUserUfi, taskAction.getInputAction().getInputValue().getUserMark()));
			XMLGregorianCalendar taskActionDate = getDate(taskAction);
			if (taskActionDate != null) {
				recordBean.setDate(taskActionDate.toGregorianCalendar().getTime());
			}
		}
		recordBean.setSubphase(boSubPhase);

		//		recordBean.setType(taskAction.getInputAction().get); // FIXME TBD Type : inputField/inputConstant/inputChoice/inputComputed/inputDocument/inputPicture ?? Releve de type charact ou non ?

		BigDecimal targetValue = null;
		BigDecimal toleranceMax = null;
		BigDecimal toleranceMin = null;

		if (taskAction.getTest() != null) {
			DataValue dataValue  = null;
			for (Test test : taskAction.getTest()) {
				dataValue = test.getDataValue();
				if (dataValue != null && dataValue.getDataValueChoice() != null) {
					if (dataValue.getDataValueChoice().getVal() != null) {
						targetValue = dataValue.getDataValueChoice().getVal();
					} else if (dataValue.getDataValueChoice().getInputRef() != null) {
						// FIXME TBD INPUT REF
					}
				}
			}

			if (targetValue != null) {
				recordBean.setValueTarget(targetValue.floatValue());
			}

			if (taskAction.getTest() != null && taskAction.getTest().length > 0) {
				for (Test test : taskAction.getTest()) {
					Interval interval = test.getInterval();
					if (interval != null) {
						if(interval.getMax() != null && interval.getMax().getDataValue().getDataValueChoice() != null 
								&& interval.getMax().getDataValue().getDataValueChoice().getVal() !=null) {
							toleranceMax = interval.getMax().getDataValue().getDataValueChoice().getVal();
						}

						if(interval.getMin() != null && interval.getMin().getDataValue().getDataValueChoice() != null 
								&& interval.getMin().getDataValue().getDataValueChoice().getVal() !=null) {
							toleranceMin = interval.getMin().getDataValue().getDataValueChoice().getVal();
						}
					} else if (dataValue != null && dataValue.getUncertainty() != null && targetValue != null) {
						toleranceMax = dataValue.getUncertainty().add(targetValue);
						toleranceMin = dataValue.getUncertainty().subtract(targetValue);
					}
				}
			}

			if (toleranceMax != null && toleranceMin != null) {
				recordBean.setMaxTolerance(toleranceMax.floatValue());
				recordBean.setMinTolerance(toleranceMin.floatValue());
			}
		}

		return recordBean;
	}

	private static XMLGregorianCalendar getDate(TaskAction taskAction) {
		XMLGregorianCalendar taskActionDate = null;
		if(taskAction.getInputAction() != null && taskAction.getInputAction().getInputValue() != null) {
			UserMarkType userMark = taskAction.getInputAction().getInputValue().getUserMark();
			if (userMark != null) {
				try {
					final GregorianCalendar calendar = new GregorianCalendar();
					calendar.setTime(userMark.getDateTime());
					taskActionDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
				} catch (DatatypeConfigurationException e) {
					logger.error("Error during building date of Record, id : " + taskAction.getId() + " -- " + e.getMessage(), e);;
				} catch (Exception e) {
					logger.error("Error during building date of Record, id : " + taskAction.getId() + " -- " + e.getMessage(), e);;
				}
			}
		}

		return taskActionDate;
	}
}
