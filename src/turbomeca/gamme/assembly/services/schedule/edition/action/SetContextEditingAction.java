package turbomeca.gamme.assembly.services.schedule.edition.action;

import turbomeca.gamme.assembly.services.schedule.edition.ARangeEditorAction;
import turbomeca.gamme.assembly.services.schedule.edition.wrapper.IWrapperRange;
import turbomeca.gamme.assembly.services.schedule.model.Edition;
import turbomeca.gamme.ecran.services.common.bean.ContextUser;
import turbomeca.gamme.ecran.services.common.exceptions.RangeIoException;
import turbomeca.gamme.ecran.services.runtime.bean.ContextEditing;

public class SetContextEditingAction extends ARangeEditorAction {

	/** */
	private ContextEditing contextEditingCurrent;
	/** */
	private ContextEditing contextEditingWanted;
	/** */
	private boolean raiseException;

	/**
	 * 
	 * @param contextEditingCurrent
	 * @param contextEditingWanted
	 * @param raiseException
	 */
	public SetContextEditingAction(ContextEditing contextEditingCurrent,
			ContextEditing contextEditingWanted, boolean raiseException) {
		setContextEditingCurrent(contextEditingCurrent);
		setContextEditingWanted(contextEditingWanted);
		setRaiseException(raiseException);
	}

	@Override
	public void execute(ContextUser contextUser, IWrapperRange rangeWrapper)
			throws RangeIoException {
		Edition userEdition = getUserEdition(rangeWrapper, contextUser);
		if (userEdition != null) {
			userEdition.getEditedObject().removeAllObject();
			if (getContextEditingWanted() != null
					&& getContextEditingWanted().getObjectEdited() != null) {
				for (String objectEdited : getContextEditingWanted().getObjectEdited()) {
					if (contains(getContextEditingCurrent().getObjectLocked(), objectEdited)) {
						if (!isRaiseException()) {
							throw new RangeIoException(
									RangeIoException.EXCEPTION_EDITING_OBJECT_USED);
						}
					} else {
						userEdition.getEditedObject().addObject(objectEdited);
					}
				}
			}
			
			//If User Only on Range Set Flags TO primary
			if(isUserPrimary(rangeWrapper)){
				userEdition.setMain(true);
			}
			
		}
		else {
			throw new RangeIoException(RangeIoException.EXCEPTION_NOT_EDITED);
		}
	}

	/**
	 * 
	 * @param listValues
	 * @param value
	 * @return
	 */
	private boolean contains(String[] listValues, String value) {
		boolean isContained = false;
		for (String listValue : listValues) {
			if (listValue.equals(value)) {
				isContained = true;
				break;
			}
		}
		return isContained;
	}

	/**
	 * @param raiseException
	 *            the raiseException to set
	 */
	public void setRaiseException(boolean raiseException) {
		this.raiseException = raiseException;
	}

	/**
	 * @return the raiseException
	 */
	public boolean isRaiseException() {
		return raiseException;
	}

	/**
	 * @param contextEditingCurrent
	 *            the contextEditingCurrent to set
	 */
	public void setContextEditingCurrent(ContextEditing contextEditingCurrent) {
		this.contextEditingCurrent = contextEditingCurrent;
	}

	/**
	 * @return the contextEditingCurrent
	 */
	public ContextEditing getContextEditingCurrent() {
		return contextEditingCurrent;
	}

	/**
	 * @param contextEditingWanted
	 *            the contextEditingWanted to set
	 */
	public void setContextEditingWanted(ContextEditing contextEditingWanted) {
		this.contextEditingWanted = contextEditingWanted;
	}

	/**
	 * @return the contextEditingWanted
	 */
	public ContextEditing getContextEditingWanted() {
		return contextEditingWanted;
	}
}
