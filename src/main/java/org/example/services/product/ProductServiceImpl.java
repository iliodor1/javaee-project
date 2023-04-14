package org.example.services.product;

import org.example.dao.product.ProductDao;
import org.example.dao.product.ProductDaoImpl;
import org.example.dao.supplier.SupplierDao;
import org.example.dao.supplier.SupplierDaoImpl;
import org.example.dto.product.NewProduct;
import org.example.dto.product.ResponseProduct;
import org.example.entities.Product;
import org.example.entities.Supplier;
import org.example.exceptions.NotFoundException;
import org.example.utils.ProductMapper;

import java.util.Collection;
import java.util.stream.Collectors;

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
    public ResponseProduct add(NewProduct newProduct) throws NotFoundException {
        Supplier supplier = null;
        if(newProduct.getSupplierId() != null) {
            supplier = supplierDao.findById(newProduct.getSupplierId())
                                  .orElseThrow(() ->
                                          new NotFoundException(String.format(
                                                  "Поставщик с id=%s не найден", newProduct.getSupplierId()
                                          )));
        }
        Product product = productDao.save(ProductMapper.toProduct(newProduct, supplier));

        return ProductMapper.toResponseProduct(product);
    }

    @Override
    public ResponseProduct update(Long id, NewProduct newProduct) throws NotFoundException {
        Product product = productDao.findById(id)
                                    .orElseThrow(() ->
                                            new NotFoundException(String.format(
                                                    "Продукт с id=%s не найден", id
                                            )));

        if(newProduct.getName() != null){
            product.setName(newProduct.getName());
        }
        if(newProduct.getPrice() != null){
            product.setPrice(newProduct.getPrice());
        }
        if(newProduct.getQuantity() != null){
            product.setQuantity(newProduct.getQuantity());
        }
        if(newProduct.getSupplierId() != null){
            Supplier supplier = supplierDao.findById(newProduct.getSupplierId())
                                           .orElseThrow(() ->
                                                   new NotFoundException(String.format(
                                                           "Поставщик с id=%s не найден", id
                                                   )));
            product.setSupplier(supplier);
        }

        Product updatedProduct = productDao.update(id, product);

        return ProductMapper.toResponseProduct(updatedProduct);
    }

    @Override
    public boolean delete(Long id) throws NotFoundException {
        productDao.findById(id)
                  .orElseThrow(() ->
                          new NotFoundException(String.format(
                                  "Продукт с id=%s не найден", id
                          )));

        return productDao.delete(id);
    }

    @Override
    public ResponseProduct get(Long id) throws NotFoundException {
        Product product = productDao.findById(id)
                                    .orElseThrow(() ->
                                            new NotFoundException(String.format(
                                                    "Продукт с id=%s не найден", id
                                            )));

        return ProductMapper.toResponseProduct(product);
    }

    @Override
    public Collection<ResponseProduct> getAll() {

        return productDao.findAll()
                         .stream()
                         .map(ProductMapper::toResponseProduct)
                         .collect(Collectors.toList());
    }
}
