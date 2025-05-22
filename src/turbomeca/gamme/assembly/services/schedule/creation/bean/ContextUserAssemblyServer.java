package turbomeca.gamme.assembly.services.schedule.creation.bean;

import turbomeca.gamme.assembly.services.constants.Constants;
import turbomeca.gamme.assembly.services.schedule.config.AssemblyRepositoryConfig;
import turbomeca.gamme.ecran.services.notification.bean.ContextElectronicNotification;
import turbomeca.gamme.ecran.services.schedule.bean.ContextUserServer;



public class ContextUserAssemblyServer extends ContextUserServer {
	
	private static final long serialVersionUID = 1L;
	
	private AssemblyRepositoryConfig repository;

	private ContextElectronicNotification contextElectronicNotification;
	
	private String contextSchedule;
	
	private int nbDoubleValidation;
	
	public AssemblyRepositoryConfig getRepository() {
		return repository;
	}
	
	public void setRepository(AssemblyRepositoryConfig repository) {
		this.repository = repository;
	}


	public ContextElectronicNotification getContextElectronicNotification() {
		return contextElectronicNotification;
	}


	public void setContextElectronicNotification(
			ContextElectronicNotification contextElectronicNotification) {
		this.contextElectronicNotification = contextElectronicNotification;
	}


	public String getContextSchedule() {
		return contextSchedule;
	}


	public void setContextSchedule(String contextSchedule) {
		this.contextSchedule = contextSchedule;
	}


	public int getNbDoubleValidation() {
		return nbDoubleValidation;
	}


	public void setNbDoubleValidation(int nbDoubleValidation) {
		this.nbDoubleValidation = nbDoubleValidation;
	}
	
	public boolean hasQualityRight(){
		boolean qualityRight = false;
		for(String currentRole : getRoles().split(";")){
			if(currentRole.equals(String.valueOf(Constants.ROLE_NOTIFICATION_QUALITY))){
				qualityRight = true;
				break;
			}
		}
		return qualityRight;
	}
	
	public boolean hasMethodRight(){
		boolean methodRight = false;
		for(String currentRole : getRoles().split(";")){
			if(currentRole.equals(String.valueOf(Constants.ROLE_NOTIFICATION_METHOD))){
				methodRight  = true;
				break;
			}
		}
		return methodRight ;
	}
}
