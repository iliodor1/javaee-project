package org.example.dao.supplier;

import org.example.entities.Supplier;

import java.util.Collection;
import java.util.Optional;

public interface SupplierDao {
    Supplier save(Supplier supplier);
    Supplier update(Supplier supplier);
    boolean delete(Long id);
    Optional<Supplier> findById(Long id);
    Collection<Supplier> findAll();
}
