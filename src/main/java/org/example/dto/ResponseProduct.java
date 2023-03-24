package org.example.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ResponseProduct {
    private Long id;
    private String name;
    private Integer quantity;
    private BigDecimal price;
    private Long supplierId;
    private List<Long> ordersIds = new ArrayList<>();

    public ResponseProduct(
            Long id,
            String name,
            Integer quantity,
            BigDecimal price,
            Long supplierId,
            List<Long> ordersIds
    ) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.supplierId = supplierId;
        this.ordersIds = ordersIds;
    }

    public static ResponseProductBuilder builder(){
        return new ResponseProductBuilder();
    }
    public static class ResponseProductBuilder{
        private Long id;
        private String name;
        private Integer quantity;
        private BigDecimal price;
        private Long supplierId;
        private List<Long> ordersIds = new ArrayList<>();

        public ResponseProductBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ResponseProductBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ResponseProductBuilder quantity(Integer quantity) {
            this.quantity = quantity;
            return this;
        }

        public ResponseProductBuilder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public ResponseProductBuilder supplierId(Long supplierId) {
            this.supplierId = supplierId;
            return this;
        }

        public ResponseProductBuilder ordersIds(List<Long> ordersIds) {
            this.ordersIds = ordersIds;
            return this;
        }

        public ResponseProduct build(){
            return new ResponseProduct(id, name, quantity, price, supplierId,ordersIds);
        }
    }
}
