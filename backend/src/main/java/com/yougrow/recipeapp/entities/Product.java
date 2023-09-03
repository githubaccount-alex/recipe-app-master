package com.yougrow.recipeapp.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.yougrow.recipeapp.models.UnitOfMeasure;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "product")
    @JsonIgnoreProperties({"product", "recipe"})
    private List<Ingredient> ingredients = new ArrayList<>();

    @NotBlank
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    private UnitOfMeasure unitOfMeasure;

    @NotNull
    @DecimalMin("0.0")
    private Double amountInStock = 0.0;

    @Column(length = 1024)
    private String imageUrl;

    @NotNull
    private LocalDate expirationDate = LocalDate.now();

    public Product(String name, UnitOfMeasure unitOfMeasure) {
        this.name = name;
        this.unitOfMeasure = unitOfMeasure;
    }

    @JsonSetter("name")
    public void setName(String name) {
        this.name = name.trim();
    }
}
