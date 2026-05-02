package com.example.customer_order_app.controller;

import com.example.customer_order_app.entity.Customer;
import com.example.customer_order_app.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public String listCustomers(Model model) {
        model.addAttribute("customers", customerService.getAllCustomers());
        return "customer/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "customer/form";
    }

    @PostMapping("/save")
    public String saveCustomer(@Valid @ModelAttribute("customer") Customer customer, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "customer/form";
        }
        try {
            customerService.createCustomer(customer);
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "customer/form";
        }
        return "redirect:/customers";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        Customer customer = customerService.getCustomerById(id);
        model.addAttribute("customer", customer);
        return "customer/update";
    }

    @PostMapping("/update/{id}")
    public String updateCustomer(@PathVariable Long id, @Valid @ModelAttribute("customer") Customer customer, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "customer/update";
        }
        try {
            customerService.updateCustomer(id, customer);
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "customer/update";
        }
        return "redirect:/customers";
    }
}
