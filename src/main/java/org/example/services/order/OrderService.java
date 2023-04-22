package org.example.services.order;

import org.example.dto.order.ResponseOrder;
import org.example.exceptions.NotFoundException;

import java.util.Collection;
import java.util.List;

public interface OrderService {
    ResponseOrder add(List<Long> productIds);
    boolean delete(Long id) throws NotFoundException;
    ResponseOrder get(Long id) throws NotFoundException;
    Collection<ResponseOrder> getAll();
}
