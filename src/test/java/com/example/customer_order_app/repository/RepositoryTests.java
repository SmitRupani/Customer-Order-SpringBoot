package com.example.customer_order_app.repository;

import com.example.customer_order_app.entity.Customer;
import com.example.customer_order_app.entity.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class RepositoryTests {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void testSaveAndFindCustomer() {
        Customer customer = new Customer("Test User", "test@example.com", "1234567890");
        customer = customerRepository.save(customer);

        Optional<Customer> found = customerRepository.findById(customer.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Test User");
    }

    @Test
    public void testFindByEmail() {
        Customer customer = new Customer("Email User", "unique@example.com", "1234567890");
        customerRepository.save(customer);

        Optional<Customer> found = customerRepository.findByEmail("unique@example.com");
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Email User");
    }

    @Test
    public void testSaveAndFindOrder() {
        Customer customer = new Customer("Order Customer", "ordercust@example.com", "1234567890");
        customer = customerRepository.save(customer);

        Order order = new Order(LocalDate.of(2025, 6, 15), 199.99, "Pending", customer);
        order = orderRepository.save(order);

        Optional<Order> found = orderRepository.findById(order.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getTotalAmount()).isEqualTo(199.99);
        assertThat(found.get().getCustomer().getName()).isEqualTo("Order Customer");
    }

    @Test
    public void testFindOrdersByCustomerId() {
        Customer customer = new Customer("Multi Order", "multi@example.com", "1234567890");
        customer = customerRepository.save(customer);

        orderRepository.save(new Order(LocalDate.of(2025, 1, 1), 100.0, "Pending", customer));
        orderRepository.save(new Order(LocalDate.of(2025, 2, 1), 200.0, "Shipped", customer));

        List<Order> orders = orderRepository.findByCustomerId(customer.getId());
        assertThat(orders).hasSize(2);
    }

    @Test
    public void testFindOrdersWithCustomerDetails() {
        Customer customer = new Customer("Join Customer", "join@example.com", "1234567890");
        customer = customerRepository.save(customer);

        orderRepository.save(new Order(LocalDate.of(2025, 3, 10), 350.50, "Delivered", customer));

        List<Object[]> results = orderRepository.findOrdersWithCustomerDetails();
        assertThat(results).isNotEmpty();

        Object[] row = results.get(0);
        // row[5] = customer name
        assertThat(row[5]).isEqualTo("Join Customer");
        // row[6] = customer email
        assertThat(row[6]).isEqualTo("join@example.com");
    }
}
