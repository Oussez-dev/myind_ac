package turbomeca.gamme.assembly.services.schedule.edition.wrapper;

import turbomeca.gamme.assembly.services.schedule.model.DoubleValidation;
import turbomeca.gamme.assembly.services.schedule.model.Edition;
import turbomeca.gamme.assembly.services.schedule.model.Instance;
import turbomeca.gamme.assembly.services.schedule.model.Modification;
import turbomeca.gamme.assembly.services.schedule.model.Range;
import turbomeca.gamme.assembly.services.schedule.model.RangeIdentification;

public class WrapperRangeGeneric implements IWrapperRange {

	/** */
	private Range range;

	/**
	 * @param range
	 *            the range to set
	 */
	public void setRange(Range range) {
		this.range = range;
	}

	/**
	 * @return the range
	 */
	public Range getRange() {
		return range;
	}

	/**
	 * Constructor
	 * 
	 * @param range
	 *            generic range
	 */
	public WrapperRangeGeneric(Range range) {
		setRange(range);
	}

	@Override
	public Modification getModification() {
		return getRange().getModification();
	}

	@Override
	public void setModification(Modification modification) {
		getRange().setModification(modification);
	}

	@Override
	public Instance getInstance() {
		return getRange().getInstance();
	}

	@Override
	public RangeIdentification getRangeIdentification() {
		return getRange().getRangeIdentification();
	}

	@Override
	public Edition[] getEditions() {
		return getRange().getEdition();
	}

	@Override
	public DoubleValidation[] getDoubleValidations() {
		return getRange().getDoubleValidation();
	}
	
	@Override
	public void addDoubleValidation(DoubleValidation validation) {
		getRange().addDoubleValidation(validation);
	}
	
	@Override
	public void removeEdition(Edition edition) {
		getRange().removeEdition(edition);
	}

	@Override
	public void addEdition(Edition edition) {
		getRange().addEdition(edition);
	}

	@Override
	public void removeRange() {
		setRange(null);
	}

	@Override
	public void removeDoubleValidation(DoubleValidation validation) {
		getRange().removeDoubleValidation(validation);
	}

}
