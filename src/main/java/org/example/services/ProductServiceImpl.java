package org.example.services;

import org.example.dao.ProductDao;
import org.example.dao.ProductDaoImpl;
import org.example.dto.NewProduct;
import org.example.dto.ResponseProduct;
import org.example.entities.Product;
import org.example.utils.ProductMapper;

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
    public ResponseProduct add(NewProduct newProduct) {
        Product product = productDao.save(newProduct);

        return ProductMapper.toResponseProduct(product);
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
    public ResponseProduct get(Long id) {
        Product product = productDao.findById(id);

        return ProductMapper.toResponseProduct(product);
    }

    @Override
    public Collection<Product> getAll() {
        return null;
    }
}
