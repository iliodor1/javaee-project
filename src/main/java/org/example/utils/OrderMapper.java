package org.example.utils;

import org.example.dto.order.NewOrder;
import org.example.dto.order.ResponseOrder;
import org.example.entities.Order;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class OrderMapper {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static Order toOrder(NewOrder newOrder) {
        return Order.builder()
                    .date(LocalDate.now())
                    .products(newOrder.getProductIds())
                    .build();
    }

    public static ResponseOrder toResponseOrder(Order order) {

        return ResponseOrder.builder()
                            .id(order.getId())
                            .date(order.getDate().format(FORMATTER))
                            .productIds(order.getProductIds())
                            .build();
    }
}
