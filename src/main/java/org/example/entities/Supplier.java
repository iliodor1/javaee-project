package org.example.entities;

import java.util.ArrayList;
import java.util.List;

public class Supplier {
    private Long id;
    private String companyName;
    private String country;
    //ManyToOne
    private final List<Product> products = new ArrayList<>();


    public Long getId() {
        return id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getCountry() {
        return country;
    }

    public List<Product> getProducts() {
        return products;
    }
}
