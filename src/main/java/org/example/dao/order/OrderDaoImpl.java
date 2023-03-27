package org.example.dao.order;

import org.example.entities.Order;
import org.example.utils.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class OrderDaoImpl implements OrderDao {
    private static final OrderDao INSTANCE = new OrderDaoImpl();
    private static final String INSERT_ORDER =
            "INSERT INTO orders (date) VALUES (?)";

    private static final String INSERT_ORDER_PRODUCT =
            "INSERT INTO order_products (order_id, product_id) VALUES (?, ?)";
    private static final String FIND_ORDER_BY_ID =
            "SELECT o.*, p.id product_id, p.name product_name, p.quantity, p.price, p.supplier_id " +
                    "FROM orders o " +
                    "LEFT JOIN order_products op " +
                    "ON o.id = op.order_id " +
                    "LEFT JOIN products p " +
                    "ON op.product_id = p.id " +
                    "WHERE o.id = ?";

    private OrderDaoImpl() {
    }

    public static OrderDao getInstance() {
        return INSTANCE;
    }

    @Override
    public Order save(Order order) {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement insertOrderStatement = connection.prepareStatement(
                     INSERT_ORDER, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement insertOrderProductStatement = connection.prepareStatement(
                     INSERT_ORDER_PRODUCT)) {

            insertOrderStatement.setObject(1, order.getDate());
            insertOrderStatement.executeUpdate();

            ResultSet generatedKeys = insertOrderStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                order.setId(generatedKeys.getLong(1));
            }

            for (Long productId : order.getProductIds()) {
                insertOrderProductStatement.setLong(1, order.getId());
                insertOrderProductStatement.setLong(2, productId);
                insertOrderProductStatement.addBatch();
            }
            insertOrderProductStatement.executeBatch();

            return order;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Order update(Long id, Order order) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
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
                                 .products(new ArrayList<>())
                                 .date(resultSet.getDate("date").toLocalDate())
                                 .build();
                }

                Long productId = resultSet.getLong("product_id");
                if (!resultSet.wasNull()) {
                    order.getProductIds().add(productId);
                }
            }
            return Optional.ofNullable(order);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Collection<Order> findAll() {
        return null;
    }
}
