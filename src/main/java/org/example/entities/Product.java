package org.example.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

public class Product implements Serializable {
    private Long id;
    private String name;
    private Integer quantity;
    private BigDecimal price;

    //OneToMany
    private Supplier supplier;

    //ManyToMany
    private Set<Order> orders;

    public Product(Long id, String name, Integer quantity, BigDecimal price, Supplier supplier, Set<Order> orders) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.supplier = supplier;
        this.orders = orders;
    }

    public Long getId() {
        return id;
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

    public Supplier getSupplier() {
        return supplier;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    public static ProductBuilder builder() {
        return new ProductBuilder();
    }

    public static class ProductBuilder {
        private Long id;
        private String name;
        private Integer quantity;
        private BigDecimal price;
        private Supplier supplier;
        private Set<Order> orders;

        public ProductBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ProductBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ProductBuilder quantity(Integer quantity) {
            this.quantity = quantity;
            return this;
        }

        public ProductBuilder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public ProductBuilder supplier(Supplier supplier) {
            this.supplier = supplier;
            return this;
        }

        public ProductBuilder orders(Set<Order> orders) {
            this.orders = orders;
            return this;
        }

        public Product build() {
            return new Product(id, name, quantity, price, supplier, orders);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(getId(), product.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", supplier=" + supplier +
                ", orders=" + orders +
                '}';
    }
}
