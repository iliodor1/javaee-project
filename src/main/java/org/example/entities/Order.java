package org.example.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private Long id;
    private LocalDate date;
    //ManyToMany
    private final List<Long> productIds;

    public Order(Long id, LocalDate date, List<Long> productIds) {
        this.id = id;
        this.date = date;
        this.productIds = productIds;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public List<Long> getProductIds() {
        return productIds;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public static OrderBuilder builder(){
        return new OrderBuilder();
    }

    public static class OrderBuilder{
        private Long id;
        private LocalDate date;
        private List<Long> productIds = new ArrayList<>();

        public OrderBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public OrderBuilder date(LocalDate date) {
            this.date = date;
            return this;
        }

        public OrderBuilder products(List<Long> productIds) {
            this.productIds = productIds;
            return this;
        }

        public Order build(){
            return new Order(id, date, productIds);
        }
    }
}
