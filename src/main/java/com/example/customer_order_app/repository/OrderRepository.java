package com.example.customer_order_app.repository;

import com.example.customer_order_app.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByCustomerId(Long customerId);

    /**
     * Custom INNER JOIN query to fetch orders along with customer details.
     * Returns Object[] where:
     *   [0] = Order id, [1] = orderDate, [2] = totalAmount, [3] = status,
     *   [4] = Customer id, [5] = Customer name, [6] = Customer email, [7] = Customer phone
     */
    @Query("SELECT o.id, o.orderDate, o.totalAmount, o.status, " +
           "c.id, c.name, c.email, c.phone " +
           "FROM Order o INNER JOIN o.customer c")
    List<Object[]> findOrdersWithCustomerDetails();
}
