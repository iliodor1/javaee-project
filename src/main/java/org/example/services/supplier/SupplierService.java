package org.example.services.supplier;

import org.example.dto.supplier.NewSupplier;
import org.example.dto.supplier.ResponseSupplier;
import org.example.exceptions.NotFoundException;

import java.util.Collection;

public interface SupplierService {
    ResponseSupplier add(NewSupplier newSupplier);
    ResponseSupplier update(Long id, NewSupplier newSupplier) throws NotFoundException;
    boolean delete(Long id) throws NotFoundException;
    ResponseSupplier get(Long id) throws NotFoundException;
    Collection<ResponseSupplier> getAll();
}
