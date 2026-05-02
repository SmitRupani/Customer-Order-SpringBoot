<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Order List</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<div class="container">
    <div class="nav">
        <a href="/customers" class="btn btn-secondary">Customers</a>
        <a href="/orders" class="btn btn-primary">Orders</a>
        <a href="/orders/joined" class="btn btn-secondary">Orders + Customers</a>
    </div>

    <div class="card">
        <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 24px;">
            <h2>Order Management</h2>
            <a href="/orders/new" class="btn btn-primary">+ Add New Order</a>
        </div>

    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Order Date</th>
            <th>Total Amount</th>
            <th>Status</th>
            <th>Customer</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="order" items="${orders}">
            <tr>
                <td>${order.id}</td>
                <td>${order.orderDate}</td>
                <td>$${order.totalAmount}</td>
                <td>
                    <c:choose>
                        <c:when test="${order.status == 'Delivered'}">
                            <span class="badge badge-success">${order.status}</span>
                        </c:when>
                        <c:when test="${order.status == 'Shipped'}">
                            <span class="badge badge-info">${order.status}</span>
                        </c:when>
                        <c:otherwise>
                            <span class="badge badge-warning">${order.status}</span>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td>${order.customer.name}</td>
                <td>
                    <div class="actions">
                        <a href="/orders/edit/${order.id}" class="btn btn-warning" style="padding: 6px 12px;">Edit</a>
                    </div>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    </div>
</div>
</body>
</html>
