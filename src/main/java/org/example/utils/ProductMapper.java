package org.example.utils;

import org.example.dto.ResponseProduct;
import org.example.entities.Order;
import org.example.entities.Product;
import org.example.entities.Supplier;

import java.util.List;
import java.util.stream.Collectors;

public class ProductMapper {
    public static ResponseProduct toResponseProduct(Product product) {
        Long supplierId = product.getSupplier() == null ? null : product.getSupplier().getId();
        List<Order> orders = product.getOrders();
        List<Long> ordersIds = null;

        if (orders != null)
            ordersIds = orders.stream()
                              .map(Order::getId)
                              .collect(Collectors.toList());

        return ResponseProduct.builder()
                              .id(product.getId())
                              .name(product.getName())
                              .quantity(product.getQuantity())
                              .price(product.getPrice())
                              .supplierId(supplierId)
                              .ordersIds(ordersIds)
                              .build();
    }
}
