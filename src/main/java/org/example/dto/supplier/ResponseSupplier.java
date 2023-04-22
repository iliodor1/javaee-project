package org.example.dto.supplier;

import java.util.Set;

public class ResponseSupplier {
    private Long id;
    private String companyName;
    private String country;
    private Set<Long> products;

    public ResponseSupplier(Long id, String companyName, String country, Set<Long> products) {
        this.id = id;
        this.companyName = companyName;
        this.country = country;
        this.products = products;
    }


    public static ResponseSupplierBuilder builder() {
        return new ResponseSupplierBuilder();
    }

    public static class ResponseSupplierBuilder {
        private Long id;
        private String companyName;
        private String country;
        private Set<Long> products;

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

        public ResponseSupplierBuilder products(Set<Long> products) {
            this.products = products;
            return this;
        }

        public ResponseSupplier build() {
            return new ResponseSupplier(id, companyName, country, products);
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

    public Set<Long> getProducts() {
        return products;
    }

    public ResponseSupplier setProducts(Set<Long> products) {
        this.products = products;
        return this;
    }
}
