package turbomeca.gamme.assembly.services.schedule.loader;

import java.io.File;
import java.io.Serializable;

import turbomeca.gamme.assembly.services.schedule.model.Ranges;
import turbomeca.gamme.ecran.services.schedule.model.dao.CastorObjectLoader;

/**
 * 
 * @author Sopra Group
 */
public class RangesLoader extends CastorObjectLoader<Ranges> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * @param summaryFile
	 */
	public RangesLoader(File file) {
		super(file);
	}

	@Override
	protected Class<?> getCastorClass() {
		return Ranges.class;
	}
}
