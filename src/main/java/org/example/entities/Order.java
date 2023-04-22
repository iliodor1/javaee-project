package org.example.entities;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

public class Order {
    private Long id;
    private LocalDate orderDate;
    //ManyToMany
    private Set<Product> products;

    public Order(Long id, LocalDate orderDate, Set<Product> products) {
        this.id = id;
        this.orderDate = orderDate;
        this.products = products;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public static OrderBuilder builder(){
        return new OrderBuilder();
    }

    public static class OrderBuilder{
        private Long id;
        private LocalDate orderDate;
        private Set<Product> products;

        public OrderBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public OrderBuilder orderDate(LocalDate date) {
            this.orderDate = date;
            return this;
        }

        public OrderBuilder products(Set<Product> products) {
            this.products = products;
            return this;
        }

        public Order build(){
            return new Order(id, orderDate, products);
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(getId(), order.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderDate=" + orderDate +
                ", products=" + products +
                '}';
    }
}
