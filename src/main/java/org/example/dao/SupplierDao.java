package org.example.dao;

import org.example.entities.Supplier;

import java.util.Collection;

public interface SupplierDao {
    Supplier save(Supplier supplier);
    Supplier update(Supplier supplier);
    boolean delete(Long id);
    Supplier findById(Long id);
    Collection<Supplier> findAll();
}
