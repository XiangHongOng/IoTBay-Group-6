<%@ page import="org.project.iotprojecttest.model.objects.Payment" %>
<%@ page import="java.util.List" %>
<%@ page import="org.project.iotprojecttest.model.objects.User" %>
<%@ page import="org.project.iotprojecttest.model.dao.StaffDAO" %>
<%@ page import="org.project.iotprojecttest.model.dao.CustomerDAO" %>
<%@ page import="org.project.iotprojecttest.model.objects.Customer" %>
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
    <div class="form-container">
        <h2>Payment History</h2>

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

        <form action="paymenthistory" method="get" class="payment-form">
            <% if (session.getAttribute("user") == null) { %>
            <label for="customerEmail">Customer Email:</label>
            <input type="email" id="customerEmail" name="customerEmail" required>

            <% } %>
            <label for="paymentId">Payment ID:</label>
            <input type="text" id="paymentId" name="paymentId">

            <label for="paymentDate">Payment Date:</label>
            <input type="date" id="paymentDate" name="paymentDate">

            <div class="form-actions">
                <input type="submit" value="Search Payments" class="btn btn-primary">
            </div>
        </form>
    </div>

    <div class="order-table-wrapper">
        <% List<Payment> payments = (List<Payment>) request.getAttribute("payments");
            CustomerDAO customerDAO = new CustomerDAO();
            OrderDAO orderDAO = new OrderDAO(); %>
        <% if (payments != null && !payments.isEmpty()) { %>
        <table class="order-table-styled">
            <thead>
            <tr>
                <th>Payment ID</th>
                <th>Payment Date</th>
                <th>Payment Method</th>
                <th>Amount</th>
                <th>Order ID</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <% for (Payment payment : payments) {
                Order order = orderDAO.getOrderById(payment.getOrderId());
                Customer customer = customerDAO.getCustomerByCustomerId(order.getCustomerId());
                String customerEmail = null;
                if (user != null) {
                    customerEmail = user.getEmail();
                } else if (customer != null && customer.getEmail() != null) {
                    customerEmail = customer.getEmail();
                }
            %>
            <tr>
                <td><%= payment.getPaymentId() %></td>
                <td><%= payment.getPaymentDate() %></td>
                <td><%= payment.getPaymentMethod() %></td>
                <td><%= payment.getAmount() %></td>
                <td><%= payment.getOrderId() %></td>
                <td class="action-buttons">
                    <% if (customerEmail != null) { %>
                    <form action="viewpayment" method="get" class="action-form">
                        <input type="hidden" name="id" value="<%= payment.getPaymentId() %>">
                        <input type="hidden" name="customerEmail" value="<%= customerEmail %>">
                        <input type="submit" value="View" class="btn-primary">
                    </form>
                    <% } %>
                </td>
            </tr>
            <% } %>
            </tbody>
        </table>
        <% } else { %>
        <p class="no-orders-text">No payments found.</p>
        <% } %>
    </div>
</main>

</body>
</html>