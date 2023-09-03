package com.yougrow.recipeapp.repositories;

import com.yougrow.recipeapp.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    /**
     * Retrieves a Product entity by its 'name' attribute.
     *
     * @param name must not be null.
     * @return The product entity with the specified 'name' or Optional#empty() if none found.
     */
    Optional<Product> findOptionalByName(String name);

    /**
     * Returns whether a Product entity with the given name exists.
     *
     * @param name must not be null.
     * @return true if a Product with the given name exists, false otherwise.
     */
    boolean existsByName(String name);
}
