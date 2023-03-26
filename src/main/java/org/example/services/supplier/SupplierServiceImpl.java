package org.example.services.supplier;

import org.example.dao.supplier.SupplierDao;
import org.example.dao.supplier.SupplierDaoImpl;
import org.example.dto.supplier.NewSupplier;
import org.example.dto.supplier.ResponseSupplier;
import org.example.entities.Supplier;
import org.example.utils.SupplierMapper;

import java.util.Collection;
import java.util.stream.Collectors;

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

        Supplier supplier = supplierDao.findById(id)
                                       .orElseThrow(RuntimeException::new);

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
        Supplier supplier = supplierDao.findById(id).orElseThrow(RuntimeException::new);

        return SupplierMapper.toResponseSupplier(supplier);
    }

    @Override
    public Collection<ResponseSupplier> getAll() {
        Collection<Supplier> suppliers = supplierDao.findAll();

        return suppliers.stream()
                        .map(SupplierMapper::toResponseSupplier)
                        .collect(Collectors.toList());
    }
}
