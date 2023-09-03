package com.yougrow.recipeapp.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.yougrow.recipeapp.models.UnitOfMeasure;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Entity
@Data
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    @JsonIgnoreProperties("ingredients")
    private Recipe recipe;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @NotBlank
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    private UnitOfMeasure unitOfMeasure;

    @NotNull
    @DecimalMin("0.0")
    private Double amountInRecipe;

    @JsonSetter("name")
    public void setName(String name) {
        this.name = name.trim();
    }

    public boolean isEnoughInStock() {
        if (product.getAmountInStock() <= 0)
            return false;
        else if (unitOfMeasure == product.getUnitOfMeasure())
            return product.getAmountInStock() >= amountInRecipe;
        else if (unitOfMeasure == UnitOfMeasure.GRAM && product.getUnitOfMeasure() == UnitOfMeasure.KILOGRAM)
            return 1000 * product.getAmountInStock() >= amountInRecipe;
        else if (unitOfMeasure == UnitOfMeasure.KILOGRAM && product.getUnitOfMeasure() == UnitOfMeasure.GRAM)
            return product.getAmountInStock() >= 1000 * amountInRecipe;
        else if (unitOfMeasure == UnitOfMeasure.MILLILITER && product.getUnitOfMeasure() == UnitOfMeasure.LITER)
            return 1000 * product.getAmountInStock() >= amountInRecipe;
        else if (unitOfMeasure == UnitOfMeasure.LITER && product.getUnitOfMeasure() == UnitOfMeasure.MILLILITER)
            return product.getAmountInStock() >= 1000 * amountInRecipe;
        else
            throw new RuntimeException("Product's unit of measure and Ingredient's unit of measure cannot be compared");
    }
}
