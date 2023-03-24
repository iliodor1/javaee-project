package org.example.services;

import org.example.dto.NewProduct;
import org.example.entities.Product;

import java.util.Collection;

public interface ProductService {
    Product save(NewProduct product);
    Product update(Long id, Product product);
    boolean delete(Long id);
    Product findById(Long id);
    Collection<Product> findAll();
}
