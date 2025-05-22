package turbomeca.gamme.assembly.services.schedule.edition.action;

import turbomeca.gamme.assembly.services.schedule.edition.ARangeEditorAction;
import turbomeca.gamme.assembly.services.schedule.edition.wrapper.IWrapperRange;
import turbomeca.gamme.ecran.services.common.bean.ContextUser;

public class SetRangeClearAction extends ARangeEditorAction {

	@Override
	public void execute(ContextUser contextUser, IWrapperRange rangeWrapper) {
		rangeWrapper.removeRange();
	}

}
