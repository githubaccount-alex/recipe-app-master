package com.yougrow.recipeapp.controllers;

import com.yougrow.recipeapp.entities.Product;
import com.yougrow.recipeapp.entities.Recipe;
import com.yougrow.recipeapp.services.RecipeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/recipes")
@CrossOrigin("http://localhost:4200")
public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    public ResponseEntity<List<Recipe>> getAllRecipes(@RequestParam(required = false) Boolean cookable) {
        return ResponseEntity.ok(
                cookable != null && cookable
                        ? recipeService.findCookable()
                        : recipeService.findAll()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable Integer id) {
        return recipeService
                .findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Recipe> createRecipe(@RequestBody @Valid Recipe recipe) {
        return recipeService
                .create(recipe)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @PostMapping("/byProducts")
    public ResponseEntity<List<Recipe>> getRecipesByProducts(@RequestBody @Valid List<Product> products) {
        return ResponseEntity.ok(recipeService.findByProducts(products));
    }

    @PutMapping
    public ResponseEntity<Recipe> updateRecipe(@RequestBody @Valid Recipe recipe) {
        return recipeService
                .update(recipe)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @PutMapping("/cook/{id}")
    public ResponseEntity<List<Product>> cookRecipe(@PathVariable Integer id) {
        return recipeService
                .cookRecipeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Integer id) {
        recipeService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
