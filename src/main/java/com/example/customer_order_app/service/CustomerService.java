package com.example.customer_order_app.service;

import com.example.customer_order_app.entity.Customer;
import java.util.List;

public interface CustomerService {
    Customer createCustomer(Customer customer);
    List<Customer> getAllCustomers();
    Customer getCustomerById(Long id);
    Customer updateCustomer(Long id, Customer customerDetails);
}
