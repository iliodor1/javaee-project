package org.example.services.order;

import org.example.dao.order.OrderDao;
import org.example.dao.order.OrderDaoImpl;
import org.example.dto.order.ResponseOrder;
import org.example.entities.Order;
import org.example.entities.Product;
import org.example.exceptions.NotFoundException;
import org.example.utils.OrderMapper;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        Set<Product> products = productIds.stream()
                                          .map(p -> Product.builder()
                                                           .id(p)
                                                           .build())
                                          .collect(Collectors.toSet());

        Order order = Order.builder()
                           .orderDate(LocalDate.now())
                           .products(products)
                           .build();

        return OrderMapper.toResponseOrder(orderDao.save(order));
    }

    @Override
    public boolean delete(Long id) throws NotFoundException {
        orderDao.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Заказ с номером id=%s не найден", id))
                );

        return orderDao.delete(id);
    }

    @Override
    public ResponseOrder get(Long id) throws NotFoundException {
        Order order = orderDao.findById(id)
                              .orElseThrow(() -> new NotFoundException(
                                      String.format("Заказ с номером id=%s не найден", id))
                              );

        return OrderMapper.toResponseOrder(order);
    }

    @Override
    public Collection<ResponseOrder> getAll() {
        return orderDao.findAll()
                       .stream()
                       .map(OrderMapper::toResponseOrder)
                       .collect(Collectors.toList());
    }
}
