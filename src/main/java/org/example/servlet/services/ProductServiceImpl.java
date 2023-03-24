package org.example.servlet.services;

import org.example.servlet.dao.ProductDao;
import org.example.servlet.dao.ProductDaoImpl;
import org.example.servlet.entities.Product;

import java.util.Collection;

public class ProductServiceImpl implements ProductService {

    private final ProductDao productDao = ProductDaoImpl.getInstance();



    @Override
    public Product save(Product product) {
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
