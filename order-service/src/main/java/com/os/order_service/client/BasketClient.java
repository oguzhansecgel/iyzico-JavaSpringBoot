package com.os.order_service.client;

import com.os.order_service.model.Basket;
import com.os.order_service.model.BasketItem;
import com.os.order_service.model.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@FeignClient("basket-service")
public interface BasketClient {

    @GetMapping("/api/v1/basket/findBy/customersBasket/{customerId}")
    List<BasketItem> findByCustomerBasket(@PathVariable Long customerId);
    @GetMapping("/api/v1/basket/basket/findById/{basketId}")
    Basket findById(@PathVariable("basketId") String basketId);
}
