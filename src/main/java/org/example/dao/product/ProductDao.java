package org.example.dao.product;

import org.example.entities.Product;

import java.util.Collection;
import java.util.Optional;

public interface ProductDao {
    Product save(Product product);
    Product update(Long id, Product product);
    boolean delete(Long id);
    Optional<Product> findById(Long id);
    Collection<Product> findAll();
}
