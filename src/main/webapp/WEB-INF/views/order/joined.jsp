<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Orders with Customer Details</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<div class="container">
    <div class="nav">
        <a href="/customers" class="btn btn-secondary">Customers</a>
        <a href="/orders" class="btn btn-secondary">Orders</a>
        <a href="/orders/joined" class="btn btn-primary">Orders + Customers</a>
    </div>

    <div class="card">
        <h2>Orders with Customer Details (INNER JOIN)</h2>

    <table>
        <thead>
        <tr>
            <th>Order ID</th>
            <th>Order Date</th>
            <th>Total Amount</th>
            <th>Status</th>
            <th>Customer ID</th>
            <th>Customer Name</th>
            <th>Customer Email</th>
            <th>Customer Phone</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="row" items="${joinedData}">
            <tr>
                <td>${row[0]}</td>
                <td>${row[1]}</td>
                <td>$${row[2]}</td>
                <td>
                    <c:choose>
                        <c:when test="${row[3] == 'Delivered'}">
                            <span class="badge badge-success">${row[3]}</span>
                        </c:when>
                        <c:when test="${row[3] == 'Shipped'}">
                            <span class="badge badge-info">${row[3]}</span>
                        </c:when>
                        <c:otherwise>
                            <span class="badge badge-warning">${row[3]}</span>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td>${row[4]}</td>
                <td>${row[5]}</td>
                <td>${row[6]}</td>
                <td>${row[7]}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    </div>
</div>
</body>
</html>
