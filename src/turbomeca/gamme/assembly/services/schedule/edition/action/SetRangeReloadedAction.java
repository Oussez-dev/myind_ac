package turbomeca.gamme.assembly.services.schedule.edition.action;

import turbomeca.gamme.assembly.services.schedule.edition.ARangeEditorAction;
import turbomeca.gamme.assembly.services.schedule.edition.wrapper.IWrapperRange;
import turbomeca.gamme.assembly.services.schedule.model.Edition;
import turbomeca.gamme.ecran.services.common.bean.ContextUser;
import turbomeca.gamme.ecran.services.common.exceptions.RangeIoException;

public class SetRangeReloadedAction extends ARangeEditorAction {

	@Override
	public void execute(ContextUser contextUser, IWrapperRange rangeWrapper) throws RangeIoException {
		Edition edition = getUserEdition(rangeWrapper, contextUser);
		if (edition == null) {
			throw new RangeIoException(RangeIoException.EXCEPTION_USER_EDITION_NOT_FOUND);
		}
		edition.setOutOfDate(false);
	}

}
