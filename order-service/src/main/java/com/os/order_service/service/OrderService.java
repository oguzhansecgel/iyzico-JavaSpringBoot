package com.os.order_service.service;

import com.os.order_service.model.CustomerDto;
import com.os.order_service.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    Order createOrder(String basketId);
    List<Order> getAllOrder();
    Optional<Order> getByIdOrder(String orderId);
    List<Order> getOrderHistoryForCustomer(Long customerId);
}
