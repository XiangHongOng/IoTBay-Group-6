<%@ page import="org.project.iotprojecttest.model.objects.Payment" %>
<%@ page import="java.util.List" %>
<%@ page import="org.project.iotprojecttest.model.objects.User" %>
<%@ page import="org.project.iotprojecttest.model.dao.StaffDAO" %>
<%@ page import="org.project.iotprojecttest.model.dao.CustomerDAO" %>
<%@ page import="org.project.iotprojecttest.model.objects.Customer" %>
<%@ page import="org.project.iotprojecttest.model.dao.PaymentDAO" %>
<%@ page import="org.project.iotprojecttest.model.dao.OrderDAO" %>
<%@ page import="org.project.iotprojecttest.model.objects.Order" %>

<html>
<head>
    <title>Payment History</title>
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
                    <li><a href="paymentmanagement">Payment Management</a></li>
                    <li><a href="paymenthistory">Payment History</a></li>
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
    <%
        int paymentId = Integer.parseInt(request.getParameter("id"));
        PaymentDAO paymentDAO = new PaymentDAO();
        Payment payment = paymentDAO.getPaymentById(paymentId);

        OrderDAO orderDAO = new OrderDAO();
        Order order = orderDAO.getOrderById(payment.getOrderId());
    %>

    <div class="form-container">
        <h2>Payment Details</h2>

        <div class="payment-info">
            <div class="payment-field">
                <span class="field-label">Payment ID:</span>
                <span class="field-value"><%= payment.getPaymentId() %></span>
            </div>
            <div class="payment-field">
                <span class="field-label">Payment Date:</span>
                <span class="field-value"><%= payment.getPaymentDate() %></span>
            </div>
            <div class="payment-field">
                <span class="field-label">Payment Method:</span>
                <span class="field-value"><%= payment.getPaymentMethod() %></span>
            </div>
            <div class="payment-field">
                <span class="field-label">Credit Card Details:</span>
                <span class="field-value"><%= payment.getCreditCardDetails() %></span>
            </div>
            <div class="payment-field">
                <span class="field-label">Amount:</span>
                <span class="field-value"><%= payment.getAmount() %></span>
            </div>
        </div>

        <h3>Order Details</h3>

        <div class="order-info">
            <div class="order-field">
                <span class="field-label">Order ID:</span>
                <span class="field-value"><%= order.getOrderId() %></span>
            </div>
            <div class="order-field">
                <span class="field-label">Order Date:</span>
                <span class="field-value"><%= order.getOrderDate() %></span>
            </div>
            <div class="order-field">
                <span class="field-label">Status:</span>
                <span class="field-value"><%= order.getStatus() %></span>
            </div>
        </div>
    </div>
</main>

</body>
</html>