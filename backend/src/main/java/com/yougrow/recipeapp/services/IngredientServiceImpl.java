package com.yougrow.recipeapp.services;

import com.yougrow.recipeapp.entities.Ingredient;
import com.yougrow.recipeapp.entities.Product;
import com.yougrow.recipeapp.entities.Recipe;
import com.yougrow.recipeapp.repositories.IngredientRepository;
import com.yougrow.recipeapp.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class IngredientServiceImpl implements IngredientService {
    private final IngredientRepository ingredientRepository;
    private final ProductRepository productRepository;

    public IngredientServiceImpl(IngredientRepository ingredientRepository,
                                 ProductRepository productRepository) {
        this.ingredientRepository = ingredientRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void createIngredientsForRecipe(Recipe recipe, List<Ingredient> ingredients) {
        for (Ingredient ingredient : ingredients) {
            Product product = productRepository
                    .findOptionalByName(ingredient.getName())
                    .orElseGet(() -> productRepository.save(new Product(ingredient.getName(), ingredient.getUnitOfMeasure())));

            ingredient.setProduct(product);
            ingredient.setRecipe(recipe);
            ingredientRepository.save(ingredient);

        }
    }

    @Override
    public void updateIngredientsForRecipe(Recipe recipe, List<Ingredient> ingredients) {
        for (Ingredient ingredient : ingredients) {
            Optional<Ingredient> oldIngredient = ingredientRepository.findById(ingredient.getId());

            // FIXME what if ingredient name was changed? Product needs to be re-linked
            // FIXME and what if ingredient's unitOfMeasure was changed?
            ingredient.setProduct(oldIngredient.get().getProduct());
            ingredient.setRecipe(recipe);
            ingredientRepository.save(ingredient);
        }
    }
}
