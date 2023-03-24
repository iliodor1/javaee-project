package org.example.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class NewProduct {
    private String name;
    private Integer quantity;
    private BigDecimal price;
    private Long supplierId;

    private final List<Long> ordersIds = new ArrayList<>();

    public String getName() {
        return name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public List<Long> getOrdersIds() {
        return ordersIds;
    }
}
