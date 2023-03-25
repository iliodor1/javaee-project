package org.example.dao;

import org.example.entities.Product;

import java.util.Collection;

public interface ProductDao {
    Product save(Product product);
    Product update(Long id, Product product);
    boolean delete(Long id);
    Product findById(Long id);
    Collection<Product> findAll();
}
