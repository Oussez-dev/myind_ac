package turbomeca.gamme.assembly.services.schedule.edition.action;

import java.util.LinkedHashSet;

import turbomeca.gamme.assembly.services.schedule.edition.ARangeEditorAction;
import turbomeca.gamme.assembly.services.schedule.edition.wrapper.IWrapperRange;
import turbomeca.gamme.assembly.services.schedule.model.Edition;
import turbomeca.gamme.ecran.services.common.bean.ContextUser;
import turbomeca.gamme.ecran.services.runtime.bean.ContextEditing;

public class GetContextEditingAction extends ARangeEditorAction {

	private ContextEditing contextEditing;
	private LinkedHashSet<String> objectsEdited;
	private LinkedHashSet<String> objectsLocked;
	private boolean outOfDate;
	
	public GetContextEditingAction() {
		contextEditing = new ContextEditing();
		objectsLocked = new LinkedHashSet<String>();
		objectsEdited = new LinkedHashSet<String>();
		outOfDate = false;
	}
	
	@Override
	public boolean isModifyRange() {
		return false;
	}
	
	@Override
	public void execute(ContextUser contextUser, IWrapperRange rangeWrapper) {
		boolean isPrimary = isUserPrimary(rangeWrapper);
	
		for (Edition edition : rangeWrapper.getEditions()) {
			if (isUserEdition(edition, contextUser)){
				for (String object : edition.getEditedObject().getObject()) {
					objectsEdited.add(object);
				}
				outOfDate |= edition.isOutOfDate();
				edition.setIsOpen(true);
				if(edition.getHostname().isEmpty()){
					edition.setHostname(contextUser.getRemoteIp());
				}
			} else {
				for (String operation : edition.getEditedObject().getObject()) {
					objectsLocked.add(operation);
				}
			}
			//If Primary set flags primary=true
			if(isPrimary){
				edition.setMain(isPrimary);
			}
		}
	}
	
	/**
	 * @return the contextEditing
	 */
	public ContextEditing getContextEditing() {
		String[] lockedObject = new String[objectsLocked.size()];
		contextEditing.setObjectLocked(objectsLocked.toArray(lockedObject));
		String[] editedObject = new String[objectsEdited.size()];
		contextEditing.setObjectEdited(objectsEdited.toArray(editedObject));
		contextEditing.setOutOfDate(outOfDate);
		return contextEditing;
	}

	/**
	 * @param contextEditing the contextEditing to set
	 */
	public void setContextEditing(ContextEditing contextEditing) {
		this.contextEditing = contextEditing;
	}
}
