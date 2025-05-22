package turbomeca.gamme.assembly.services.bodb.bobuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import turbomeca.gamme.assembly.services.model.data.Operation;
import turbomeca.gamme.assembly.services.model.data.Operations;
import turbomeca.gamme.assembly.services.model.data.ScheduleType;
import turbomeca.gamme.assembly.services.model.data.types.StatusInstType;
import turbomeca.gamme.ecran.services.bodb.bo.BoOperation;
import turbomeca.gamme.ecran.services.bodb.bo.BoSchedule;
import turbomeca.gamme.ecran.services.bodb.bo.BoSubPhase;
import turbomeca.gamme.ecran.services.bomodel.IBoPart;
import turbomeca.gamme.ecran.services.bomodel.IBoSubPhase;
import turbomeca.gamme.ecran.services.bomodel.IBoUserUfi;
import turbomeca.gamme.ecran.services.common.enumtypes.StatusType;
import turbomeca.gamme.ecran.services.runtime.bean.ContextSynchronizeBoData;

public class BoDbOperationBuilder {

	private BoDbOperationBuilder() {
		throw new IllegalStateException("Utility class"); //$NON-NLS-1$
	}
	
	public static List<BoOperation> buildScheduleOperationsBean (BoSchedule boSchedule, ScheduleType schedule, 
			Set<? extends IBoPart> listParts, List<String> listIdOperation, List<String> listIdSubPhases, 
			ContextSynchronizeBoData contextSynchroBOData) {
		List<BoOperation> operationsBean = new ArrayList<BoOperation>();
		List<IBoUserUfi> listUserUfi = new ArrayList<IBoUserUfi>();
		operationsBean.addAll(buildOperationsBean(boSchedule, schedule.getOperations(), 
				listParts, listUserUfi, listIdOperation, listIdSubPhases, contextSynchroBOData));
		return operationsBean;
	}
	
	public static List<BoOperation> buildOperationsBean(BoSchedule boSchedule, Operations operations, 
			Set<? extends IBoPart> listParts, List<IBoUserUfi> listUserUfi, List<String> listIdOperation, 
			List<String> listIdSubPhases, ContextSynchronizeBoData contextSynchroBOData) {
		List<BoOperation> operationsBean = new ArrayList<BoOperation>();
		for (Operation operation : operations.getOperation()) {
			// Check if Operation is in edition objects list
			if( (listIdOperation != null && listIdOperation.contains(operation.getId()))
					|| listIdOperation == null){
				operationsBean.add(buildOperation(boSchedule, operation, listParts, listUserUfi, listIdSubPhases, contextSynchroBOData));
			}
		}
		return operationsBean;
	}

	public static BoOperation buildOperation(BoSchedule boSchedule, Operation operation,
			Set<? extends IBoPart> listParts, List<IBoUserUfi> listUserUfi, List<String> listIdSubPhases, 
			ContextSynchronizeBoData contextSynchroBOData) {
		BoOperation operationBean = new BoOperation();
		if(operation != null){
			operationBean.setUfiId(operation.getId());
			if (operation.getNumber() != 0) {
				operationBean.setNumber(operation.getNumber());
			} else {
				operationBean.setNumber(Integer.valueOf(operation.getDisplayId()));
			}
			operationBean.setStatus(StatusType.valueOf(operation.getState().getStatus().value()));
			operationBean.setType(operation.getType());
			
			if (operation.getInfoOperation() != null) {
				operationBean.setWorkstation(operation.getInfoOperation().getWorkstation());
				operationBean.setMnemonic(operation.getInfoOperation().getMnemonic());
				
				if (operation.getInfoOperation().getStatusInst() != null) {
					String operationType = "";
					if (operation.getInfoOperation().getStatusInst().getStatusInst() == StatusInstType.FROZEN) {
						operationType = "F";
					} else if (operation.getInfoOperation().getStatusInst().getStatusInst() == StatusInstType.SETUP) {
						operationType = "S";
					}
					operationBean.setOperationType(operationType);
				}
				
				if (operation.getInfoOperation().getStandardTime() != null) {
					operationBean.setTuningTime(operation.getInfoOperation().getStandardTime().getTuningTime().doubleValue());
					operationBean.setUnitTime(operation.getInfoOperation().getStandardTime().getUnitTime().doubleValue());
				}
			}
			
//			operationBean.setImSource(operation.get); FIXME TBD Zone neutralisee
			operationBean.setSubPhases(BoDbSubPhaseBuilder.buildOperationSubphasesBean(operation, listParts, listUserUfi, listIdSubPhases, contextSynchroBOData));
			
			operationBean.setSchedule(boSchedule);
			operationBean.setUserUfi(BoDbUserUfiBuilder.getOrbuildBeanFromUserMark(listUserUfi, operation.getState().getUserMark()));
			for(IBoSubPhase boSubphase : operationBean.getSubPhases()){
				((BoSubPhase)boSubphase).setOperation(operationBean);
			}
			
			if(operation.getState().getUserMark() != null &&
					StatusType.DONE.equals(StatusType.valueOf(operation.getState().getStatus().value()))) {
				operationBean.setDate(operation.getState().getUserMark().getDateTime());
			}
		}
		return operationBean;
	}	
}
