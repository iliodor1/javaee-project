package org.example.utils;

import org.example.dto.order.ResponseOrder;
import org.example.entities.Order;
import org.example.entities.Product;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.stream.Collectors;

public class OrderMapper {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    public static ResponseOrder toResponseOrder(Order order) {

        return ResponseOrder.builder()
                            .id(order.getId())
                            .date(order.getOrderDate().format(FORMATTER))
                            .products(order.getProducts()
                                           .stream()
                                           .map(Product::getId)
                                           .collect(Collectors.toList()))
                            .build();
    }
}
