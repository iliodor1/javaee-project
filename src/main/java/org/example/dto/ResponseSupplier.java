package org.example.dto;

import java.util.ArrayList;
import java.util.List;

public class ResponseSupplier {
    private Long id;
    private String companyName;
    private String country;
    private List<Long> productIds;

    public ResponseSupplier(Long id, String companyName, String country) {
        this.id = id;
        this.companyName = companyName;
        this.country = country;
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
