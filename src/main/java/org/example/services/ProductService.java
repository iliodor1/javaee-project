package org.example.services;

import org.example.dto.NewProduct;
import org.example.dto.ResponseProduct;
import org.example.entities.Product;

import java.util.Collection;

public interface ProductService {
    Product add(NewProduct product);
    Product update(Long id, Product product);
    boolean delete(Long id);
    ResponseProduct get(Long id);
    Collection<Product> getAll();
}
