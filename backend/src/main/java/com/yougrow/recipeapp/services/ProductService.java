package com.yougrow.recipeapp.services;

import com.yougrow.recipeapp.entities.Product;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    /**
     * Returns all instances of Product types.
     *
     * @return all Product entities.
     */
    List<Product> findAll();

    /**
     * Retrieves a Product entity by its id.
     *
     * @param id must not be null.
     * @return the Product entity with the given id or Optional#empty() if none found.
     */
    Optional<Product> findById(Integer id);

    /**
     * Creates the given Product entity.
     * The product's id must be null. The product's name must be unique. It is not allowed
     * to create several products with the same names.
     *
     * @param product must not be null, its id must be null, its name must be unique.
     * @return the saved Product; or Optional#empty() if id is not null or if a Product
     * entity with the same name already exists.
     */
    Optional<Product> create(Product product);

    /**
     * Updates (changes) the given Product entity.
     * It is not allowed to change the name of a Product entity since it could be linked
     * to an Ingredient entity. We could allow changing the name of Product entities that
     * are not linked to any Ingredient entities but this would result in inconsistent
     * behavior and very likely confuse some users. Therefore, it's always disabled.
     *
     * @param product must not be null, its id must not be null, its name must not change.
     * @return the saved Product; or Optional#empty() if id is null or if the Product
     * entity name was changed.
     */
    Optional<Product> update(Product product);

    /**
     * Deletes a Product entity with the given id. Unknown ids are silently ignored.
     * If a Product entity is linked to an Ingredient entity, the Product is not deleted.
     * Instead, the Product's amountInStock attribute will be set to zero.
     *
     * @param id must not be null.
     */
    void deleteById(Integer id);
}
