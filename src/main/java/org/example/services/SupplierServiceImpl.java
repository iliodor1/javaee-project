package org.example.services;

import org.example.dao.SupplierDao;
import org.example.dao.SupplierDaoImpl;
import org.example.dto.NewSupplier;
import org.example.dto.ResponseSupplier;
import org.example.entities.Supplier;
import org.example.utils.SupplierMapper;

import java.util.Collection;

public class SupplierServiceImpl implements SupplierService {

    private static final SupplierService INSTANCE = new SupplierServiceImpl();

    private SupplierServiceImpl() {
    }

    public static SupplierService getInstance() {
        return INSTANCE;
    }


    private final SupplierDao supplierDao = SupplierDaoImpl.getInstance();

    @Override
    public ResponseSupplier add(NewSupplier newSupplier) {
        Supplier supplier = SupplierMapper.toSupplier(newSupplier);
        Supplier savedSupplier = supplierDao.save(supplier);

        return SupplierMapper.toResponseSupplier(savedSupplier);
    }

    @Override
    public ResponseSupplier update(Long id, NewSupplier newSupplier) {
        Supplier updatedSupplier = SupplierMapper.toSupplier(newSupplier);

        Supplier supplier = supplierDao.findById(id);

        if (updatedSupplier.getCompanyName() != null) {
            supplier.setCompanyName(updatedSupplier.getCompanyName());
        }
        if (updatedSupplier.getCountry() != null) {
            supplier.setCountry(updatedSupplier.getCountry());
        }

        return SupplierMapper.toResponseSupplier(supplierDao.update(supplier));
    }

    @Override
    public boolean delete(Long id) {
        return supplierDao.delete(id);
    }

    @Override
    public ResponseSupplier get(Long id) {
        Supplier supplier = supplierDao.findById(id);

        return SupplierMapper.toResponseSupplier(supplier);
    }

    @Override
    public Collection<Long> getAll() {
        return null;
    }
}
