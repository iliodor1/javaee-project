package org.example.services.supplier;

import org.example.dto.supplier.NewSupplier;
import org.example.dto.supplier.ResponseSupplier;

import java.util.Collection;

public interface SupplierService {
    ResponseSupplier add(NewSupplier newSupplier);
    ResponseSupplier update(Long id, NewSupplier newSupplier);
    boolean delete(Long id);
    ResponseSupplier get(Long id);
    Collection<ResponseSupplier> getAll();
}
