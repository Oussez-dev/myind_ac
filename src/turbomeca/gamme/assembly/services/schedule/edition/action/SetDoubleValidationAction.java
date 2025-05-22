package turbomeca.gamme.assembly.services.schedule.edition.action;

import turbomeca.gamme.assembly.services.schedule.edition.ARangeEditorAction;
import turbomeca.gamme.assembly.services.schedule.edition.wrapper.IWrapperRange;
import turbomeca.gamme.assembly.services.schedule.model.DoubleValidation;
import turbomeca.gamme.ecran.services.common.bean.ContextUser;
import turbomeca.gamme.ecran.services.common.exceptions.RangeIoException;

public class SetDoubleValidationAction extends ARangeEditorAction {

	String userLogin;
	
	public SetDoubleValidationAction(String userLogin){
		setUserLogin(userLogin);
	}
	
	@Override
	public void execute(ContextUser contextUser, IWrapperRange rangeWrapper) throws RangeIoException {
		DoubleValidation userValidation = getUserValidation(rangeWrapper, getUserLogin());
		if (userValidation == null) {
			userValidation=new DoubleValidation();
			rangeWrapper.addDoubleValidation(userValidation);
			userValidation.setLogin(getUserLogin());
			userValidation.setValidate(false);
		}
		
	}

	public String getUserLogin() {
		return userLogin;
	}

	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}
}
