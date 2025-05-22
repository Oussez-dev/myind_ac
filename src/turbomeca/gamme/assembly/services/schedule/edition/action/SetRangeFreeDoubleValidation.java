package turbomeca.gamme.assembly.services.schedule.edition.action;

import turbomeca.gamme.assembly.services.schedule.edition.ARangeEditorAction;
import turbomeca.gamme.assembly.services.schedule.edition.wrapper.IWrapperRange;
import turbomeca.gamme.assembly.services.schedule.model.DoubleValidation;
import turbomeca.gamme.ecran.services.common.bean.ContextUser;
import turbomeca.gamme.ecran.services.common.exceptions.RangeIoException;

public class SetRangeFreeDoubleValidation extends ARangeEditorAction{

	@Override
	public void execute(ContextUser contextUser, IWrapperRange rangeWrapper)
			throws RangeIoException {
		DoubleValidation userValidation = getUserValidation(rangeWrapper,contextUser.getLogin());
		if (userValidation != null) {
			rangeWrapper.removeDoubleValidation(userValidation);
		}
	}

}
