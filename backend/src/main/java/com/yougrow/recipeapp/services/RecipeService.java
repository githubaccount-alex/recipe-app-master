package com.yougrow.recipeapp.services;

import com.yougrow.recipeapp.entities.Product;
import com.yougrow.recipeapp.entities.Recipe;
import java.util.List;
import java.util.Optional;

public interface RecipeService {
    /**
     * Returns all instances of Recipe types.
     *
     * @return all Recipe entities.
     */
    List<Recipe> findAll();

    /**
     * Returns all Recipe entities having a relationship with a given list of Product items.
     *
     * @param products must not be null.
     * @return all Recipe entities containing the list of products.
     */
    List<Recipe> findByProducts(List<Product> products);

    /**
     * Returns all cookable Recipe entities.
     *
     * @return all Product entities that are cookable.
     */
    List<Recipe> findCookable();

    /**
     * Retrieves a Recipe entity by its id.
     *
     * @param id must not be null.
     * @return the Recipe entity with the given id or Optional#empty() if none found.
     */
    Optional<Recipe> findById(Integer id);

    /**
     * Creates the given Recipe entity.
     *
     * @param recipe must not be null, its id must be null, and it must contain at least
     * one Ingredient.
     * @return the saved Recipe; or Optional#empty() if id is not null or if the Ingredient
     * list is empty.
     */
    Optional<Recipe> create(Recipe recipe);

    /**
     * Updates (changes) the given Recipe entity.
     *
     * @param recipe must not be null, its id must not be null.
     * @return the saved Recipe; or Optional#empty() if id is null, id doesn't refer to a
     * valid Recipe, or if the supplied Ingredient list is empty.
     */
    Optional<Recipe> update(Recipe recipe);

    /**
     * Deletes a Recipe entity with the given id. Unknown ids are silently ignored.
     * It recursively deletes all Ingredient entities the Recipe contains.
     *
     * @param id must not be null.
     */
    void deleteById(Integer id);

    /**
     * Cook a Recipe with the given id. This method checks if a Recipe is cookable by
     * comparing for each Ingredient the required amount for the Recipe with the amount
     * available, and if true, it will subtract the Ingredient amount form each Product.
     *
     * @param id must not be null.
     * @return Product entities required for the given Recipe with their updated amounts;
     * or Optional#empty() if Recipe id is not valid or if Recipe not cookable.
     * @throws RuntimeException if an Ingredient's UnitOfMeasure cannot be compared to a
     * Product's UnitOfMeasure.
     */
    Optional<List<Product>> cookRecipeById(Integer id);
}
