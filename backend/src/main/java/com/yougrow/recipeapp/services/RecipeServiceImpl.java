package com.yougrow.recipeapp.services;

import com.yougrow.recipeapp.entities.Product;
import com.yougrow.recipeapp.entities.Recipe;
import com.yougrow.recipeapp.entities.Ingredient;
import com.yougrow.recipeapp.models.UnitOfMeasure;
import com.yougrow.recipeapp.repositories.ProductRepository;
import com.yougrow.recipeapp.repositories.IngredientRepository;
import com.yougrow.recipeapp.repositories.RecipeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;
    private final ProductRepository productRepository;
    private final IngredientRepository ingredientRepository;
    private final IngredientService ingredientService;

    public RecipeServiceImpl(RecipeRepository recipeRepository,
                             ProductRepository productRepository,
                             IngredientRepository ingredientRepository,
                             IngredientService ingredientService) {
        this.recipeRepository = recipeRepository;
        this.productRepository = productRepository;
        this.ingredientRepository = ingredientRepository;
        this.ingredientService = ingredientService;
    }

    @Override
    public List<Recipe> findAll() {
        return recipeRepository.findAll();
    }

    @Override
    public List<Recipe> findByProducts(List<Product> products) {
        // TODO Should we use a @Query() in RecipeRepository?
        return findAll()
                .stream()
                .filter(recipe -> new HashSet<>(
                        recipe.getIngredients()
                                .stream()
                                .map(Ingredient::getProduct)
                                .toList())
                        .containsAll(products))
                .toList();
    }

    @Override
    public List<Recipe> findCookable() {
        // TODO Should we use a @Query() in RecipeRepository?
        return findAll()
                .stream()
                .filter(Recipe::isCookable)
                .toList();
    }

    @Override
    public Optional<Recipe> findById(Integer id) {
        return recipeRepository.findById(id);
    }

    @Override
    public Optional<Recipe> create(Recipe recipe) {
        // A recipe is invalid without ingredients
        if (recipe.getId() != null || recipe.getIngredients().isEmpty())
            return Optional.empty();

        recipe = recipeRepository.save(recipe);
        ingredientService.createIngredientsForRecipe(recipe, recipe.getIngredients());

        return Optional.of(recipe);
    }

    @Override
    public Optional<Recipe> update(Recipe recipe) {
        if (recipe.getId() == null || recipe.getIngredients().isEmpty())
            return Optional.empty();

        Optional<Recipe> oldRecipe = recipeRepository.findById(recipe.getId());

        if (oldRecipe.isEmpty())
            return Optional.empty();

        // Filter out ingredients with unknown IDs (TODO should we notify user somehow?)
        recipe.getIngredients()
                .removeIf(ingredient -> ingredient.getId() != null
                        && !oldRecipe.get()
                        .getIngredients()
                        .stream()
                        .map(Ingredient::getId)
                        .toList()
                        .contains(ingredient.getId()));

        if (recipe.getIngredients().isEmpty())
            return Optional.empty();

        // Add new ingredient not contained in old recipe version
        ingredientService.createIngredientsForRecipe(recipe, recipe.getIngredients()
                .stream()
                .filter(ingredient -> ingredient.getId() == null)
                .toList());

        // Update old ingredient contained in old and new recipe version
        ingredientService.updateIngredientsForRecipe(recipe, recipe.getIngredients()
                .stream()
                .filter(ingredient -> ingredient.getId() != null)
                .toList());

        // Delete old ingredients not contained in new recipe version
        ingredientRepository.deleteAll(oldRecipe
                .get()
                .getIngredients()
                .stream()
                .filter(ingredient -> !recipe
                        .getIngredients()
                        .stream()
                        .map(Ingredient::getId)
                        .toList()
                        .contains(ingredient.getId()))
                .toList());

        return Optional.of(recipeRepository.save(recipe));
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        if (recipeRepository.findById(id).isEmpty())
            return;

        ingredientRepository.deleteAllByRecipeId(id);
        recipeRepository.deleteById(id);
    }

    @Override
    public Optional<List<Product>> cookRecipeById(Integer id) {
        Optional<Recipe> recipe = recipeRepository.findById(id);

        // TODO give separate response message when recipe is not cookable
        if (recipe.isEmpty() || !recipe.get().isCookable())
            return Optional.empty();

        for (Ingredient ingredient : recipe.get().getIngredients()) {
            Product product = ingredient.getProduct();

            if (product.getUnitOfMeasure() == ingredient.getUnitOfMeasure())
                product.setAmountInStock(product.getAmountInStock() - ingredient.getAmountInRecipe());
            else if (product.getUnitOfMeasure() == UnitOfMeasure.GRAM && ingredient.getUnitOfMeasure() == UnitOfMeasure.KILOGRAM)
                product.setAmountInStock(product.getAmountInStock() - 1000 * ingredient.getAmountInRecipe());
            else if (product.getUnitOfMeasure() == UnitOfMeasure.KILOGRAM && ingredient.getUnitOfMeasure() == UnitOfMeasure.GRAM)
                product.setAmountInStock(product.getAmountInStock() - ingredient.getAmountInRecipe() / 1000);
            else if (product.getUnitOfMeasure() == UnitOfMeasure.MILLILITER && ingredient.getUnitOfMeasure() == UnitOfMeasure.LITER)
                product.setAmountInStock(product.getAmountInStock() - 1000 * ingredient.getAmountInRecipe());
            else if (product.getUnitOfMeasure() == UnitOfMeasure.LITER && ingredient.getUnitOfMeasure() == UnitOfMeasure.MILLILITER)
                product.setAmountInStock(product.getAmountInStock() - ingredient.getAmountInRecipe() / 1000);
            else
                throw new RuntimeException("Product's unit of measure and Ingredient's unit of measure cannot be compared");

            productRepository.save(product);
        }

        return recipe.map(r -> r.getIngredients()
                .stream()
                .map(Ingredient::getProduct)
                .toList());
    }
}
