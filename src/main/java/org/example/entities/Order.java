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
    private List<Long> productIds;

    public Order(Long id, LocalDateTime date, Integer quantity, Double price, List<Long> productIds) {
        this.id = id;
        this.date = date;
        this.quantity = quantity;
        this.price = price;
        this.productIds = productIds;
    }

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

    public List<Long> getProductIds() {
        return productIds;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public static OrderBuilder builder(){
        return new OrderBuilder();
    }

    public static class OrderBuilder{
        private Long id;
        private LocalDateTime date;
        private Integer quantity;
        private Double price;
        private List<Long> productIds = new ArrayList<>();

        public OrderBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public OrderBuilder date(LocalDateTime date) {
            this.date = date;
            return this;
        }

        public OrderBuilder quantity(Integer quantity) {
            this.quantity = quantity;
            return this;
        }

        public OrderBuilder price(Double price) {
            this.price = price;
            return this;
        }

        public OrderBuilder products(List<Long> productIds) {
            this.productIds = productIds;
            return this;
        }

        public Order build(){
            return new Order(id, date, quantity, price, productIds);
        }
    }
}
