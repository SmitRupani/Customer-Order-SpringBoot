package com.example.customer_order_app.service.impl;

import com.example.customer_order_app.entity.Order;
import com.example.customer_order_app.exception.EntityNotFoundException;
import com.example.customer_order_app.repository.OrderRepository;
import com.example.customer_order_app.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with id: " + id));
    }

    @Override
    public Order updateOrder(Long id, Order orderDetails) {
        Order order = getOrderById(id);

        order.setOrderDate(orderDetails.getOrderDate());
        order.setTotalAmount(orderDetails.getTotalAmount());
        order.setStatus(orderDetails.getStatus());
        order.setCustomer(orderDetails.getCustomer());

        return orderRepository.save(order);
    }

    @Override
    public List<Object[]> getOrdersWithCustomerDetails() {
        return orderRepository.findOrdersWithCustomerDetails();
    }
}
