package org.example.services.product;

import org.example.dto.product.NewProduct;
import org.example.dto.product.ResponseProduct;

import java.util.Collection;

public interface ProductService {
    ResponseProduct add(NewProduct product);
    ResponseProduct update(Long id, NewProduct product);
    boolean delete(Long id);
    ResponseProduct get(Long id);
    Collection<Long> getAll();
}
