package com.os.order_service.client;

import com.os.order_service.model.CustomerDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient("customer-service")
public interface CustomerClient {
    @GetMapping("/api/v1/users/getByIdUser/{id}")
    Optional<CustomerDto> getByIdUser(@PathVariable Long id);
}
