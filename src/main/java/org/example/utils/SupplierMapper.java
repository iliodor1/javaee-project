package org.example.utils;

import org.example.dto.supplier.NewSupplier;
import org.example.dto.supplier.ResponseSupplier;
import org.example.entities.Product;
import org.example.entities.Supplier;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SupplierMapper {
    public static Supplier toSupplier(NewSupplier newSupplier) {

        return Supplier.builder()
                       .companyName(newSupplier.getCompanyName())
                       .country(newSupplier.getCountry())
                       .build();
    }

    public static ResponseSupplier toResponseSupplier(Supplier savedSupplier) {
        Set<Long> products
                = savedSupplier.getProducts() == null
                ? null
                : savedSupplier.getProducts().stream().map(Product::getId).collect(Collectors.toSet());

        return ResponseSupplier.builder()
                               .id(savedSupplier.getId())
                               .companyName(savedSupplier.getCompanyName())
                               .country(savedSupplier.getCountry())
                               .products(products)
                               .build();
    }
}
