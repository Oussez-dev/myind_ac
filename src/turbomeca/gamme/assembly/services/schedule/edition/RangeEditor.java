package turbomeca.gamme.assembly.services.schedule.edition;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import turbomeca.gamme.assembly.services.schedule.edition.action.GetContextEditingAction;
import turbomeca.gamme.assembly.services.schedule.edition.action.GetStatusAction;
import turbomeca.gamme.assembly.services.schedule.edition.action.SetContextEditingAction;
import turbomeca.gamme.assembly.services.schedule.edition.action.SetDoubleValidationAction;
import turbomeca.gamme.assembly.services.schedule.edition.action.SetRangeClearAction;
import turbomeca.gamme.assembly.services.schedule.edition.action.SetRangeEditedAction;
import turbomeca.gamme.assembly.services.schedule.edition.action.SetRangeFreeAction;
import turbomeca.gamme.assembly.services.schedule.edition.action.SetRangeFreeDoubleValidation;
import turbomeca.gamme.assembly.services.schedule.edition.action.SetRangeIdentificationAction;
import turbomeca.gamme.assembly.services.schedule.edition.action.SetRangeModifyAction;
import turbomeca.gamme.assembly.services.schedule.edition.action.SetRangeReloadedAction;
import turbomeca.gamme.assembly.services.schedule.edition.action.SetRangeStatusAction;
import turbomeca.gamme.assembly.services.schedule.edition.wrapper.IWrapperRange;
import turbomeca.gamme.assembly.services.schedule.loader.AWrapperRangeLoader;
import turbomeca.gamme.assembly.services.schedule.loader.IWrapperRangeLoader;
import turbomeca.gamme.assembly.services.schedule.model.DoubleValidation;
import turbomeca.gamme.assembly.services.schedule.model.Instance;
import turbomeca.gamme.ecran.services.common.bean.ContextUser;
import turbomeca.gamme.ecran.services.common.exceptions.RangeIoException;
import turbomeca.gamme.ecran.services.editing.IRangeEditor;
import turbomeca.gamme.ecran.services.runtime.bean.ContextEditing;
import turbomeca.gamme.ecran.services.schedule.IScheduleWrapper;

/**
 * FIXME REPORT : 
 *  This skeleton is created to resolve dependencies for EPIC dependencies
 *  Its an empty shell to be filled with content of
 *  turbomeca.gamme.accessory.server.dao.range.edition.RangeEditor
 *  
 * @author ahermo
 *
 */
public class RangeEditor implements IRangeEditor<IWrapperRange>, Serializable {

	/** */	
	private static final long serialVersionUID = 1L;

	/** */
	private List<IWrapperRange> rangeWrappers;

	/** */
	private IWrapperRangeLoader rangeWrapperLoader;

	/**
	 * Constructor
	 * 
	 * @param rangeWrapper
	 */
	public RangeEditor(AWrapperRangeLoader<?> rangeWrapperLoader) {
		this.rangeWrapperLoader = rangeWrapperLoader;
	}

	/**
	 * Constructor
	 */
	public RangeEditor() {
	}

	/**
	 * 
	 * @param contextUser
	 * @throws RangeIoException
	 */
	public void load(ContextUser contextUser) throws RangeIoException {
		setRangeWrappers(rangeWrapperLoader.load(contextUser));
	}

	@Override
	public boolean load(String os, String scheduleType, ContextUser contextUser)
			throws RangeIoException {
		return false;
	}

	@Override
	public void load() throws RangeIoException {
		setRangeWrappers(rangeWrapperLoader.load());
	}
	
	
	/**
	 * 
	 * @throws RangeIoException
	 */
	public void close() throws RangeIoException {
		rangeWrapperLoader.unload();
	}

	/**
	 * 
	 * @throws RangeIoException
	 */
	public void save() throws RangeIoException {
		rangeWrapperLoader.save();
	}
	/**
	 * 
	 * @throws RangeIoException
	 */
	public void removeAndSave() throws RangeIoException {
		rangeWrapperLoader.removeAndSave();
	}

	/**
	 * Load, execute and save if needed
	 * @param contextUser
	 * @param action
	 * @throws RangeIoException
	 */
	public void execute(ContextUser contextUser, IRangeEditorAction action) throws RangeIoException {
		try {
			load(contextUser);
			run(contextUser, new IRangeEditorAction[] { action });
			if (action.isModifyRange()) {
				save();
			}
		} finally {
			close();
		}
	}

	/**
	 * 
	 * @param contextUser
	 * @param action
	 * @throws RangeIoException
	 */
	private void run(ContextUser contextUser, IRangeEditorAction action) throws RangeIoException {
		run(contextUser, new IRangeEditorAction[] { action });
	}

	/**
	 * 
	 * @param contextUser
	 * @param actions
	 * @return
	 * @throws RangeIoException
	 */
	private boolean run(ContextUser contextUser, IRangeEditorAction[] actions)
			throws RangeIoException {
		boolean mustSave = false;
		for (IRangeEditorAction action : actions) {
			for (IWrapperRange wrapperRange : getRangeWrappers()) {
				action.execute(contextUser, wrapperRange);
			}
			mustSave |= action.isModifyRange();
		}
		return mustSave;
	}

	@Override
	public String getStatus(ContextUser contextUser) throws RangeIoException {
		GetStatusAction statusAction = new GetStatusAction();
		run(contextUser, statusAction);
		return statusAction.getStatus().value();
	}

	@Override
	public void setRangeIdentification(ContextUser contextUser) throws RangeIoException {
		run(contextUser, new SetRangeIdentificationAction());
	}

	@Override
	public void setRangeEdited(ContextUser contextUser) throws RangeIoException {
		run(contextUser, new SetRangeEditedAction());
	}

	@Override
	public void setRangeClear(ContextUser contextUser) throws RangeIoException {
		run(contextUser, new SetRangeClearAction());
	}

	@Override
	public void setRangeFree(ContextUser contextUser) throws RangeIoException {
		run(contextUser, new SetRangeFreeAction());
	}

	@Override
	public void setRangeModify(ContextUser contextUser, String status, String[] statusDomain)
			throws RangeIoException {
		run(contextUser, new IRangeEditorAction[] { new SetRangeModifyAction(),
				new SetRangeStatusAction(status) });
	}

	@Override
	public boolean setDoubleValidation(ContextUser contextUser, String userLogin) throws RangeIoException {
		return run(contextUser, new IRangeEditorAction[] { new SetDoubleValidationAction(userLogin)});
	}

	@Override
	public boolean deleteDoubleValidation(ContextUser contextUser) throws RangeIoException {
		return run(contextUser, new IRangeEditorAction[] { new SetRangeFreeDoubleValidation()});
	}

	@Override
	public ContextEditing getContextEditing(ContextUser contextUser) throws RangeIoException {
		GetContextEditingAction getContextEditingAction = new GetContextEditingAction();
		run(contextUser, getContextEditingAction);
		return getContextEditingAction.getContextEditing();
	}

	@Override
	public ContextEditing setContextEditing(ContextUser contextUser, ContextEditing contextEditing)
			throws RangeIoException {
		return setContextEditing(contextUser, contextEditing, true);
	}

	/**
	 * set editable instance
	 * @param setEditable
	 * @return
	 */
	public boolean setInstanceEditable(boolean setEditable){
		boolean success = false;
		for(IWrapperRange wrapper : getRangeWrappers()){
			Instance instance = wrapper.getInstance();
			instance.setEditable(setEditable);
			success = true;
		}
		return success;
	}

	@Override
	public ContextEditing setDefaultContextEditing(ContextUser contextUser,
			ContextEditing contextEditing) throws RangeIoException {

		return setContextEditing(contextUser, contextEditing, true);
	}

	/**
	 * 
	 * @param contextUser
	 * @param contextEditing
	 * @return
	 * @throws RangeIoException
	 */
	private ContextEditing setContextEditing(ContextUser contextUser,
			ContextEditing contextEditing, boolean isDefault) throws RangeIoException {
		ContextEditing contextEditingCurrent = getContextEditing(contextUser);
		SetContextEditingAction setContextEditingAction = new SetContextEditingAction(
				contextEditingCurrent, contextEditing, isDefault);
		run(contextUser, setContextEditingAction);
		return getContextEditing(contextUser);
	}

	/**
	 * 
	 * @param contextUser
	 * @throws RangeIoException
	 */
	public void setRangeReloaded(ContextUser contextUser) throws RangeIoException {
		run(contextUser, new SetRangeReloadedAction());
	}

	/**
	 * @return the rangeWrapperLoader
	 */
	public IWrapperRangeLoader getRangeWrapperLoader() {
		return rangeWrapperLoader;
	}

	/**
	 * @param rangeWrapperLoader
	 *            the rangeWrapperLoader to set
	 */
	public void setRangeWrapperLoader(IWrapperRangeLoader rangeWrapperLoader) {
		this.rangeWrapperLoader = rangeWrapperLoader;
	}

	/**
	 * @param range
	 *            the range to set
	 */
	private void setRangeWrappers(List<IWrapperRange> rangeWrappers) {
		this.rangeWrappers = rangeWrappers;
	}

	/**
	 * @return the range
	 */
	public List<IWrapperRange> getRangeWrappers() {
		return rangeWrappers;
	}

	@Override
	public void loadWrappers(List<IWrapperRange> rangeWrappers) {
		setRangeWrappers(rangeWrappers);
	}


	@Override
	public String getFileName(ContextUser contextUser) throws RangeIoException {
		return null;
	}

	@Override
	public IScheduleWrapper getSchedule(ContextUser contextUser, String fileName, String officialFolder) throws RangeIoException {
		return null;
	}

	@Override
	public boolean getInstanceEditable() {
		boolean editable = false;
		for(IWrapperRange wrapper : getRangeWrappers()){
			Instance instance = wrapper.getInstance();
			editable = instance.getEditable();
		}
		return editable;
	}

	@Override
	public List<String> getSchedulesCrossValidations(String userLogin) throws RangeIoException {
		List<String> scheduleImpactedByCrossValidation = new ArrayList<String>();
		if(getRangeWrappers() != null) {
			for(IWrapperRange wrapperRange : getRangeWrappers()) {
				for(DoubleValidation currentDoubleValidation : wrapperRange.getDoubleValidations()) {
					if(!currentDoubleValidation.getValidate() 
							&& currentDoubleValidation.getLogin().toLowerCase().equals(userLogin.toLowerCase())) {
						scheduleImpactedByCrossValidation.add(wrapperRange.getRangeIdentification().getFile());
					}
				}
			}
		}
		return scheduleImpactedByCrossValidation;
	}
	
}
