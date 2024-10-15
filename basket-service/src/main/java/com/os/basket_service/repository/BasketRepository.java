package com.os.basket_service.repository;

import com.os.basket_service.model.Basket;
import com.os.basket_service.model.BasketItem;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BasketRepository extends MongoRepository<Basket,String> {
    Basket findByCustomerId(Long customerId);
}
