package com.yougrow.recipeapp.repositories;

import com.yougrow.recipeapp.entities.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {
    /**
     * Deletes all Ingredient entities inside a Recipe with given id.
     *
     * @param id must not be null.
     */
    void deleteAllByRecipeId(Integer id);
}
