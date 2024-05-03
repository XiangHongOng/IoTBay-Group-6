<%@ page import="org.project.iotprojecttest.model.objects.User" %>
<%@ page import="org.project.iotprojecttest.model.dao.StaffDAO" %>
<%@ page import="org.project.iotprojecttest.model.dao.OrderDAO" %>
<%@ page import="org.project.iotprojecttest.model.dao.PaymentDAO" %>
<%@ page import="org.project.iotprojecttest.model.objects.Order" %>
<%@ page import="org.project.iotprojecttest.model.objects.Payment" %>
<%@ page import="java.util.List" %>
<html>
<head>
    <title>Create Payment</title>
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
    <div class="form-container">
        <h2>Create Payment</h2>

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

        <%
            int orderId = Integer.parseInt(request.getParameter("orderId"));
            OrderDAO orderDAO = new OrderDAO();
            PaymentDAO paymentDAO = new PaymentDAO();

            double orderTotalAmount = orderDAO.calculateOrderTotalAmount(orderId);
            List<Payment> payments = paymentDAO.getPaymentsByOrderId(orderId);

            double totalPaid = 0.0;
            for (Payment payment : payments) {
                totalPaid += payment.getAmount();
            }

            double remainingAmount = orderTotalAmount - totalPaid;
        %>

        <p>Order Total: <%= orderTotalAmount %></p>
        <p>Total Paid: <%= totalPaid %></p>
        <p>Remaining Amount: <%= remainingAmount %></p>

        <form action="createpayment" method="post" class="payment-form">
            <input type="hidden" name="orderId" value="<%= request.getParameter("orderId") %>">
            <div class="form-group">
                <label for="paymentMethod">Payment Method:</label>
                <select id="paymentMethod" name="paymentMethod" required>
                    <option value="">Select Payment Method</option>
                    <option value="Credit Card">Credit Card</option>
                    <option value="PayPal">PayPal</option>
                    <!-- Add more payment method options as needed -->
                </select>
            </div>
            <div class="form-group">
                <label for="creditCardDetails">Credit Card Details:</label>
                <input type="text" id="creditCardDetails" name="creditCardDetails" required>
            </div>
            <div class="form-group">
                <label for="amount">Amount:</label>
                <input type="number" id="amount" name="amount" step="0.01" required>
            </div>
            <div class="form-group">
                <label for="paymentDate">Payment Date:</label>
                <input type="date" id="paymentDate" name="paymentDate" required>
            </div>
            <div class="form-actions">
                <input type="submit" value="Save Payment" class="btn btn-primary">
            </div>
        </form>
    </div>
</main>

</body>
</html>