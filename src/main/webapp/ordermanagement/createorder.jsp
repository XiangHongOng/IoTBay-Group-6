<%@ page import="org.project.iotprojecttest.model.objects.User" %>
<%@ page import="org.project.iotprojecttest.model.dao.StaffDAO" %>
<%@ page import="org.project.iotprojecttest.model.dao.ProductDAO" %>
<%@ page import="org.project.iotprojecttest.model.objects.Product" %>
<%@ page import="java.util.List" %>

<html>
<head>
    <title>Create Order</title>
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
            <%
                if (user != null && staffDAO.isUserStaff(user.getUserId())) { %>
            <li class="dropdown">
                <a href="#">Account Management</a>
                <ul class="dropdown-menu">
                    <li><a href="../staff/customermanagement">Customer Management</a></li>
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
    <div class="product-management-container">
        <h2>Create Order</h2>

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

        <form action="createorder" method="post">
            <label for="productId">Product:</label>
            <select name="productId" id="productId" required>
                <option value="">Select a product</option>
                <%
                    ProductDAO productDAO = new ProductDAO();
                    List<Product> products = productDAO.getAllProducts();
                    for (Product product : products) {
                %>
                <option value="<%= product.getProductId() %>">
                    <%= product.getProductName() %> (Stock: <%= product.getQuantity() %>)
                </option>
                <% } %>
            </select>

            <label for="quantity">Quantity:</label>
            <input type="number" name="quantity" id="quantity" min="1" required>

            <% if (user == null) { %>
            <label for="customerName">Your Name:</label>
            <input type="text" name="customerName" id="customerName" required>

            <label for="customerEmail">Your Email:</label>
            <input type="email" name="customerEmail" id="customerEmail" required>
            <% } %>

            <div class="form-actions centered-actions">
                <input type="submit" value="Save Order" class="btn-add">
            </div>
        </form>
    </div>
</main>


</body>
</html>