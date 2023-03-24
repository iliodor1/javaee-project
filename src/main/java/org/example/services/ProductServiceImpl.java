package org.example.services;

import org.example.dao.ProductDao;
import org.example.dao.ProductDaoImpl;
import org.example.dto.NewProduct;
import org.example.entities.Product;

import java.util.Collection;

public class ProductServiceImpl implements ProductService {

    private final ProductDao productDao = ProductDaoImpl.getInstance();

    private static final ProductService INSTANCE = new ProductServiceImpl();

    private ProductServiceImpl() {
    }

    public static ProductService getInstance() {
        return INSTANCE;
    }

    @Override
    public Product save(NewProduct product) {
        return productDao.save(product);
    }

    @Override
    public Product update(Long id, Product product) {
        return productDao.update(id, product);
    }

    @Override
    public boolean delete(Long id) {
        return productDao.delete(id);
    }

    @Override
    public Product findById(Long id) {
        return productDao.findById(id);
    }

    @Override
    public Collection<Product> findAll() {
        return null;
    }
}
