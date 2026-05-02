<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add Order</title>
    <link rel="stylesheet" href="/css/style.css">
</head>

<body>
<div class="container" style="max-width: 700px;">
    <div class="nav">
        <a href="/customers" class="btn btn-secondary">Customers</a>
        <a href="/orders" class="btn btn-secondary">Orders</a>
    </div>

    <div class="card">
        <h2>Add New Order</h2>

        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger">${errorMessage}</div>
        </c:if>

    <form:form action="/orders/save" method="post" modelAttribute="order">
        <div class="form-group">
            <label>Order Date:</label>
            <form:input path="orderDate" type="date" />
            <form:errors path="orderDate" cssClass="error-msg" />
        </div>
        <div class="form-group">
            <label>Total Amount ($):</label>
            <form:input path="totalAmount" type="number" step="0.01" />
            <form:errors path="totalAmount" cssClass="error-msg" />
        </div>
        <div class="form-group">
            <label>Status:</label>
            <form:select path="status">
                <form:option value="" label="-- Select Status --" />
                <form:option value="Pending" label="Pending" />
                <form:option value="Shipped" label="Shipped" />
                <form:option value="Delivered" label="Delivered" />
            </form:select>
            <form:errors path="status" cssClass="error-msg" />
        </div>
        <div class="form-group">
            <label>Customer:</label>
            <form:select path="customer.id">
                <form:option value="" label="-- Select Customer --" />
                <c:forEach var="cust" items="${customers}">
                    <form:option value="${cust.id}" label="${cust.name} (${cust.email})" />
                </c:forEach>
            </form:select>
            <form:errors path="customer" cssClass="error-msg" />
        </div>
        <div class="form-group" style="margin-top: 32px;">
            <button type="submit" class="btn btn-primary">Save Order</button>
            <a href="/orders" class="btn btn-secondary">Cancel</a>
        </div>
    </form:form>
    </div>
</div>
</body>
</html>
