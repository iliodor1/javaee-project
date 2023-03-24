package org.example.servlet.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private Long id;
    private LocalDateTime date;
    private Integer quantity;
    private Double price;
    //ManyToMany
    private final List<Product> products = new ArrayList<>();
}
