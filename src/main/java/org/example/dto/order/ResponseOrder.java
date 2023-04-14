package org.example.dto.order;

import java.util.List;

public class ResponseOrder {
    private Long id;
    private String date;
    private List<Long> productIds;

    public ResponseOrder(Long id, String date, List<Long> productIds) {
        this.id = id;
        this.date = date;
        this.productIds = productIds;
    }

    public static OrderResponseOrder builder(){
        return new OrderResponseOrder();
    }

    public static class OrderResponseOrder {
        private Long id;
        private String date;
        private List<Long> productIds;

        public OrderResponseOrder id(Long id) {
            this.id = id;
            return this;
        }

        public OrderResponseOrder date(String date) {
            this.date = date;
            return this;
        }

        public OrderResponseOrder products(List<Long> productIds) {
            this.productIds = productIds;
            return this;
        }

        public ResponseOrder build() {
           return new ResponseOrder(id, date, productIds);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Long> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<Long> productIds) {
        this.productIds = productIds;
    }
}
