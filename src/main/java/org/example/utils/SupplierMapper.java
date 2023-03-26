package org.example.utils;

import org.example.dto.supplier.NewSupplier;
import org.example.dto.supplier.ResponseSupplier;
import org.example.entities.Supplier;

import java.util.List;

public class SupplierMapper {
    public static Supplier toSupplier(NewSupplier newSupplier) {

        return Supplier.builder()
                       .companyName(newSupplier.getCompanyName())
                       .country(newSupplier.getCountry())
                       .build();
    }

    public static ResponseSupplier toResponseSupplier(Supplier savedSupplier) {
        List<Long> productsIds
                = savedSupplier.getProductIds() == null ? null : savedSupplier.getProductIds();

        return ResponseSupplier.builder()
                               .id(savedSupplier.getId())
                               .companyName(savedSupplier.getCompanyName())
                               .country(savedSupplier.getCountry())
                               .productIds(productsIds)
                               .build();
    }
}
