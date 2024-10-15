package com.os.basket_service.client;

import com.os.basket_service.model.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient("product-service")
public interface ProductClient {
    @GetMapping("/api/v1/product/getById/product/{id}")
    Optional<ProductDto> getProductById(@PathVariable Long id);
}
