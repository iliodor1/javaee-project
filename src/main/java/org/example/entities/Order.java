package org.example.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private Long id;
    private LocalDateTime date;
    private Integer quantity;
    private Double price;
    //ManyToMany
    private final List<Product> products = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Double getPrice() {
        return price;
    }

    public List<Product> getProducts() {
        return products;
    }
}
