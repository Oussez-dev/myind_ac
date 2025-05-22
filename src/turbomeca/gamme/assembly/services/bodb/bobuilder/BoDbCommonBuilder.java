package turbomeca.gamme.assembly.services.bodb.bobuilder;

import turbomeca.gamme.assembly.services.model.data.Historical;

public class BoDbCommonBuilder {

	private BoDbCommonBuilder() {
		throw new IllegalStateException("Utility class"); //$NON-NLS-1$
	}
	
	public static String getHistoricalLastVersion(Historical historical) {
		String version = null;
		if (historical != null) {
			Integer issueCount = historical.getIssue().length;
			if (issueCount > 0) {
				version = historical.getIssue()[issueCount - 1].getVersion();
			}
		}
		return version;
	}
	
}
