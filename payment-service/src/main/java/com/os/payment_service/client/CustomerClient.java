package com.os.payment_service.client;

import com.os.payment_service.model.Customer;
import com.os.payment_service.model.CustomerContactInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient("customer-service")
public interface CustomerClient {
    @GetMapping("/api/v1/users/getByIdUser/{id}")
    Customer getByIdUser(@PathVariable Long id);
    @GetMapping("/api/v1/contactInfo/customer/{customerId}")
   List<CustomerContactInfo> getContactInfosByCustomerId(@PathVariable Long customerId);
}
