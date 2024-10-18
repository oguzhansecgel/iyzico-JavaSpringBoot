package com.os.payment_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("basket-service")
public interface BasketClient {

    @DeleteMapping("/api/v1/basket/delete/basket/beforeOrder/{basketId}")
    void deleteBasketBeforeOrder(@PathVariable("basketId") String basketId);
}
