package org.example.servlet.entities;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Supplier {
    private Long id;
    private String companyName;
    //ManyToOne
    private final List<Product> products = new ArrayList<>();
}
