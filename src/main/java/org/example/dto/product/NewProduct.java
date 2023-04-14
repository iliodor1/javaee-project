package org.example.dto.product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class NewProduct {
    private String name;
    private Integer quantity;
    private BigDecimal price;
    private Long supplierId;

    public NewProduct(String name, Integer quantity, BigDecimal price, Long supplierId) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.supplierId = supplierId;
    }

    public NewProduct() {
    }

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

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }
}
