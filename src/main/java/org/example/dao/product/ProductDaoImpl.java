package org.example.dao.product;

import org.example.entities.Order;
import org.example.entities.Product;
import org.example.entities.Supplier;
import org.example.utils.ConnectionPool;

import java.sql.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class ProductDaoImpl implements ProductDao {
    private static final ProductDao INSTANCE = new ProductDaoImpl();

    private static final String FIND_PRODUCT_BY_ID =
            "SELECT p.*, s.company_name, s.country, po.order_id, order_date " +
                    "FROM products p " +
                    "LEFT JOIN suppliers s ON p.supplier_id = s.id " +
                    "LEFT JOIN order_products po ON p.id = po.product_id " +
                    "LEFT JOIN orders o ON o.id = po.order_id " +
                    "WHERE p.id = ?";
    private static final String FIND_ALL_PRODUCT =
            "SELECT p.*, s.company_name, s.country, po.order_id, order_date " +
                    "FROM products p " +
                    "LEFT JOIN suppliers s ON p.supplier_id = s.id " +
                    "LEFT JOIN order_products po ON p.id = po.product_id " +
                    "LEFT JOIN orders o ON o.id = po.order_id";
    private static final String SAVE_PRODUCT =
            "INSERT INTO products(name, quantity, price, supplier_id) " +
                    "VALUES (?,?,?,?)";
    private static final String UPDATE_PRODUCT =
            "UPDATE products " +
            "SET name = ?, quantity = ?, price = ?, supplier_id = ? " +
            "WHERE id = ?";

    private static final String DELETE_PRODUCT_BY_ID = "DELETE FROM products WHERE id = ?";

    private ProductDaoImpl() {
    }

    public static ProductDao getInstance() {
        return INSTANCE;
    }

    @Override
    public Product save(Product product) {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     SAVE_PRODUCT,
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
                     UPDATE_PRODUCT)) {

            preparedStatement.setString(1, product.getName());
            preparedStatement.setInt(2, product.getQuantity());
            preparedStatement.setBigDecimal(3, product.getPrice());
            if (product.getSupplier() != null) {
                preparedStatement.setLong(4, product.getSupplier().getId());
            } else {
                preparedStatement.setNull(4, Types.BIGINT);
            }
            preparedStatement.setLong(5, id);

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
                     DELETE_PRODUCT_BY_ID
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
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_PRODUCT_BY_ID)) {

            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Product product = null;
            Supplier supplier;
            while (resultSet.next()) {
                if (product == null) {
                    product = Product.builder()
                                     .id(resultSet.getLong("id"))
                                     .name(resultSet.getString("name"))
                                     .price(resultSet.getBigDecimal("price"))
                                     .quantity(resultSet.getInt("quantity"))
                                     .orders(new HashSet<>())
                                     .build();

                    supplier = Supplier.builder()
                                       .id(resultSet.getLong("supplier_id"))
                                       .companyName(resultSet.getString("company_name"))
                                       .country(resultSet.getString("country"))
                                       .build();

                    product.setSupplier(supplier);
                }

                Order order = Order.builder()
                                   .id(resultSet.getLong("order_id"))
                                   .orderDate(resultSet.getDate("order_date") == null
                                           ? null
                                           : resultSet.getDate("order_date").toLocalDate())
                                   .build();

                if (!resultSet.wasNull()) {
                    product.getOrders().add(order);
                }
            }

            return Optional.ofNullable(product);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Collection<Product> findAll() {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_PRODUCT)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            Set<Product> products = new HashSet<>();
            Product product = null;
            Set<Order> orders = null;

            while (resultSet.next()) {
                if (product == null || resultSet.getLong("id") != product.getId()) {
                    orders = new HashSet<>();
                }

                product = Product.builder()
                                 .id(resultSet.getLong("id"))
                                 .name(resultSet.getString("name"))
                                 .price(resultSet.getBigDecimal("price"))
                                 .quantity(resultSet.getInt("quantity"))
                                 .supplier(Supplier.builder()
                                                   .id(resultSet.getLong("supplier_id"))
                                                   .companyName(resultSet.getString("company_name"))
                                                   .country(resultSet.getString("country"))
                                                   .build())
                                 .build();

                Order order = Order.builder()
                                   .id(resultSet.getLong("order_id"))
                                   .orderDate(resultSet.getDate("order_date") == null
                                           ? null
                                           : resultSet.getDate("order_date").toLocalDate())
                                   .build();

                if (!resultSet.wasNull()) {
                    orders.add(order);
                    product.setOrders(orders);
                }

                products.add(product);
            }

            return products;
        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
