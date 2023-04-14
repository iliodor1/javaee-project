package org.example.services.supplier;

import org.example.dao.supplier.SupplierDao;
import org.example.dao.supplier.SupplierDaoImpl;
import org.example.dto.supplier.NewSupplier;
import org.example.dto.supplier.ResponseSupplier;
import org.example.entities.Supplier;
import org.example.exceptions.NotFoundException;
import org.example.utils.SupplierMapper;

import java.util.Collection;
import java.util.stream.Collectors;

public class SupplierServiceImpl implements SupplierService {
    private static final SupplierService INSTANCE = new SupplierServiceImpl();
    private final SupplierDao supplierDao;

    private SupplierServiceImpl() {
        supplierDao = SupplierDaoImpl.getInstance();
    }

    public static SupplierService getInstance() {
        return INSTANCE;
    }

    @Override
    public ResponseSupplier add(NewSupplier newSupplier) {
        Supplier supplier = SupplierMapper.toSupplier(newSupplier);
        Supplier savedSupplier = supplierDao.save(supplier);

        return SupplierMapper.toResponseSupplier(savedSupplier);
    }

    @Override
    public ResponseSupplier update(Long id, NewSupplier newSupplier) throws NotFoundException {
        Supplier updatedSupplier = SupplierMapper.toSupplier(newSupplier);

        Supplier supplier = supplierDao.findById(id)
                                       .orElseThrow(() ->
                                               new NotFoundException(String.format(
                                                       "Поставщик с id=%s не найден", id
                                               )));

        if (updatedSupplier.getCompanyName() != null) {
            supplier.setCompanyName(updatedSupplier.getCompanyName());
        }
        if (updatedSupplier.getCountry() != null) {
            supplier.setCountry(updatedSupplier.getCountry());
        }

        return SupplierMapper.toResponseSupplier(supplierDao.update(supplier));
    }

    @Override
    public boolean delete(Long id) throws NotFoundException {
        supplierDao.findById(id)
                   .orElseThrow(() ->
                           new NotFoundException(String.format(
                                   "Поставщик с id=%s не найден", id
                           )));

        return supplierDao.delete(id);
    }

    @Override
    public ResponseSupplier get(Long id) throws NotFoundException {
        Supplier supplier = supplierDao.findById(id)
                                       .orElseThrow(() ->
                                               new NotFoundException(String.format(
                                                       "Поставщик с id=%s не найден", id
                                               )));

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
