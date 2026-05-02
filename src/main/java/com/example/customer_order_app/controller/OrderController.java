package com.example.customer_order_app.controller;

import com.example.customer_order_app.entity.Order;
import com.example.customer_order_app.service.CustomerService;
import com.example.customer_order_app.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final CustomerService customerService;

    @Autowired
    public OrderController(OrderService orderService, CustomerService customerService) {
        this.orderService = orderService;
        this.customerService = customerService;
    }

    @GetMapping
    public String listOrders(Model model) {
        model.addAttribute("orders", orderService.getAllOrders());
        return "order/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("order", new Order());
        model.addAttribute("customers", customerService.getAllCustomers());
        return "order/form";
    }

    @PostMapping("/save")
    public String saveOrder(@Valid @ModelAttribute("order") Order order, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("customers", customerService.getAllCustomers());
            return "order/form";
        }
        try {
            orderService.createOrder(order);
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("customers", customerService.getAllCustomers());
            return "order/form";
        }
        return "redirect:/orders";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        Order order = orderService.getOrderById(id);
        model.addAttribute("order", order);
        model.addAttribute("customers", customerService.getAllCustomers());
        return "order/update";
    }

    @PostMapping("/update/{id}")
    public String updateOrder(@PathVariable Long id, @Valid @ModelAttribute("order") Order order, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("customers", customerService.getAllCustomers());
            return "order/update";
        }
        try {
            orderService.updateOrder(id, order);
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("customers", customerService.getAllCustomers());
            return "order/update";
        }
        return "redirect:/orders";
    }

    @GetMapping("/joined")
    public String viewJoinedData(Model model) {
        model.addAttribute("joinedData", orderService.getOrdersWithCustomerDetails());
        return "order/joined";
    }
}
