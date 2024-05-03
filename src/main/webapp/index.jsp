<%@ page import="org.project.iotprojecttest.model.objects.User" %>
<%@ page import="org.project.iotprojecttest.model.dao.StaffDAO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Welcome to IoTBay</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>
<header>
    <nav>
        <div class="logo">IoTBay</div>
        <ul class="nav-links">
            <li><a href="index" class="<%= request.getRequestURI().endsWith("index.jsp") ? "active" : "" %>">Home</a></li>
            <li><a href="product/products" class="<%= request.getRequestURI().endsWith("/products.jsp") ? "active" : "" %>">Products</a></li>
            <%
                User user = (User) session.getAttribute("user");
                StaffDAO staffDAO = new StaffDAO();
                if (user != null && staffDAO.isUserStaff(user.getUserId())) { %>
            <li><a href="product/product-management" class="<%= request.getRequestURI().endsWith("/product-management.jsp") ? "active" : "" %>">Product Management</a></li>
            <% } %>
            <li class="dropdown">
                <a href="#">Orders</a>
                <ul class="dropdown-menu">
                    <li><a href="order/createorder">Create Order</a></li>
                    <li><a href="order/vieworders">View Saved Orders</a></li>
                </ul>
            </li>
            <li class="dropdown">
                <a href="#">Payments</a>
                <ul class="dropdown-menu">
                    <li><a href="payment/paymentmanagement">Payment Management</a></li>
                    <li><a href="payment/paymenthistory">Payment History</a></li>
                </ul>
            </li>
        </ul>
        <div class="user-actions">
            <% if (session.getAttribute("user") != null) { %>
            <a href="account/accountdetails" class="<%= request.getRequestURI().endsWith("accountdetails.jsp") ? "active" : "" %>">Account</a>
            <a href="logout-servlet">Logout</a>
            <% } else { %>
            <a href="login">Login</a>
            <a href="register">Register</a>
            <% } %>
        </div>
    </nav>
</header>

<main>
    <section class="hero">
        <h1>Welcome to IoTBay</h1>
        <p>Discover the world of IoT devices.</p>
        <%
            String errorMessage = (String) session.getAttribute("errorMessage");
            if (errorMessage != null) {
        %>
        <div class="message-container">
            <p class="error-message"><%= errorMessage %></p>
        </div>
        <% session.removeAttribute("errorMessage"); %>
        <%
            }
        %>
        <%
            String successMessage = (String) session.getAttribute("successMessage");
            if (successMessage != null) {
        %>
        <div class="message-container">
            <p class="success-message"><%= successMessage %></p>
        </div>
        <% session.removeAttribute("successMessage"); %>
        <%
            }
        %>
    </section>
</main>


</body>
</html>