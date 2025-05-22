package turbomeca.gamme.assembly.services.schedule.edition.action;

import java.util.Calendar;

import turbomeca.gamme.assembly.services.schedule.edition.ARangeEditorAction;
import turbomeca.gamme.assembly.services.schedule.edition.wrapper.IWrapperRange;
import turbomeca.gamme.assembly.services.schedule.model.Edition;
import turbomeca.gamme.assembly.services.schedule.model.Modification;
import turbomeca.gamme.ecran.services.common.bean.ContextUser;

public class SetRangeModifyAction extends ARangeEditorAction {

	@Override
	public void execute(ContextUser contextUser, IWrapperRange rangeWrapper) {
		Modification modification = rangeWrapper.getModification();
		if (modification == null) {
			modification = new Modification(); 
			rangeWrapper.setModification(modification);
		}
		modification.setDate(Calendar.getInstance().getTime());
		modification.setUser(contextUser.getUserName());
		modification.setLogin(contextUser.getLogin());
		for (Edition edition : rangeWrapper.getEditions()) {
			if (!isUserEdition(edition, contextUser)) {
				edition.setOutOfDate(true);
			}
		}
	}
}
