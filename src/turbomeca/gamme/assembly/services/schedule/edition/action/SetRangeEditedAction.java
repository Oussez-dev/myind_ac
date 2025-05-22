package turbomeca.gamme.assembly.services.schedule.edition.action;

import turbomeca.gamme.assembly.services.schedule.edition.ARangeEditorAction;
import turbomeca.gamme.assembly.services.schedule.edition.wrapper.IWrapperRange;
import turbomeca.gamme.assembly.services.schedule.model.EditedObject;
import turbomeca.gamme.assembly.services.schedule.model.Edition;
import turbomeca.gamme.ecran.services.common.bean.ContextUser;
import turbomeca.gamme.ecran.services.common.exceptions.RangeIoException;

public class SetRangeEditedAction extends ARangeEditorAction {

	@Override
	public void execute(ContextUser contextUser, IWrapperRange rangeWrapper) throws RangeIoException {
		Edition edition = getUserEdition(rangeWrapper, contextUser);
		if (edition == null) {
			if (rangeWrapper.getInstance().isEditable()) {
				Edition newEdition = new Edition();
				newEdition.setUser(contextUser.getUserName());
				newEdition.setHostname(contextUser.getRemoteIp());
				newEdition.setLogin(contextUser.getLogin());
				newEdition.setOutOfDate(false);
				newEdition.setEditedObject(new EditedObject());
				rangeWrapper.addEdition(newEdition);
			}
			else {
				throw new RangeIoException(RangeIoException.EXCEPTION_RANGE_ALREADY_OPENED);
			}
		}
		else {
			edition.setOutOfDate(false);
		}
	}
}
