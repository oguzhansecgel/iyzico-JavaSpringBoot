package com.os.product_service.service.impl;

import com.os.product_service.model.Customer;
import com.os.product_service.repository.CustomerRepository;
import com.os.product_service.service.CustomerService;
import org.springframework.stereotype.Service;

import java.io.Serial;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    @Override
    public Customer addCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }
}
