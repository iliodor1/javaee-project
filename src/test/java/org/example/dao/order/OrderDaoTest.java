package org.example.dao.order;

import org.example.entities.Order;
import org.example.entities.Product;
import org.example.utils.ConnectionPool;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OrderDaoTest {

    OrderDao orderDao = OrderDaoImpl.getInstance();
    private static final String INSERT_ORDER =
            "INSERT INTO orders (order_date) VALUES (?)";

    private static final String INSERT_ORDER_PRODUCT =
            "INSERT INTO order_products (order_id, product_id) VALUES (?, ?)";

    Order testOrder;

    @Test
    void whenSaveOrder_thenReturnSavedOrder() {
        testOrder = Order.builder()
                         .orderDate(LocalDate.now())
                         .products(
                                 Set.of(
                                         Product.builder()
                                                .id(1L)
                                                .build(),
                                         Product.builder()
                                                .id(3L)
                                                .build()
                                 )
                         ).build();

        Order savedOrder = orderDao.save(testOrder);

        assertNotNull(savedOrder.getId());
        assertEquals(LocalDate.now(), savedOrder.getOrderDate());
        assertEquals(2, savedOrder.getProducts().size());

        deleteById(savedOrder.getId());
    }

    @Test
    void whenDeleteExistOrder_thenReturnTrue() {
        testOrder = Order.builder()
                         .orderDate(LocalDate.now())
                         .products(
                                 Set.of(
                                         Product.builder()
                                                .id(1L)
                                                .build(),
                                         Product.builder()
                                                .id(3L)
                                                .build()
                                 )
                         ).build();

        insertNewOrder(testOrder);

        boolean isDelete = orderDao.delete(testOrder.getId());

        assertTrue(isDelete);
    }


    @Test
    void whenDeleteNotExistOrder_thenReturnFalse() {
        boolean isDelete = orderDao.delete(999L);

        assertFalse(isDelete);
    }


    @Test
    void whenFindByIdExistOrder_thenReturnOrder() {
        Optional<Order> optionalOrder = orderDao.findById(1L);

        assertTrue(optionalOrder.isPresent());
        assertEquals(1L, optionalOrder.get().getId());
        assertEquals(LocalDate.now(), optionalOrder.get().getOrderDate());
        assertEquals(3, optionalOrder.get().getProducts().size());
    }


    @Test
    void whenFindAllOrders_thenReturnCollectionOfOrders() {
        Collection<Order> orders = orderDao.findAll();

        System.out.println(orders);

        assertFalse(orders.isEmpty());
        assertEquals(3, orders.size());
    }

    private void insertNewOrder(Order order) {
        Connection connection = ConnectionPool.getConnection();
        try (connection;
             PreparedStatement insertOrderStatement = connection.prepareStatement(
                     INSERT_ORDER, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement insertOrderProductStatement = connection.prepareStatement(
                     INSERT_ORDER_PRODUCT)) {
            connection.setAutoCommit(false);
            insertOrderStatement.setObject(1, LocalDate.now());
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

            testOrder = order;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
    }


    private void deleteById(Long id) {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement
                     = connection.prepareStatement("delete from orders where id = ?")) {
            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}