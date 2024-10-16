package com.os.order_service.service.impl;

import com.os.order_service.client.BasketClient;
import com.os.order_service.model.Basket;
import com.os.order_service.model.CustomerDto;
import com.os.order_service.model.Order;
import com.os.order_service.repository.OrderRepository;
import com.os.order_service.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final BasketClient basketClient;

    public OrderServiceImpl(OrderRepository orderRepository, BasketClient basketClient) {
        this.orderRepository = orderRepository;
        this.basketClient = basketClient;
    }

    @Override
    public Order createOrder(String basketId) {
        Basket basket = basketClient.findById(basketId);

        CustomerDto customer = basket.getCustomer();

        Order order = new Order();
        order.setCustomer(customer);
        order.setItems(basket.getItems());
        order.setTotalPrice(basket.getTotalPrice());
        order.setStatus("PENDING");

        return orderRepository.save(order);

    }

    @Override
    public List<Order> getAllOrder() {
        return orderRepository.findAll();
    }

    @Override
    public Optional<Order> getByIdOrder(String orderId) {

        return orderRepository.findById(orderId);
    }

    @Override
    public Order getOrderHistoryForCustomer(Long customerId) {
        return orderRepository.findByCustomerId(customerId);
    }
}
