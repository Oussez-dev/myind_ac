package turbomeca.gamme.assembly.services.schedule.edition.wrapper;

import turbomeca.gamme.assembly.services.schedule.model.DoubleValidation;
import turbomeca.gamme.assembly.services.schedule.model.Edition;
import turbomeca.gamme.assembly.services.schedule.model.Instance;
import turbomeca.gamme.assembly.services.schedule.model.Modification;
import turbomeca.gamme.assembly.services.schedule.model.RangeIdentification;

public interface IWrapperRange {
	public Modification getModification();
	public void setModification(Modification modification);
	public Instance getInstance();
	public RangeIdentification getRangeIdentification();
	public Edition[] getEditions();
	public DoubleValidation[] getDoubleValidations();
	public void removeEdition(Edition edition);
	public void addEdition(Edition newEdition);
	public void addDoubleValidation(DoubleValidation validation);
	public void removeDoubleValidation(DoubleValidation validation);
	public void removeRange();
}
