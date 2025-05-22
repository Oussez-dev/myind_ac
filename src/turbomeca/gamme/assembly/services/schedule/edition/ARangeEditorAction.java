package turbomeca.gamme.assembly.services.schedule.edition;

import turbomeca.gamme.assembly.services.schedule.edition.wrapper.IWrapperRange;
import turbomeca.gamme.assembly.services.schedule.model.DoubleValidation;
import turbomeca.gamme.assembly.services.schedule.model.Edition;
import turbomeca.gamme.ecran.services.common.bean.ContextUser;
import turbomeca.gamme.ecran.services.common.exceptions.RangeIoException;

public abstract class ARangeEditorAction implements IRangeEditorAction {

	/**
	 * 
	 * @param edition
	 * @param contextUser
	 * @return
	 */
	protected boolean isUserEdition(Edition edition, ContextUser contextUser) {
		//return (edition.getLogin().equals(contextUser.getLogin()));
		return (edition.getLogin().equals(contextUser.getLogin())
				&& (edition.getHostname().equals(contextUser.getRemoteIp())
				    || edition.getHostname().equals("")));
	}
	
	/**
	 * Detect if current validation from user 
	 * @param edition
	 * @param contextUser
	 * @return
	 */
	protected boolean isUserDoubleValidation(DoubleValidation doubleValidation, String userLogin) {
		return (doubleValidation.getLogin().equals(userLogin));
	}
	
	/**
	 * 
	 * @param contextUser
	 * @return
	 */
	protected Edition getUserEdition(IWrapperRange wrapperRange, ContextUser contextUser) {
		Edition userEdition = null;
		for (Edition edition : wrapperRange.getEditions()) {
			if (isUserEdition(edition, contextUser)) {
				userEdition = edition;
			}
		}
		return userEdition;
	}
	
	protected DoubleValidation getUserValidation(IWrapperRange wrapperRange,String userLogin) {
		DoubleValidation validation = null;
		for (DoubleValidation currentValidation : wrapperRange.getDoubleValidations()) {
			if (isUserDoubleValidation(currentValidation, userLogin)) {
				validation = currentValidation;
				break;
			}
		}
		return validation;
	}
	
	
	protected void removeDelegateEdition(IWrapperRange wrapperRange,Edition edition){
		if(edition.isMain()){
			for(Edition currentEdition :wrapperRange.getEditions()){
				if(currentEdition.isDelegate() && !currentEdition.isIsOpen()){
					wrapperRange.removeEdition(currentEdition);
				}
			}
		}
	}
	
	
	/**
	 * Detect if user has Primary 
	 * @param contextUser
	 * @return
	 */
	
	protected boolean isUserPrimary(IWrapperRange wrapperRange) {
		boolean isPrimary= true;
		for (Edition edition : wrapperRange.getEditions()) {
			if(edition.isDelegate()){
				isPrimary=false;
				break;
			}
		}
		return isPrimary;
	}
	
	@Override
	public boolean isModifyRange() {
		return true;
	}
	
	public abstract void execute(ContextUser contextUser, IWrapperRange rangeWrapper) throws RangeIoException;
}
