<%@ page import="org.project.iotprojecttest.model.objects.User" %>
<%@ page import="org.project.iotprojecttest.model.dao.StaffDAO" %>
<%@ page import="org.project.iotprojecttest.model.objects.Customer" %>
<%@ page import="org.project.iotprojecttest.model.dao.UserDAO" %><%--
  Created by IntelliJ IDEA.
  User: camse
  Date: 5/05/2024
  Time: 5:15 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>View Customer</title>
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
        <h2>View Customer</h2>

        <%
            String successMessage = (String) request.getAttribute("successMessage");
            if (successMessage != null) {
        %>
        <div class="message-container">
            <p class="success-message"><%= successMessage %></p>
        </div>
        <%
            }
        %>

        <%
            String error = (String) request.getAttribute("errorMessage");
            if (error != null) {
        %>
        <p class="error-message"><%= error %></p>
        <%
            }
        %>

        <% Customer customer = (Customer) request.getAttribute("customer");
            UserDAO userDAO = new UserDAO();

            User userObject = userDAO.getUserById(customer.getUserId());
        %>

        <form action="viewcustomer" method="post" class="customer-form">
            <input type="hidden" name="customerId" value="<%= customer.getCustomerId() %>">

            <div class="form-group">
                <label for="fullName">Full Name:</label>
                <input type="text" id="fullName" name="fullName" value="<%= customer.getFullName() %>">
            </div>

            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" value="<%= customer.getEmail() != null ? customer.getEmail() : userObject.getEmail()%>">
            </div>

            <div class="form-group">
                <label for="phone">Phone:</label>
                <input type="text" id="phone" name="phone" value="<%= userObject != null ? userObject.getPhone() : "" %>">
            </div>

            <div class="form-group">
                <label for="customerType">Customer Type:</label>
                <select id="customerType" name="customerType">
                    <option value="individual" <%= customer.getCustomerType().equals("individual") ? "selected" : "" %>>Individual</option>
                    <option value="company" <%= customer.getCustomerType().equals("company") ? "selected" : "" %>>Company</option>
                </select>
            </div>

            <div class="form-group">
                <label for="address">Address:</label>
                <input type="text" id="address" name="address" value="<%= customer.getAddress() != null ? customer.getAddress() : "" %>">
            </div>

            <div class="form-group">
                <label for="isActive">Active:</label>
                <input type="checkbox" id="isActive" name="isActive" <%= customer.getActive() ? "checked" : "" %>>
            </div>

            <div class="form-actions centered-actions">
                <form action="updatecustomer" method="post" class="action-form">
                    <input type="hidden" name="id" value="<%= customer.getCustomerId() %>">
                    <input type="submit" value="Update" class="btn-add">
                </form>

                <form action="deletecustomer" method="post" class="action-form">
                    <input type="hidden" name="customerId" value="<%= customer.getCustomerId() %>">
                    <input type="submit" value="Delete" class="btn-cancel">
                </form>
            </div>
        </form>
    </div>
</main>

</body>
</html>
