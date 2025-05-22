package turbomeca.gamme.assembly.services.schedule.edition.action;

import turbomeca.gamme.assembly.services.schedule.edition.ARangeEditorAction;
import turbomeca.gamme.assembly.services.schedule.edition.wrapper.IWrapperRange;
import turbomeca.gamme.assembly.services.schedule.model.Instance;
import turbomeca.gamme.assembly.services.schedule.model.types.StatusRangeType;
import turbomeca.gamme.ecran.services.common.bean.ContextUser;

public class SetRangeStatusAction extends ARangeEditorAction {

	/** Range Status */
	private String status;

	/**
	 * Constructor
	 * 
	 * @param status
	 *            range status
	 * @param statusDomains
	 *            status per domain
	 */
	public SetRangeStatusAction(String status) {
		this.status = status;
	}

	@Override
	public void execute(ContextUser contextUser, IWrapperRange rangeWrapper) {
		Instance instance = rangeWrapper.getInstance();
		if (status != null) {
			instance.setStatus(StatusRangeType.valueOf(status));
		}
	}
}
