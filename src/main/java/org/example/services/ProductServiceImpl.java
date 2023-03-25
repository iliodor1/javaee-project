package org.example.services;

import org.example.dao.ProductDao;
import org.example.dao.ProductDaoImpl;
import org.example.dao.SupplierDao;
import org.example.dao.SupplierDaoImpl;
import org.example.dto.NewProduct;
import org.example.dto.ResponseProduct;
import org.example.entities.Product;
import org.example.entities.Supplier;
import org.example.utils.ProductMapper;

import java.util.Collection;

public class ProductServiceImpl implements ProductService {

    private final ProductDao productDao = ProductDaoImpl.getInstance();
    private final SupplierDao supplierDao = SupplierDaoImpl.getInstance();

    private static final ProductService INSTANCE = new ProductServiceImpl();

    private ProductServiceImpl() {
    }

    public static ProductService getInstance() {
        return INSTANCE;
    }

    @Override
    public ResponseProduct add(NewProduct newProduct) {
        Supplier supplier = supplierDao.findById(newProduct.getSupplierId());
        Product product = productDao.save(ProductMapper.toProduct(newProduct, supplier));

        return ProductMapper.toResponseProduct(product);
    }

    @Override
    public ResponseProduct update(Long id, Product product) {
        Product updatedProduct = productDao.update(id, product);

        return ProductMapper.toResponseProduct(updatedProduct);
    }

    @Override
    public boolean delete(Long id) {
        return productDao.delete(id);
    }

    @Override
    public ResponseProduct get(Long id) {
        Product product = productDao.findById(id);

        return ProductMapper.toResponseProduct(product);
    }

    @Override
    public Collection<Long> getAll() {
        return null;
    }
}
