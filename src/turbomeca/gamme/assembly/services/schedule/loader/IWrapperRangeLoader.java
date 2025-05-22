package turbomeca.gamme.assembly.services.schedule.loader;

import java.io.File;
import java.util.List;

import turbomeca.gamme.assembly.services.schedule.edition.wrapper.IWrapperRange;
import turbomeca.gamme.ecran.services.common.bean.ContextUser;
import turbomeca.gamme.ecran.services.common.exceptions.RangeIoException;

public interface IWrapperRangeLoader {
	
	/**
	 * Load Schedule from contextUser
	 * @param contextUser
	 * @return
	 * @throws RangeIoException
	 */
	List<IWrapperRange> load(ContextUser contextUser) throws RangeIoException;
	
	
	/**
	 * Load all schedule from initialize repository (OFFICIAL - OFFICIAL TEST)
	 * @return
	 * @throws RangeIoException
	 */
	List<IWrapperRange> load() throws RangeIoException;
	
	/**
	 * Unload schedule file
	 * @throws RangeIoException
	 */
	void unload() throws RangeIoException;

	/**
	 * Save schedule 
	 * @throws RangeIoException
	 */
	void save() throws RangeIoException;
	
	/**
	 * Remove And Save 
	 * @throws RangeIoException
	 */
	void removeAndSave() throws RangeIoException;
	
	/**
	 * Save from file parameters
	 * @param file
	 * @throws RangeIoException
	 */
	void save(File file) throws RangeIoException;
}
