package turbomeca.gamme.assembly.services.schedule.edition.action;

import turbomeca.gamme.assembly.services.schedule.edition.ARangeEditorAction;
import turbomeca.gamme.assembly.services.schedule.edition.wrapper.IWrapperRange;
import turbomeca.gamme.assembly.services.schedule.model.Edition;
import turbomeca.gamme.ecran.services.common.bean.ContextUser;
import turbomeca.gamme.ecran.services.common.constants.OpenMode;
import turbomeca.gamme.ecran.services.common.exceptions.RangeIoException;

public class SetRangeFreeAction extends ARangeEditorAction {

	@Override
	public void execute(ContextUser contextUser, IWrapperRange rangeWrapper) throws RangeIoException {
		if (contextUser.getWritingMode() != OpenMode.OPEN_MODE_READ) {
			Edition edition = getUserEdition(rangeWrapper, contextUser);
			if (edition != null) {
				rangeWrapper.removeEdition(edition);
				rangeWrapper.getInstance().setEditable(true);
				removeDelegateEdition(rangeWrapper, edition);
			} else {
				throw new RangeIoException(RangeIoException.EXCEPTION_RANGE_ALREADY_OPENED);
			}
		}
	}
}
