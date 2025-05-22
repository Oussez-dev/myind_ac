package turbomeca.gamme.assembly.services.bodb.bobuilder;

import java.util.ArrayList;
import java.util.List;

import turbomeca.gamme.assembly.services.bodb.utils.BoDbUtil;
import turbomeca.gamme.assembly.services.model.data.Ingredient;
import turbomeca.gamme.assembly.services.model.data.Ingredients;
import turbomeca.gamme.ecran.services.bodb.bo.BoIngredient;
import turbomeca.gamme.ecran.services.bodb.bo.BoSubPhase;

public class BoDbIngredientBuilder {

	private BoDbIngredientBuilder() {
		throw new IllegalStateException("Utility class"); //$NON-NLS-1$
	}

	public static List<BoIngredient> buildIngredients(Ingredients ingredients, BoSubPhase boSubphase) {
		List<BoIngredient> result = new ArrayList<BoIngredient>();
		if (ingredients != null) {
			for (Ingredient ingredient : ingredients.getIngredient()) {
				result.add(buildBoIngredient(ingredient, boSubphase));
			}
		}
		return result;
	}

	public static BoIngredient buildBoIngredient(Ingredient ingredient, BoSubPhase boSubphase) {
		BoIngredient boIngredient = null;
		if(ingredient != null) {
			boIngredient = new BoIngredient();
			boIngredient.setUfiId(ingredient.getId());
			boIngredient.setPn(ingredient.getPn());
			if (ingredient.getDesignation() != null) {
				boIngredient.setDesignation(BoDbUtil.buildTextFromParaList(ingredient.getDesignation().getPara()));
			}
			if (ingredient.getSubstitute() != null) {
				boIngredient.setAlternative(Boolean.valueOf(ingredient.getSubstitute().value()));
			}
			
			boIngredient.setSubphase(boSubphase);
		}
		

		return boIngredient;
	}




}
