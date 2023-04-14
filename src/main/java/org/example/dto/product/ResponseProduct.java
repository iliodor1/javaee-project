package org.example.dto.product;

import org.example.entities.Order;

import java.math.BigDecimal;
import java.util.Set;

public class ResponseProduct {
    private Long id;
    private String name;
    private Integer quantity;
    private BigDecimal price;
    private Long supplierId;
    private Set<Long> orders;

    public ResponseProduct(
            Long id,
            String name,
            Integer quantity,
            BigDecimal price,
            Long supplierId,
            Set<Long> orders
    ) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.supplierId = supplierId;
        this.orders = orders;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public Set<Long> getOrders() {
        return orders;
    }

    public void setOrders(Set<Long> orders) {
        this.orders = orders;
    }

    public static ResponseProductBuilder builder(){
        return new ResponseProductBuilder();
    }
    public static class ResponseProductBuilder{
        private Long id;
        private String name;
        private Integer quantity;
        private BigDecimal price;
        private Long supplierId;
        private Set<Long> orders;

        public ResponseProductBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ResponseProductBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ResponseProductBuilder quantity(Integer quantity) {
            this.quantity = quantity;
            return this;
        }

        public ResponseProductBuilder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public ResponseProductBuilder supplierId(Long supplierId) {
            this.supplierId = supplierId;
            return this;
        }

        public ResponseProductBuilder ordersIds(Set<Long> orders) {
            this.orders = orders;
            return this;
        }

        public ResponseProduct build(){
            return new ResponseProduct(id, name, quantity, price, supplierId, orders);
        }
    }
}
