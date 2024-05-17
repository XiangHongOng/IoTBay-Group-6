<%@ page import="org.project.iotprojecttest.model.objects.User" %>
<%@ page import="org.project.iotprojecttest.model.dao.StaffDAO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration Page</title>
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
    <section class="register-container">
        <h1>Registration</h1>
        <% if (request.getAttribute("errorMessage") != null) { %>
        <div class="message-container">
            <p class="error-message"><%= request.getAttribute("errorMessage") %></p>
        </div>
        <% } %>
        <form action="register" method="post">
            <input type="text" id="fullName" name="fullName" placeholder="Full Name" required><br>
            <input type="email" id="email" name="email" placeholder="Email" required><br>
            <input type="password" id="password" name="password" placeholder="Password" required><br>
            <input type="text" id="address" name="address" placeholder="Address" required><br>
            <input type="text" id="phone" name="phone" placeholder="Phone" required><br>
            <div class="form-group">
                <label for="customerType">Customer Type:</label>
                <select id="customerType" name="customerType" required>
                    <option value="">Select Customer Type</option>
                    <option value="individual">Individual</option>
                    <option value="company">Company</option>
                </select>
            </div>
            <input type="submit" class="button" value="Register">
        </form>
        <p class="login-link">Already have an account? <a href="login">Login here</a></p>
    </section>
</main>


</body>
</html>