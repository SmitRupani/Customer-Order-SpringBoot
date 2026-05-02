package com.example.customer_order_app.service.impl;

import com.example.customer_order_app.entity.Customer;
import com.example.customer_order_app.exception.DuplicateEmailException;
import com.example.customer_order_app.exception.EntityNotFoundException;
import com.example.customer_order_app.repository.CustomerRepository;
import com.example.customer_order_app.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer createCustomer(Customer customer) {
        if (customerRepository.findByEmail(customer.getEmail()).isPresent()) {
            throw new DuplicateEmailException("Customer with email " + customer.getEmail() + " already exists");
        }
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id: " + id));
    }

    @Override
    public Customer updateCustomer(Long id, Customer customerDetails) {
        Customer customer = getCustomerById(id);

        // Check if email is being changed and if new email already exists
        if (!customer.getEmail().equals(customerDetails.getEmail()) &&
            customerRepository.findByEmail(customerDetails.getEmail()).isPresent()) {
            throw new DuplicateEmailException("Customer with email " + customerDetails.getEmail() + " already exists");
        }

        customer.setName(customerDetails.getName());
        customer.setEmail(customerDetails.getEmail());
        customer.setPhone(customerDetails.getPhone());

        return customerRepository.save(customer);
    }
}
