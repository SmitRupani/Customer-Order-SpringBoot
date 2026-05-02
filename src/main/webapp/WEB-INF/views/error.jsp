<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Error</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<div class="container" style="max-width: 700px;">
    <div class="nav">
        <a href="/customers" class="btn btn-secondary">Customers</a>
        <a href="/orders" class="btn btn-secondary">Orders</a>
    </div>

    <div class="card">
        <h2 style="color: var(--danger); border-bottom-color: var(--danger);">Error</h2>
        <div class="alert alert-danger">
            <c:choose>
                <c:when test="${not empty errorMessage}">
                    ${errorMessage}
                </c:when>
                <c:otherwise>
                    An unexpected error occurred. Please try again.
                </c:otherwise>
            </c:choose>
        </div>
        <a href="/customers" class="btn btn-primary">Go to Customers</a>
        <a href="/orders" class="btn btn-secondary">Go to Orders</a>
    </div>
</div>
</body>
</html>
