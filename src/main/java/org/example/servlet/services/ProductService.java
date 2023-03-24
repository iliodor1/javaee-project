package org.example.servlet.services;

import org.example.servlet.entities.Product;

import java.util.Collection;

public interface ProductService {
    Product save(Product product);
    Product update(Long id, Product product);
    boolean delete(Long id);
    Product findById(Long id);
    Collection<Product> findAll();
}
