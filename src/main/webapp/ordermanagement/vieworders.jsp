<%@ page import="org.project.iotprojecttest.model.objects.Order" %>
<%@ page import="java.util.List" %>
<%@ page import="org.project.iotprojecttest.model.objects.User" %>
<%@ page import="org.project.iotprojecttest.model.dao.StaffDAO" %>
<%@ page import="org.project.iotprojecttest.model.dao.CustomerDAO" %>
<%@ page import="org.project.iotprojecttest.model.objects.Customer" %>

<html>
<head>
    <title>View Orders</title>
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
                    <li><a href="createorder">Create Order</a></li>
                    <li><a href="vieworders">View Saved Orders</a></li>
                </ul>
            </li>
            <li class="dropdown">
                <a href="#">Payments</a>
                <ul class="dropdown-menu">
                    <li><a href="../payment/paymentmanagement">Payment Management</a></li>
                    <li><a href="../payment/paymenthistory">Payment History</a></li>
                </ul>
            </li>
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
        <h2>Order History</h2>

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

        <form action="vieworders" method="get" class="order-form">
            <% if (session.getAttribute("user") == null) { %>
            <div class="form-group">
                <label for="customerEmail">Customer Email:</label>
                <input type="email" id="customerEmail" name="customerEmail" required>
            </div>
            <% } %>
            <div class="form-group">
                <label for="orderId">Order ID:</label>
                <input type="text" id="orderId" name="orderId">
            </div>
            <div class="form-group">
                <label for="orderDate">Order Date:</label>
                <input type="date" id="orderDate" name="orderDate">
            </div>
            <div class="form-actions">
                <input type="submit" value="Search Orders" class="btn btn-primary">
            </div>
        </form>
    </div>

    <div class="order-table-wrapper">
        <% List<Order> orders = (List<Order>) request.getAttribute("orders"); %>
        <% if (orders != null && !orders.isEmpty()) { %>
        <table class="order-table-styled">
            <thead>
            <tr>
                <th>Order ID</th>
                <th>Order Date</th>
                <th>Customer</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <% for (Order order : orders) { %>
            <tr>
                <td><%= order.getOrderId() %></td>
                <td><%= order.getOrderDate() %></td>
                <td>
                    <%
                        CustomerDAO customerDAO = new CustomerDAO();
                        Customer customer = customerDAO.getCustomerByCustomerId(order.getCustomerId());
                        if (customer != null && customer.getUserId() == 0) {
                    %>
                    <%= customer.getEmail() %>
                    <% } else { %>
                    Registered Customer
                    <% } %>
                </td>
                <td class="action-buttons">
                    <% if (order.getStatus().equals("Saved")) { %>
                    <div class="button-group">
                        <a href="editorder?id=<%= order.getOrderId() %>" class="btn btn-primary btn-sm">Edit</a>
                        <a href="submitorder?id=<%= order.getOrderId() %>" class="btn btn-update btn-sm">Submit</a>
                        <a href="cancelorder?id=<%= order.getOrderId() %>" class="btn btn-cancel btn-sm">Cancel</a>
                    </div>
                    <% } else { %>
                    <span class="no-actions-message">No actions available</span>
                    <% } %>
                </td>
            </tr>
            <% } %>
            </tbody>
        </table>
        <% } else { %>
        <p class="no-orders-text">No orders found.</p>
        <% } %>
    </div>
</main>

</body>
</html>