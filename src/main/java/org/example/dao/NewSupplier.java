package org.example.dao;

import java.util.ArrayList;
import java.util.List;

public class NewSupplier {
    private String companyName;
    private String country;
    private List<Long> productIds;

    public NewSupplier(String companyName, String country, List<Long> productIds) {
        this.companyName = companyName;
        this.country = country;
        this.productIds = productIds;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getCountry() {
        return country;
    }

    public List<Long> getProductIds() {
        return productIds;
    }

    public NewSupplier setCompanyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public NewSupplier setCountry(String country) {
        this.country = country;
        return this;
    }

    public NewSupplier setProductIds(List<Long> productIds) {
        this.productIds = productIds;
        return this;
    }
}
