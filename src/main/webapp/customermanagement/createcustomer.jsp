<%@ page import="org.project.iotprojecttest.model.objects.User" %>
<%@ page import="org.project.iotprojecttest.model.dao.StaffDAO" %><%--
  Created by IntelliJ IDEA.
  User: camse
  Date: 5/05/2024
  Time: 5:15 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create Customer</title>
    <link rel="stylesheet" type="text/css" href="../css/styles.css">
</head>
<body>

<header>
    <nav>
        <div class="logo">IoTBay</div>
        <ul class="nav-links">
            <li><a href="../index" class="<%= request.getRequestURI().endsWith("index.jsp") ? "active" : "" %>">Home</a></li>
            <li><a href="../product/products" class="<%= request.getRequestURI().endsWith("/products.jsp") ? "active" : "" %>">Products</a></li>
            <%
                User user = (User) session.getAttribute("user");
                StaffDAO staffDAO = new StaffDAO();
                if (user != null && staffDAO.isUserStaff(user.getUserId())) { %>
            <li><a href="../product/product-management" class="<%= request.getRequestURI().endsWith("/product-management.jsp") ? "active" : "" %>">Product Management</a></li>
            <% } %>
            <li class="dropdown">
                <a href="#">Orders</a>
                <ul class="dropdown-menu">
                    <li><a href="../order/createorder">Create Order</a></li>
                    <li><a href="../order/vieworders">View Saved Orders</a></li>
                </ul>
            </li>
            <li class="dropdown">
                <a href="#">Payments</a>
                <ul class="dropdown-menu">
                    <li><a href="../payment/paymentmanagement">Payment Management</a></li>
                    <li><a href="../payment/paymenthistory">Payment History</a></li>
                </ul>
            </li>
            <%
                if (user != null && staffDAO.isUserStaff(user.getUserId())) { %>
            <li class="dropdown">
                <a href="#">Account Management</a>
                <ul class="dropdown-menu">
                    <li><a href="customermanagement">Customer Management</a></li>
                </ul>
            </li>
            <% } %>
        </ul>
        <div class="user-actions">
            <% if (session.getAttribute("user") != null) { %>
            <a href="../account/accountdetails" class="<%= request.getRequestURI().endsWith("accountdetails.jsp") ? "active" : "" %>">Account</a>
            <a href="../logout-servlet">Logout</a>
            <% } else { %>
            <a href="../login">Login</a>
            <a href="../register">Register</a>
            <% } %>
        </div>
    </nav>
</header>

<main>
    <div class="form-container">
        <h2>Create Customer</h2>

        <form action="createcustomer" method="post" class="customer-form">
            <div class="form-group">
                <label for="fullName">Full Name:</label>
                <input type="text" id="fullName" name="fullName">
            </div>

            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" id="email" name="email">
            </div>

            <div class="form-group">
                <label for="customerType">Customer Type:</label>
                <select id="customerType" name="customerType">
                    <option value="individual">Individual</option>
                    <option value="company">Company</option>
                </select>
            </div>

            <div class="form-group">
                <label for="address">Address:</label>
                <input type="text" id="address" name="address">
            </div>

            <div class="form-actions">
                <input type="submit" value="Create" class="btn btn-primary">
            </div>
        </form>
    </div>
</main>

</body>
</html>
