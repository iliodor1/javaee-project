package org.example.dao.order;

import org.example.entities.Order;

import java.util.Collection;
import java.util.Optional;

public interface OrderDao {
    Order save(Order order);

    boolean delete(Long id);

    Optional<Order> findById(Long id);

    Collection<Order> findAll();
}
