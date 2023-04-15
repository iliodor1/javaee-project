package org.example.dao.product;

import org.example.entities.Product;
import org.example.entities.Supplier;
import org.example.utils.ConnectionPool;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.*;
import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ProductDaoTest {

    ProductDao productDao = ProductDaoImpl.getInstance();
    String PRODUCT_SQL = "INSERT INTO products(product_name, price, quantity, supplier_id) VALUES (?,?,?,?)";

    Product newProduct;


    @Test
    void whenSaveProduct_thenReturnSavedProduct() {
        Product product = Product.builder()
                                 .name("Name")
                                 .quantity(10)
                                 .price(BigDecimal.valueOf(10))
                                 .supplier(new Supplier(1L, "Company1", "Country1", null))
                                 .build();

        Product savedProduct = productDao.save(product);

        assertEquals("Name", savedProduct.getName());
        assertEquals(10, savedProduct.getQuantity());
        assertEquals(BigDecimal.valueOf(10), savedProduct.getPrice());
        assertEquals(product.getSupplier().getId(), savedProduct.getSupplier().getId());
        assertEquals(product.getSupplier().getCompanyName(), savedProduct.getSupplier().getCompanyName());

        deleteById(savedProduct.getId());
    }

    @Test
    void whenSaveEmptyProduct_thenThrowRuntimeException() {
        Product product = Product.builder()
                                 .build();

        assertThrows(RuntimeException.class, () -> productDao.save(product));

    }

    @Test
    void whenUpdate_thenReturnUpdatedProduct() {
        Supplier supplier = Supplier.builder().id(1L).build();
        insertNewProduct("NewProduct", BigDecimal.valueOf(10), 1, supplier);
        Long id = newProduct.getId();

        Product product = productDao.update(id, newProduct);

        assertEquals(id, product.getId());
        assertEquals(newProduct.getName(), product.getName());
        assertEquals(newProduct.getPrice(), product.getPrice());
        assertEquals(newProduct.getQuantity(), product.getQuantity());
        assertEquals(1L, product.getSupplier().getId());

        deleteById(product.getId());
    }

    @Test
    void whenDeleteExistProduct_thenReturnTrue() {
        insertNewProduct(
                "Name",
                BigDecimal.valueOf(1),
                1,
                new Supplier(1L, "", "", null)
        );

        boolean isDelete = productDao.delete(newProduct.getId());

        assertTrue(isDelete);
    }

    @Test
    void whenFindByIdExistProduct_thenReturnProduct() {
        Optional<Product> product = productDao.findById(1L);

        assertTrue(product.isPresent());
        assertEquals("Name1", product.get().getName());
        assertEquals(BigDecimal.valueOf(100), product.get().getPrice());
        assertEquals(1, product.get().getQuantity());
        assertEquals(1L, product.get().getSupplier().getId());
        assertEquals(1, product.get().getOrders().size());
    }

    @Test
    void whenFindByIdNotExistProduct_thenReturnOptionalEmpty() {
        Optional<Product> product = productDao.findById(999L);

        assertTrue(product.isEmpty());
    }

    @Test
    void whenFindAll_thenReturnCollectionOfProducts() {
        Collection<Product> products = productDao.findAll();

        assertFalse(products.isEmpty());
        assertEquals(3, products.size());
    }

    private void insertNewProduct(String name, BigDecimal price, Integer quantity, Supplier supplier) {
        newProduct = Product.builder()
                            .name(name)
                            .price(price)
                            .quantity(quantity)
                            .build();

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(PRODUCT_SQL,
                     Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, newProduct.getName());
            preparedStatement.setBigDecimal(2, newProduct.getPrice());
            preparedStatement.setInt(3, newProduct.getQuantity());
            preparedStatement.setLong(4, supplier.getId());
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

            if (generatedKeys.next()) {
                newProduct.setId(generatedKeys.getLong("id"));
            }

            newProduct.setSupplier(supplier);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private void deleteById(Long id) {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement
                     = connection.prepareStatement("delete from products where id = ?")) {
            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}