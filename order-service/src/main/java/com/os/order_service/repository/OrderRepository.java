package com.os.order_service.repository;

import com.os.order_service.model.CustomerDto;
import com.os.order_service.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order, String> {
    Order findByCustomerId(Long customerId);
}
