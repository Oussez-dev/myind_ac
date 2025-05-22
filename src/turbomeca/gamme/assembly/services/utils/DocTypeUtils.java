package turbomeca.gamme.assembly.services.utils;

import turbomeca.gamme.assembly.services.schedule.creation.utils.AssemblyGlobalServices;

public class DocTypeUtils {

	public static String getDoctype(String rangeType, String rangeContext) {
		String result = null;

		if (rangeType.compareTo(AssemblyGlobalServices.SCHEDULE_ASSEMBLY_TYPE) == 0) {
			if (rangeContext.compareTo(AssemblyGlobalServices.SCHEDULE_ASSEMBLY_NEW) == 0) {
				result = AssemblyGlobalServices.SCHEDULE_DOCTYPE_ASSEMBLY_NEW;
			} else if (rangeContext.compareTo(AssemblyGlobalServices.SCHEDULE_ASSEMBLY_REPAIRED) == 0) {
				result = AssemblyGlobalServices.SCHEDULE_DOCTYPE_ASSEMBLY_REPAIRED;
			}
		} else if (rangeType.compareTo(AssemblyGlobalServices.SCHEDULE_DISASSEMBLY_TYPE) == 0) {
			result = AssemblyGlobalServices.SCHEDULE_DOCTYPE_DISASSEMBLY;
		}

		return result;
	}

}
