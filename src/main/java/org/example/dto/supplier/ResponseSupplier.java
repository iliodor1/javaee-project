package org.example.dto.supplier;

import java.util.List;

public class ResponseSupplier {
    private Long id;
    private String companyName;
    private String country;
    private List<Long> productIds;

    public ResponseSupplier(Long id, String companyName, String country, List<Long> productIds) {
        this.id = id;
        this.companyName = companyName;
        this.country = country;
        this.productIds = productIds;
    }

    public static ResponseSupplierBuilder builder(){
        return new ResponseSupplierBuilder();
    }
    public static class ResponseSupplierBuilder{
        private Long id;
        private String companyName;
        private String country;
        private List<Long> productIds;

        public ResponseSupplierBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ResponseSupplierBuilder companyName(String companyName) {
            this.companyName = companyName;
            return this;
        }

        public ResponseSupplierBuilder country(String country) {
            this.country = country;
            return this;
        }

        public ResponseSupplierBuilder productIds(List<Long> productIds) {
            this.productIds = productIds;
            return this;
        }

        public ResponseSupplier build(){
            return new ResponseSupplier(id, companyName, country, productIds);
        }
    }

    public Long getId() {
        return id;
    }

    public ResponseSupplier setId(Long id) {
        this.id = id;
        return this;
    }

    public String getCompanyName() {
        return companyName;
    }

    public ResponseSupplier setCompanyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public ResponseSupplier setCountry(String country) {
        this.country = country;
        return this;
    }

    public List<Long> getProductIds() {
        return productIds;
    }

    public ResponseSupplier setProductIds(List<Long> productIds) {
        this.productIds = productIds;
        return this;
    }
}
