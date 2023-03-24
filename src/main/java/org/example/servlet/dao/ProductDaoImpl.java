package org.example.servlet.dao;

import org.example.servlet.entities.Product;
import org.example.servlet.utils.ConnectionPool;

import java.sql.*;
import java.util.Collection;

public class ProductDaoImpl implements ProductDao {
    private static final ProductDao INSTANCE = new ProductDaoImpl();

    private ProductDaoImpl() {
    }

    public static ProductDao getInstance() {
        return INSTANCE;
    }

    @Override
    public Product save(Product product) {

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO products(name, quantity, price) VALUES (?,?,?)",
                     Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, product.getName());
            preparedStatement.setInt(2, product.getQuantity());
            preparedStatement.setBigDecimal(3, product.getPrice());
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
        return null;
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
    public Product findById(Long id) {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM products WHERE id = ?"
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
                        .build();
            }

            return product;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Collection<Product> findAll() {
        return null;
    }
}
