package com.os.basket_service.service;

import com.os.basket_service.model.Basket;

import java.util.Map;
import java.util.Optional;

public interface BasketService {
    Basket createBasketItem(Long customerId, Map<Long, Integer> productQuantities);
    Basket productController(Long productId);
    Basket findByCustomersBasket(Long customerId);
    Optional<Basket> findByBasketId(String basketId);
}
