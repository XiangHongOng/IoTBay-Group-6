<%@ page import="org.project.iotprojecttest.model.objects.User" %>
<%@ page import="org.project.iotprojecttest.model.dao.StaffDAO" %>
<%@ page import="org.project.iotprojecttest.model.objects.Order" %>
<%@ page import="org.project.iotprojecttest.model.objects.Product" %>
<%@ page import="org.project.iotprojecttest.model.objects.OrderLineItem" %>
<%@ page import="java.util.List" %>
<%@ page import="org.project.iotprojecttest.model.objects.Customer" %><%--
  Created by IntelliJ IDEA.
  User: camse
  Date: 28/04/2024
  Time: 8:01 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit Order</title>
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
        <h2>Edit Order</h2>
        <%
            Order order = (Order) request.getAttribute("order");
            List<Product> products = (List<Product>) request.getAttribute("products");
            List<OrderLineItem> orderLineItems = (List<OrderLineItem>) request.getAttribute("orderLineItems");
            Customer customer = (Customer) request.getAttribute("customer");
        %>
        <form action="editorder" method="post" class="order-form">
            <input type="hidden" name="orderId" value="<%= order.getOrderId() %>">

            <div class="form-group">
                <label for="productId">Product:</label>
                <select name="productId" id="productId" required>
                    <option value="">Select a product</option>
                    <%
                        for (Product product : products) {
                            boolean isSelected = false;
                            if (orderLineItems.size() > 0) {
                                isSelected = orderLineItems.get(0).getProductId() == product.getProductId();
                            }
                    %>
                    <option value="<%= product.getProductId() %>" <%= isSelected ? "selected" : "" %>>
                        <%= product.getProductName() %> (Stock: <%= product.getQuantity() %>)
                    </option>
                    <% } %>
                </select>
            </div>

            <div class="form-group">
                <label for="quantity">Quantity:</label>
                <input type="number" name="quantity" id="quantity" min="1" value="<%= orderLineItems.size() > 0 ? orderLineItems.get(0).getOrderedQuantity() : 1 %>" required>
            </div>

            <%
                if (customer != null && customer.getUserId() == 0) {
            %>
            <div class="form-group">
                <label for="customerEmail">Customer Email:</label>
                <input type="email" name="customerEmail" id="customerEmail" value="<%= customer.getEmail() %>" required>
            </div>
            <% } %>

            <div class="form-actions">
                <input type="submit" value="Update Order" class="btn btn-primary">
            </div>
        </form>
    </div>
</main>

</body>
</html>
