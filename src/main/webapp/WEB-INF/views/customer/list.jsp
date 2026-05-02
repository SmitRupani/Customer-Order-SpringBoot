<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Customer List</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<div class="container">
    <div class="nav">
        <a href="/customers" class="btn btn-primary">Customers</a>
        <a href="/orders" class="btn btn-secondary">Orders</a>
        <a href="/orders/joined" class="btn btn-secondary">Orders + Customers</a>
    </div>

    <div class="card">
        <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 24px;">
            <h2>Customer Management</h2>
            <a href="/customers/new" class="btn btn-primary">+ Add New Customer</a>
        </div>

    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Email</th>
            <th>Phone</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="customer" items="${customers}">
            <tr>
                <td>${customer.id}</td>
                <td>${customer.name}</td>
                <td>${customer.email}</td>
                <td>${customer.phone}</td>
                <td>
                    <div class="actions">
                        <a href="/customers/edit/${customer.id}" class="btn btn-warning" style="padding: 6px 12px;">Edit</a>
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
