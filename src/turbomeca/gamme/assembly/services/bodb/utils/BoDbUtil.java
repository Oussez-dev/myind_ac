package turbomeca.gamme.assembly.services.bodb.utils;

import java.util.ArrayList;
import java.util.List;

import turbomeca.gamme.assembly.services.model.data.Derogation;
import turbomeca.gamme.assembly.services.model.data.InputChoice;
import turbomeca.gamme.assembly.services.model.data.InputValue;
import turbomeca.gamme.assembly.services.model.data.Para;
import turbomeca.gamme.assembly.services.model.data.Quantity;
import turbomeca.gamme.assembly.services.model.data.Ref;
import turbomeca.gamme.assembly.services.model.data.TaskAction;

public class BoDbUtil {

	private BoDbUtil() {
		throw new IllegalStateException("Utility class");
	}

	public static String buildTextFromParaList(Para[] paraTable) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < paraTable.length; i++) {
			builder.append(paraTable[i].getContent());
		}

		return builder.toString();
	}

	public static List<String> buildDerogation(Derogation[] derogations) {
		List<String> result = new ArrayList<String>();
		for (Derogation d : derogations) {
			result.add(d.getValue());
		}
		return result;
	}

	public static String getTaskActionValue(TaskAction taskAction) {
		String value = null;
		if (taskAction.getInputAction().getInputValue() != null) {
			InputValue inputValue = taskAction.getInputAction().getInputValue();
			if(inputValue.getValueStatus()!=null) {
				if (taskAction.getInputAction().getInputActionChoice().getInputChoice() != null) {
					InputChoice inputChoice = taskAction.getInputAction().getInputActionChoice().getInputChoice();
					Integer choiceIndex = Integer.parseInt(inputValue.getValue()) - 1;
					if (inputChoice.getVal().length > 0) {
						value = inputChoice.getVal(choiceIndex).toString();
					} else if (inputChoice.getStringValue().length > 0) {
						value = inputChoice.getStringValue(choiceIndex).getContent();
					}
				} else {
					value = inputValue.getValue();
				}
			}
		}

		return value; 
	}


	public static String buildQuantity(Quantity[] quantity) {
		return quantity.length > 0 ? quantity[0].getContent() : null;
	}

	public static String buildReference(Ref[] ref) {
		StringBuilder builder = new StringBuilder();
		for (Ref r : ref) {
			builder.append(r.getContent());
		}
		return builder.toString();
	}

}
