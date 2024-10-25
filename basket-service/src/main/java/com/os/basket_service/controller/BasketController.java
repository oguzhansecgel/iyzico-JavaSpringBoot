package com.os.basket_service.controller;

import com.os.basket_service.model.Basket;
import com.os.basket_service.model.BasketItem;
import com.os.basket_service.service.BasketService;
import com.os.spring_security.security.BaseJwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/basket")
public class BasketController {

    private final BasketService basketService;
    private final BaseJwtService baseJwtService;

    public BasketController(BasketService basketService, BaseJwtService baseJwtService) {
        this.basketService = basketService;
        this.baseJwtService = baseJwtService;
    }
    @GetMapping("/basket/findById/{basketId}")
    public Optional<Basket> findById(@PathVariable("basketId") String basketId)
    {
        return basketService.findByBasketId(basketId);
    }
    @GetMapping("/product/{productId}")
    public void product(@PathVariable Long productId)
    {
        basketService.productController(productId);
    }
    @GetMapping("/findBy/customersBasket/{customerId}")
    public Basket findByCustomerBasket(@PathVariable Long customerId)
    {
        return basketService.findByCustomersBasket(customerId);
    }
    @PostMapping("/create")
    public ResponseEntity<Basket> createBasketItem(@RequestHeader("Authorization") String token, @RequestBody Map<Long, Integer> productQuantities) {
        String tokenTrim = token.trim();
        String substring = tokenTrim.substring(7);
        Integer customerId = baseJwtService.extractUserId(substring);
        Basket basket = basketService.createBasketItem(Long.valueOf(customerId), productQuantities);
        return new ResponseEntity<>(basket, HttpStatus.CREATED);
    }
    @DeleteMapping("/delete/basket/beforeOrder/{basketId}")
    public void deleteBasketBeforeOrder(@PathVariable("basketId") String basketId)
    {
        basketService.deleteBasket(basketId);
    }

}
