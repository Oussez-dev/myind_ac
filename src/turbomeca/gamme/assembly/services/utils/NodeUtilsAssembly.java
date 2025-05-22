package turbomeca.gamme.assembly.services.utils;

import org.w3c.dom.Document;

import turbomeca.gamme.ecran.services.common.utils.xml.BinderUtils;
import turbomeca.gamme.ecran.services.model.constants.ModelGlobalConstants;

public class NodeUtilsAssembly {

	public static long getScheduleInstances(Document document) {
		Integer parsedValue = BinderUtils.getIntegerValue(BinderUtils.getFirstChildNode(BinderUtils
				.getFirstChildNode(document.getDocumentElement(),
						ModelGlobalConstants.TAG_IDENTIFICATION),
						ModelGlobalConstants.TAG_INSTANCES));
		if (parsedValue == null) {
			return 1;
		}
		long result = parsedValue.longValue();
		return result;
	}
	
}
