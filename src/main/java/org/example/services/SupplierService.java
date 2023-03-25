package org.example.services;

import org.example.dto.NewSupplier;
import org.example.dto.ResponseSupplier;

import java.util.Collection;

public interface SupplierService {
    ResponseSupplier add(NewSupplier newSupplier);
    ResponseSupplier update(Long id, NewSupplier newSupplier);
    boolean delete(Long id);
    ResponseSupplier get(Long id);
    Collection<ResponseSupplier> getAll();
}
