package org.example.dao.order;

import org.example.entities.Order;
import org.example.entities.Product;
import org.example.utils.ConnectionPool;

import java.sql.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class OrderDaoImpl implements OrderDao {
    private static final OrderDao INSTANCE = new OrderDaoImpl();
    private static final String INSERT_ORDER =
            "INSERT INTO orders (order_date) VALUES (?)";

    private static final String INSERT_ORDER_PRODUCT =
            "INSERT INTO order_products (order_id, product_id) VALUES (?, ?)";
    private static final String FIND_ORDER_BY_ID =
            "SELECT o.*, p.id product_id, p.product_name, p.quantity, p.price, p.supplier_id " +
                    "FROM orders o " +
                    "LEFT JOIN order_products op " +
                    "ON o.id = op.order_id " +
                    "LEFT JOIN products p " +
                    "ON op.product_id = p.id " +
                    "WHERE o.id = ?";

    private static final String FIND_ALL_ORDERS =
            "SELECT o.*, p.id product_id, p.product_name, p.quantity, p.price, p.supplier_id " +
                    "FROM orders o " +
                    "LEFT JOIN order_products op " +
                    "ON o.id = op.order_id " +
                    "LEFT JOIN products p " +
                    "ON op.product_id = p.id";

    private static final String DELETE_ORDER_BY_ID = "DELETE FROM orders WHERE id = ?";


    private OrderDaoImpl() {
    }

    public static OrderDao getInstance() {
        return INSTANCE;
    }


    @Override
    public Order save(Order order) {
        Connection connection = ConnectionPool.getConnection();
        try (connection;
             PreparedStatement insertOrderStatement = connection.prepareStatement(
                     INSERT_ORDER, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement insertOrderProductStatement = connection.prepareStatement(
                     INSERT_ORDER_PRODUCT)) {

            connection.setAutoCommit(false);
            insertOrderStatement.setObject(1, order.getOrderDate());
            insertOrderStatement.executeUpdate();

            ResultSet generatedKeys = insertOrderStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                order.setId(generatedKeys.getLong(1));
            }

            if(order.getProducts() != null) {
                for (Product product : order.getProducts()) {
                    insertOrderProductStatement.setLong(1, order.getId());
                    insertOrderProductStatement.setLong(2, product.getId());
                    insertOrderProductStatement.addBatch();
                }
                insertOrderProductStatement.executeBatch();
            } else {
                throw new RuntimeException();
            }
            connection.commit();

            return order;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement deleteOrderStatement = connection.prepareStatement(DELETE_ORDER_BY_ID)
        ) {
            deleteOrderStatement.setLong(1, id);

            return deleteOrderStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Order> findById(Long id) {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ORDER_BY_ID)
        ) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Order order = null;

            while (resultSet.next()) {
                if (order == null) {
                    order = Order.builder()
                                 .id(resultSet.getLong("id"))
                                 .orderDate(resultSet.getDate("order_date").toLocalDate())
                                 .products(new HashSet<>())
                                 .build();
                }

                Product product = Product.builder()
                                         .id(resultSet.getLong("product_id"))
                                         .name(resultSet.getString("product_name"))
                                         .price(resultSet.getBigDecimal("price"))
                                         .quantity(resultSet.getInt("quantity"))
                                         .build();

                if (!resultSet.wasNull()) {
                    order.getProducts().add(product);
                }
            }
            return Optional.ofNullable(order);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public Collection<Order> findAll() {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_ORDERS)
        ) {

            ResultSet resultSet = preparedStatement.executeQuery();
            Set<Order> orders = new HashSet<>();
            Set<Product> products = new HashSet<>();
            Order order = null;

            while (resultSet.next()) {
                if (order == null || resultSet.getLong("id") != order.getId()) {
                    products = new HashSet<>();
                }

                order = Order.builder()
                             .id(resultSet.getLong("id"))
                             .orderDate(resultSet.getDate("order_date").toLocalDate())
                             .build();

                Product product = Product.builder()
                                         .id(resultSet.getLong("product_id"))
                                         .name(resultSet.getString("product_name"))
                                         .price(resultSet.getBigDecimal("price"))
                                         .quantity(resultSet.getInt("quantity"))
                                         .build();

                if (!resultSet.wasNull()) {
                    products.add(product);
                    order.setProducts(products);
                }
                orders.add(order);
            }
            return orders;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
