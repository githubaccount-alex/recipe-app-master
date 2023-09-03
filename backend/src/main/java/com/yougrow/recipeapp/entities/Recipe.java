package com.yougrow.recipeapp.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "recipe")
    @JsonIgnoreProperties(value = {"recipe", "product"}, allowSetters = true)
    private List<Ingredient> ingredients;

    @NotBlank
    private String name;

    @NotBlank
    @Column(length = 8192)
    private String instructions;

    @Column(length = 1024)
    private String imageUrl;

    public boolean isCookable() {
        return ingredients.stream().allMatch(Ingredient::isEnoughInStock);
    }
}
