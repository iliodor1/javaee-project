package org.example.utils;

import org.example.dto.NewProduct;
import org.example.dto.ResponseProduct;
import org.example.entities.Product;
import org.example.entities.Supplier;

import java.util.List;

public class ProductMapper {
    public static ResponseProduct toResponseProduct(Product product) {
        Long supplierId = product.getSupplier() == null ? null : product.getSupplier().getId();
        List<Long> ordersIds = product.getOrderIds() == null ? null : product.getOrderIds();

        return ResponseProduct.builder()
                              .id(product.getId())
                              .name(product.getName())
                              .quantity(product.getQuantity())
                              .price(product.getPrice())
                              .supplierId(supplierId)
                              .ordersIds(ordersIds)
                              .build();
    }

    public static Product toProduct(NewProduct newProduct, Supplier supplier) {
        List<Long> ordersIds = newProduct.getOrdersIds() == null ? null : newProduct.getOrdersIds();

        return Product.builder()
                      .name(newProduct.getName())
                      .quantity(newProduct.getQuantity())
                      .price(newProduct.getPrice())
                      .supplier(supplier)
                      .orderIds(ordersIds)
                      .build();
    }
}
