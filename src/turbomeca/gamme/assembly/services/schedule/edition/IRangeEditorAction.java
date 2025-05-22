package turbomeca.gamme.assembly.services.schedule.edition;

import turbomeca.gamme.assembly.services.schedule.edition.wrapper.IWrapperRange;
import turbomeca.gamme.ecran.services.common.bean.ContextUser;
import turbomeca.gamme.ecran.services.common.exceptions.RangeIoException;

public interface IRangeEditorAction {

	public abstract boolean isModifyRange();
	
	public abstract void execute(ContextUser contextUser, IWrapperRange rangeWrapper)
			throws RangeIoException;
}
