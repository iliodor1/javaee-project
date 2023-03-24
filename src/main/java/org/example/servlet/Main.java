package org.example.servlet;


import org.example.servlet.entities.Product;
import org.example.servlet.services.ProductService;
import org.example.servlet.services.ProductServiceImpl;

public class Main {
    public static void main(String[] args) {
        ProductService productService = new ProductServiceImpl();

        Product product = productService.findById(2L);

        System.out.println(product.getName());
    }
}


