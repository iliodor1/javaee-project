package org.example.dao.product;

import org.example.entities.Product;
import org.example.utils.ConnectionPool;

import java.sql.*;
import java.util.Collection;
import java.util.Optional;

public class ProductDaoImpl implements ProductDao {
    private static final ProductDao INSTANCE = new ProductDaoImpl();

    private ProductDaoImpl() {
    }

    public static ProductDao getInstance() {
        return INSTANCE;
    }

    @Override
    public Product save(Product product) {
// TODO: 24.03.2023 Add orders and supplier
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO products(name, quantity, price, supplier_id) VALUES (?,?,?,?)",
                     Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, product.getName());
            preparedStatement.setInt(2, product.getQuantity());
            preparedStatement.setBigDecimal(3, product.getPrice());
            if (product.getSupplier() != null) {
                preparedStatement.setLong(4, product.getSupplier().getId());
            } else {
                preparedStatement.setNull(4, Types.BIGINT);
            }
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

            if (generatedKeys.next()) {
                product.setId(generatedKeys.getLong("id"));
            }

            return product;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Product update(Long id, Product product) {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE products " +
                             "SET name = ?, quantity = ?, price = ?, supplier_id = ? " +
                             "WHERE id = ?")) {

            preparedStatement.setString(1, product.getName());
            preparedStatement.setInt(2, product.getQuantity());
            preparedStatement.setBigDecimal(3, product.getPrice());
            if (product.getSupplier() != null) {
                preparedStatement.setLong(4, product.getSupplier().getId());
            } else {
                preparedStatement.setNull(4, Types.BIGINT);
            }
            preparedStatement.setLong(5, product.getId());

            preparedStatement.executeUpdate();

            return product;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "DELETE FROM products WHERE id = ?"
             )) {

            preparedStatement.setLong(1, id);

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Product> findById(Long id) {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT p.*, s.id AS supplier_id, s.company_name AS supplier_name, po.order_id " +
                             "FROM products p " +
                             "LEFT JOIN suppliers s ON p.supplier_id = s.id " +
                             "LEFT JOIN order_products po ON p.id = po.product_id " +
                             "WHERE p.id = ?"
             )) {

            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Product product = null;
            if (resultSet.next()) {
                product = Product.builder()
                                 .id(resultSet.getLong("id"))
                                 .name(resultSet.getString("name"))
                                 .price(resultSet.getBigDecimal("price"))
                                 .quantity(resultSet.getInt("quantity"))
                                 //.supplier(Order.builder().id(resultSet.getInt("quantity"))
                                 .build();
            }

            return Optional.ofNullable(product);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Collection<Product> findAll() {
        return null;
    }
}
