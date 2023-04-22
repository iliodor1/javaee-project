package org.example.services.product;

import org.example.dto.product.NewProduct;
import org.example.dto.product.ResponseProduct;
import org.example.exceptions.NotFoundException;

import java.util.Collection;

public interface ProductService {
    ResponseProduct add(NewProduct product) throws NotFoundException;
    ResponseProduct update(Long id, NewProduct product) throws NotFoundException;
    boolean delete(Long id) throws NotFoundException;
    ResponseProduct get(Long id) throws NotFoundException;
    Collection<ResponseProduct> getAll();
}
