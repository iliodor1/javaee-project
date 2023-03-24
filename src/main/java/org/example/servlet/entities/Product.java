package org.example.servlet.entities;

import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
public class Product {
    private Long id;
    private String name;
    private Integer quantity;
    private BigDecimal price;

    //OneToMany
    private Supplier supplier;

    //ManyToMany
    private final List<Order> orders = new ArrayList<>();

}
