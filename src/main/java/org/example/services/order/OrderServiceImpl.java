package org.example.services.order;

import org.example.dao.order.OrderDao;
import org.example.dao.order.OrderDaoImpl;
import org.example.dto.order.NewOrder;
import org.example.dto.order.ResponseOrder;
import org.example.entities.Order;
import org.example.utils.OrderMapper;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public class OrderServiceImpl implements OrderService {
    private static final OrderService INSTANCE = new OrderServiceImpl();
    private final OrderDao orderDao = OrderDaoImpl.getInstance();

    private OrderServiceImpl() {
    }

    public static OrderService getInstance() {
        return INSTANCE;
    }

    @Override
    public ResponseOrder add(List<Long> productIds) {
        Order order = Order.builder()
                           .date(LocalDate.now())
                           .products(productIds)
                           .build();

        return OrderMapper.toResponseOrder(orderDao.save(order));
    }

    @Override
    public ResponseOrder update(Long id, NewOrder order) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public ResponseOrder get(Long id) {
        Order order = orderDao.findById(id)
                              .orElseThrow(RuntimeException::new);

        return OrderMapper.toResponseOrder(order);
    }

    @Override
    public Collection<ResponseOrder> getAll() {
        return null;
    }
}
