package turbomeca.gamme.assembly.services.ressources;

import java.util.Properties;

import org.springframework.stereotype.Service;

import turbomeca.gamme.ecran.services.common.config.ARessourcesLoaderService;


@Service
// FIXME : Remove this class and inject through spring these constants value to
// ecran service from a internal-ressources.properties
public class AssemblyRessourcesLoaderService extends ARessourcesLoaderService {
	protected final static String PROPERTY_FILE_PATTERN = "resource_assembly_%s.properties";
	protected static final String PROPERTY_FILE_PATTERN_DEFAULT = "resource_assembly_en.properties";

	@Override
	protected String getFilenamePattern() {
		return PROPERTY_FILE_PATTERN;
	}
	
	@Override
	protected String getDefaultFilenamePattern(){
		return PROPERTY_FILE_PATTERN_DEFAULT;
	}
	
	public AssemblyRessourcesLoaderService(){
		property = new Properties();
	}
}
