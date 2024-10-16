package com.os.order_service.controller;

import com.os.order_service.model.CustomerDto;
import com.os.order_service.model.Order;
import com.os.order_service.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    @GetMapping("/getAll/orders")
    public List<Order> getAllOrders()
    {
        return orderService.getAllOrder();
    }
    @GetMapping("/getById/orders/{orderId}")
    public Optional<Order> getByIdOrder(@PathVariable String orderId)
    {
        return orderService.getByIdOrder(orderId);
    }
    @GetMapping("/getOrderHistoryForCustomer/{customerId}")
    public Order getOrderHistoryForCustomer(@PathVariable Long customerId)
    {
        return orderService.getOrderHistoryForCustomer(customerId);
    }
    @PostMapping("/create-order/{basketId}")
    public ResponseEntity<Order> createOrder(@PathVariable String basketId) {
        Order order = orderService.createOrder(basketId);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }
}
