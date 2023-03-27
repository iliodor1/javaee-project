package org.example.services.order;

import org.example.dto.order.ResponseOrder;
import org.example.dto.order.NewOrder;

import java.util.Collection;
import java.util.List;

public interface OrderService {
    ResponseOrder add(List<Long> productIds);
    ResponseOrder update(Long id, NewOrder order);
    boolean delete(Long id);
    ResponseOrder get(Long id);
    Collection<ResponseOrder> getAll();
}
