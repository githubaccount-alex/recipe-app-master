package com.yougrow.recipeapp.services;

import com.yougrow.recipeapp.entities.Ingredient;
import com.yougrow.recipeapp.entities.Recipe;
import java.util.List;

public interface IngredientService {
    /**
     * Creates a list of new ingredients and makes a connection between each Ingredient
     * in a Recipe and a Product. All Ingredient ids must be null. The Product is looked
     * up by name. In other words, a Product can be used for an Ingredient when both
     * names are identical.
     *
     * @param recipe must not be null.
     * @param ingredients list of ingredients that need to be linked to a product.
     */
    void createIngredientsForRecipe(Recipe recipe, List<Ingredient> ingredients);

    /**
     * Updates a list of existing ingredients. All Ingredient ids must be valid.
     *
     * @param recipe must not be null.
     * @param ingredients list of ingredients with existing ids.
     */
    void updateIngredientsForRecipe(Recipe recipe, List<Ingredient> ingredients);
}
