package org.example.entities;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Supplier {
    private Long id;
    private String companyName;
    private String country;
    //ManyToOne
    private Set<Product> products;

    public Supplier(Long id, String companyName, String country, Set<Product> products) {
        this.id = id;
        this.companyName = companyName;
        this.country = country;
        this.products = products;
    }

    public static SupplierBuilder builder(){
        return new SupplierBuilder();
    }

    public static class SupplierBuilder{
        private Long id;
        private String companyName;
        private String country;
        private Set<Product> products;

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

        public SupplierBuilder productIds(Set<Product> products) {
            this.products = products;
            return this;
        }

        public Supplier build(){
            return new Supplier(id, companyName, country, products);
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

    public Set<Product> getProducts() {
        return products;
    }

    public Supplier setProducts(Set<Product> products) {
        this.products = products;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Supplier supplier = (Supplier) o;
        return Objects.equals(getId(), supplier.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Supplier{" +
                "id=" + id +
                ", companyName='" + companyName + '\'' +
                ", country='" + country + '\'' +
                ", products=" + products +
                '}';
    }
}
