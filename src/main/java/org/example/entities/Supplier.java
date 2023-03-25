package org.example.entities;

import java.util.ArrayList;
import java.util.List;

public class Supplier {
    private Long id;
    private String companyName;
    private String country;
    //ManyToOne
    private List<Long> productIds;

    public Supplier(Long id, String companyName, String country, List<Long> productIds) {
        this.id = id;
        this.companyName = companyName;
        this.country = country;
        this.productIds = productIds;
    }

    public static SupplierBuilder builder(){
        return new SupplierBuilder();
    }

    public static class SupplierBuilder{
        private Long id;
        private String companyName;
        private String country;
        private List<Long> productIds;

        public SupplierBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public SupplierBuilder companyName(String companyName) {
            this.companyName = companyName;
            return this;
        }

        public SupplierBuilder country(String country) {
            this.country = country;
            return this;
        }

        public SupplierBuilder productIds(List<Long> productIds) {
            this.productIds = productIds;
            return this;
        }

        public Supplier build(){
            return new Supplier(id, companyName, country, productIds);
        }
    }

    public Long getId() {
        return id;
    }

    public Supplier setId(Long id) {
        this.id = id;
        return this;
    }

    public String getCompanyName() {
        return companyName;
    }

    public Supplier setCompanyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public Supplier setCountry(String country) {
        this.country = country;
        return this;
    }

    public List<Long> getProductIds() {
        return productIds;
    }

    public Supplier setProductIds(List<Long> productIds) {
        this.productIds = productIds;
        return this;
    }
}
