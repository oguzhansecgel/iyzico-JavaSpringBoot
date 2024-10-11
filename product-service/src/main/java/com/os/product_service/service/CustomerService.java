package com.os.product_service.service;

import com.os.product_service.model.Customer;

public interface CustomerService {
    Customer addCustomer(Customer customer);
    Customer getCustomerById(int id);
}
