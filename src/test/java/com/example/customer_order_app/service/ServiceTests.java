package com.example.customer_order_app.service;

import com.example.customer_order_app.entity.Customer;
import com.example.customer_order_app.entity.Order;
import com.example.customer_order_app.exception.DuplicateEmailException;
import com.example.customer_order_app.exception.EntityNotFoundException;
import com.example.customer_order_app.repository.CustomerRepository;
import com.example.customer_order_app.repository.OrderRepository;
import com.example.customer_order_app.service.impl.CustomerServiceImpl;
import com.example.customer_order_app.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ServiceTests {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @InjectMocks
    private OrderServiceImpl orderService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    // ======================== Customer Service Tests ========================

    @Test
    public void testCreateCustomer_Success() {
        Customer customer = new Customer("New Customer", "new@example.com", "1234567890");
        when(customerRepository.findByEmail(customer.getEmail())).thenReturn(Optional.empty());
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        Customer created = customerService.createCustomer(customer);
        assertThat(created.getName()).isEqualTo("New Customer");
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    public void testCreateCustomer_DuplicateEmail() {
        Customer customer = new Customer("Dup Customer", "dup@example.com", "1234567890");
        when(customerRepository.findByEmail(customer.getEmail())).thenReturn(Optional.of(customer));

        assertThatThrownBy(() -> customerService.createCustomer(customer))
                .isInstanceOf(DuplicateEmailException.class)
                .hasMessageContaining("already exists");

        verify(customerRepository, never()).save(any(Customer.class));
    }

    @Test
    public void testGetCustomerById_NotFound() {
        when(customerRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> customerService.getCustomerById(99L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Customer not found");
    }

    @Test
    public void testUpdateCustomer() {
        Customer existing = new Customer("Old Name", "old@example.com", "1234567890");
        existing.setId(1L);

        Customer details = new Customer("New Name", "old@example.com", "0987654321");

        when(customerRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(customerRepository.save(any(Customer.class))).thenReturn(existing);

        Customer updated = customerService.updateCustomer(1L, details);
        assertThat(updated.getName()).isEqualTo("New Name");
        assertThat(updated.getPhone()).isEqualTo("0987654321");
    }

    // ======================== Order Service Tests ========================

    @Test
    public void testCreateOrder_Success() {
        Customer customer = new Customer("Order Cust", "ordercust@example.com", "1234567890");
        customer.setId(1L);
        Order order = new Order(LocalDate.of(2025, 5, 1), 250.00, "Pending", customer);

        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order created = orderService.createOrder(order);
        assertThat(created.getTotalAmount()).isEqualTo(250.00);
        assertThat(created.getStatus()).isEqualTo("Pending");
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    public void testGetOrderById_NotFound() {
        when(orderRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> orderService.getOrderById(99L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Order not found");
    }

    @Test
    public void testGetOrdersWithCustomerDetails() {
        Object[] row = new Object[]{1L, LocalDate.of(2025, 1, 1), 100.0, "Pending", 1L, "Test", "test@test.com", "1234567890"};
        when(orderRepository.findOrdersWithCustomerDetails()).thenReturn(Arrays.asList(row));

        List<Object[]> results = orderService.getOrdersWithCustomerDetails();
        assertThat(results).hasSize(1);
        assertThat(results.get(0)[5]).isEqualTo("Test");
    }

    @Test
    public void testUpdateOrder() {
        Customer customer = new Customer("Cust", "cust@example.com", "1234567890");
        customer.setId(1L);

        Order existing = new Order(LocalDate.of(2025, 1, 1), 100.0, "Pending", customer);
        existing.setId(1L);

        Order details = new Order(LocalDate.of(2025, 6, 1), 500.0, "Shipped", customer);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(orderRepository.save(any(Order.class))).thenReturn(existing);

        Order updated = orderService.updateOrder(1L, details);
        assertThat(updated.getTotalAmount()).isEqualTo(500.0);
        assertThat(updated.getStatus()).isEqualTo("Shipped");
    }
}
