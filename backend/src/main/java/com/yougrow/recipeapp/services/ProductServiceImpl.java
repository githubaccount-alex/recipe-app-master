package com.yougrow.recipeapp.services;

import com.yougrow.recipeapp.entities.Product;
import com.yougrow.recipeapp.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> findById(Integer id) {
        return productRepository.findById(id);
    }

    @Override
    public Optional<Product> create(Product product) {
        // Product names must be unique
        if (productRepository.existsByName(product.getName()) || product.getId() != null)
            return Optional.empty();

        return Optional.of(productRepository.save(product));
    }

    @Override
    public Optional<Product> update(Product product) {
        if (product.getId() == null)
            return Optional.empty();

        Optional<Product> oldProduct = findById(product.getId());

        // Don't allow changing the name of an existing Product, nor the unit of measure
        if (oldProduct.isEmpty()
                || !oldProduct.get().getName().equals(product.getName())
                || !oldProduct.get().getUnitOfMeasure().equals(product.getUnitOfMeasure()))
            return Optional.empty();

        return Optional.of(productRepository.save(product));
    }

    @Override
    public void deleteById(Integer id) {
        Optional<Product> product = productRepository.findById(id);

        if (product.isEmpty())
            return;

        // If a Product links to an Ingredient, don't delete it
        if (!product.get().getIngredients().isEmpty()) {
            product.get().setAmountInStock(0.0);
            productRepository.save(product.get());
            return;
        }

        productRepository.deleteById(id);
    }
}
