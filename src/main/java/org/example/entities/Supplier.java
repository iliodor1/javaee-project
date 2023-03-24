package org.example.entities;

import java.util.ArrayList;
import java.util.List;

public class Supplier {
    private Long id;
    private String companyName;
    //ManyToOne
    private final List<Product> products = new ArrayList<>();
}
