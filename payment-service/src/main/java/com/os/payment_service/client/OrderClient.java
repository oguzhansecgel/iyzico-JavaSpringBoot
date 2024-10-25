package com.os.payment_service.client;

import com.os.payment_service.model.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient("order-service")
public interface OrderClient {

    @GetMapping("/api/v1/order/getById/orders/{orderId}")
    Order getByIdOrder(@PathVariable String orderId);
}
