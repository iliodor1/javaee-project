package org.example.utils;

import org.example.dto.product.NewProduct;
import org.example.dto.product.ResponseProduct;
import org.example.entities.Order;
import org.example.entities.Product;
import org.example.entities.Supplier;

import java.util.Set;
import java.util.stream.Collectors;

public class ProductMapper {
    public static ResponseProduct toResponseProduct(Product product) {
        Long supplierId = product.getSupplier() == null ? null : product.getSupplier().getId();
        Set<Long> orders = product.getOrders() == null ? null : product.getOrders()
                                                                       .stream()
                                                                       .map(Order::getId)
                                                                       .collect(Collectors.toSet());

        return ResponseProduct.builder()
                              .id(product.getId())
                              .name(product.getName())
                              .quantity(product.getQuantity())
                              .price(product.getPrice())
                              .supplierId(supplierId)
                              .ordersIds(orders)
                              .build();
    }

    public static Product toProduct(NewProduct newProduct, Supplier supplier) {
        return Product.builder()
                      .name(newProduct.getName())
                      .quantity(newProduct.getQuantity())
                      .price(newProduct.getPrice())
                      .supplier(supplier)
                      .build();
    }
}
