package turbomeca.gamme.assembly.services.schedule.edition.action;

import turbomeca.gamme.assembly.services.schedule.edition.ARangeEditorAction;
import turbomeca.gamme.assembly.services.schedule.edition.wrapper.IWrapperRange;
import turbomeca.gamme.assembly.services.schedule.model.types.StatusRangeType;
import turbomeca.gamme.ecran.services.common.bean.ContextUser;

public class GetStatusAction extends ARangeEditorAction{

	private StatusRangeType status;
	
	@Override
	public boolean isModifyRange() {
		return false;
	}
	
	@Override
	public void execute(ContextUser contextUser, IWrapperRange rangeWrapper) {
		setStatus(rangeWrapper.getInstance().getStatus());
	}

	/**
	 * @param status the status to set
	 */
	private void setStatus(StatusRangeType status) {
		this.status = status;
	}

	/**
	 * @return the status
	 */
	public StatusRangeType getStatus() {
		return status;
	}

}
