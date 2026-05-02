<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Update Customer</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<div class="container" style="max-width: 700px;">
    <div class="nav">
        <a href="/customers" class="btn btn-secondary">Customers</a>
        <a href="/orders" class="btn btn-secondary">Orders</a>
    </div>

    <div class="card">
    <h2>Update Customer</h2>

    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger">${errorMessage}</div>
    </c:if>

    <form:form action="/customers/update/${customer.id}" method="post" modelAttribute="customer">
        <div class="form-group">
            <label>Name:</label>
            <form:input path="name" />
            <form:errors path="name" cssClass="error-msg" />
        </div>
        <div class="form-group">
            <label>Email:</label>
            <form:input path="email" type="email" />
            <form:errors path="email" cssClass="error-msg" />
        </div>
        <div class="form-group">
            <label>Phone:</label>
            <form:input path="phone" />
            <form:errors path="phone" cssClass="error-msg" />
        </div>
        <div class="form-group" style="margin-top: 32px;">
            <button type="submit" class="btn btn-warning">Update Customer</button>
            <a href="/customers" class="btn btn-secondary">Cancel</a>
        </div>
    </form:form>
    </div>
</div>
</body>
</html>
