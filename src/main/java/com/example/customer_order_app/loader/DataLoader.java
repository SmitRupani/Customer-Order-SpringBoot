package com.example.customer_order_app.loader;

import com.example.customer_order_app.entity.Customer;
import com.example.customer_order_app.entity.Order;
import com.example.customer_order_app.repository.CustomerRepository;
import com.example.customer_order_app.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public DataLoader(CustomerRepository customerRepository, OrderRepository orderRepository) {
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (customerRepository.count() > 0 || orderRepository.count() > 0) {
            return; // Data already loaded
        }

        // Create Customers
        List<Customer> customers = Arrays.asList(
                new Customer("John Doe",      "john@example.com",      "9876543210"),
                new Customer("Jane Smith",    "jane@example.com",      "9876543211"),
                new Customer("Bob Johnson",   "bob@example.com",       "9876543212"),
                new Customer("Alice Williams","alice@example.com",     "9876543213"),
                new Customer("Charlie Brown", "charlie@example.com",   "9876543214"),
                new Customer("Diana Prince",  "diana@example.com",     "9876543215"),
                new Customer("Edward Norton", "edward@example.com",    "9876543216"),
                new Customer("Fiona Apple",   "fiona@example.com",     "9876543217"),
                new Customer("George Lucas",  "george@example.com",    "9876543218"),
                new Customer("Helen Mirren",  "helen@example.com",     "9876543219")
        );
        customerRepository.saveAll(customers);

        // Retrieve saved customers to attach orders
        Customer john    = customerRepository.findByEmail("john@example.com").get();
        Customer jane    = customerRepository.findByEmail("jane@example.com").get();
        Customer bob     = customerRepository.findByEmail("bob@example.com").get();
        Customer alice   = customerRepository.findByEmail("alice@example.com").get();
        Customer charlie = customerRepository.findByEmail("charlie@example.com").get();
        Customer diana   = customerRepository.findByEmail("diana@example.com").get();
        Customer edward  = customerRepository.findByEmail("edward@example.com").get();
        Customer fiona   = customerRepository.findByEmail("fiona@example.com").get();
        Customer george  = customerRepository.findByEmail("george@example.com").get();
        Customer helen   = customerRepository.findByEmail("helen@example.com").get();

        // Create Orders
        List<Order> orders = Arrays.asList(
                new Order(LocalDate.of(2025, 1, 15), 250.00,  "Delivered",  john),
                new Order(LocalDate.of(2025, 2, 20), 450.50,  "Shipped",    jane),
                new Order(LocalDate.of(2025, 3, 10), 120.75,  "Pending",    bob),
                new Order(LocalDate.of(2025, 3, 25), 890.00,  "Delivered",  alice),
                new Order(LocalDate.of(2025, 4,  5), 340.25,  "Shipped",    charlie),
                new Order(LocalDate.of(2025, 4, 18), 175.00,  "Pending",    diana),
                new Order(LocalDate.of(2025, 5,  1), 560.80,  "Delivered",  edward),
                new Order(LocalDate.of(2025, 5, 12), 230.40,  "Shipped",    fiona),
                new Order(LocalDate.of(2025, 6,  8), 710.00,  "Pending",    george),
                new Order(LocalDate.of(2025, 6, 22), 995.99,  "Delivered",  helen)
        );
        orderRepository.saveAll(orders);

        System.out.println("Sample data loaded successfully!");
    }
}
