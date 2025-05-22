package turbomeca.gamme.assembly.services.schedule.creation.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import turbomeca.gamme.ecran.services.common.utils.misc.PublishedScheduleUtils;
import turbomeca.gamme.ecran.services.publication.bo.BoEffectivity;
import turbomeca.gamme.ecran.services.publication.bo.BoEffectivityParameter;
import turbomeca.gamme.ecran.services.publication.bo.BoPublishedSchedule;
import turbomeca.gamme.ecran.services.publication.bo.BoVariantEffectivity;

/**
 * Provide utilities to handle with Pusblished schedule business object
 *
 */
public class AssemblyPublishedScheduleUtils extends PublishedScheduleUtils {
	
	
	/**
	 * Returns a list for parameters (String) of the variant
	 * @param publishedSchedule
	 * @param key
	 * @return
	 */
	public static List<String> getParametersValuesFromVariant(BoVariantEffectivity bo, String key) {
		
		List<String> list = new ArrayList<String>();
		
		for (BoEffectivityParameter parameter : bo.getEffectivityParameter()) {
			if (parameter.getKeyEffectivityParameter().equals(key)) {
				list.add(parameter.getValueEffectivityParameter());
			}
		}
		return list;
	}
	

	
	/**
	 * Extract a parameter list from the schedule business object
	 * @param publishedSchedules
	 * @param key
	 * @return
	 */
	public static List<String> extractParametersFromSchedules(Collection<BoPublishedSchedule> publishedSchedules, String key) {
		List<String> pnList = new ArrayList<String>();
		for (BoPublishedSchedule publishedSchedule : publishedSchedules) {
			for (BoEffectivity effectivity : publishedSchedule.getEffectivities()) {
				for (BoVariantEffectivity variant : effectivity.getVariantEffectivities()) {
					for (BoEffectivityParameter parameter : variant.getEffectivityParameter()) {
						if (parameter.getKeyEffectivityParameter().equals(key)) {
							pnList.add(parameter.getValueEffectivityParameter());
						}
					}
				}
			}
		}
		return pnList;
	}
	
}
