package turbomeca.gamme.assembly.services.dvi;

import turbomeca.gamme.assembly.services.schedule.creation.bean.ContextScheduleCreationAssembly;
import turbomeca.gamme.ecran.services.dvi.RepositoryType;

/***
 * This interface is in charge to provide an helper service to compute an
 * instantiated schedule creation  parameter from an intermediary file 
 * <p>
 * Correct intermediary file is identified according to the provided parameters
 * <p>
 * Provided creation context aims to be similar to the one used from
 * instantiation server page in order to re-use existing instantiation services
 * 
 * @author ahermo
 *
 */

public interface ISearchIntermediaryCreationContext {

	/***
	 * 
	 * Build a schedule creation context to allow instantiation according to provided parameters 
	 * 
	 * 
	 * @param articlePn
	 * @param va
	 * @param family (optional parameter) When not null, used only to raise an error
	 * @param variant (optional parameter) When not null, used only to raise an error
	 * @param material (optional parameter) When not null, used only to raise an error
	 * @param isolated may be null (to create a schedule for isolated and not isolated applicabilities)
	 * @param referential
	 * @param lang
	 * @param site
	 * @return
	 * @throws SearchCreationContextException
	 */
	public ContextScheduleCreationAssembly getIntermediaryCreationContext(String articlePn, String va,
			@Deprecated String family,
			@Deprecated String variant,
			@Deprecated String material,
			Boolean isolated, RepositoryType referential,
			String lang,
			String site) throws SearchCreationContextException;

	public ContextScheduleCreationAssembly getIntermediaryCreationContextFromGeode(String geodeRef, String geodeVersion,
			RepositoryType referential) throws SearchCreationContextException;
}
