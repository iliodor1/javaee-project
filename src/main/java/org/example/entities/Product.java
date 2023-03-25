package org.example.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class Product implements Serializable {
    private Long id;
    private String name;
    private Integer quantity;
    private BigDecimal price;

    //OneToMany
    private Supplier supplier;

    //ManyToMany
    private final List<Long> orderIds;

    public Product(Long id, String name, Integer quantity, BigDecimal price, Supplier supplier, List<Long> orderIds) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.supplier = supplier;
        this.orderIds = orderIds;
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

    public List<Long> getOrderIds() {
        return orderIds;
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

    public static ProductBuilder builder() {
        return new ProductBuilder();
    }

    public static class ProductBuilder {
        private Long id;
        private String name;
        private Integer quantity;
        private BigDecimal price;
        private Supplier supplier;
        private List<Long> orderIds;

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

        public ProductBuilder orderIds(List<Long> orderIds) {
            this.orderIds = orderIds;
            return this;
        }

        public Product build() {
            return new Product(id, name, quantity, price, supplier, orderIds);
        }
    }

}
