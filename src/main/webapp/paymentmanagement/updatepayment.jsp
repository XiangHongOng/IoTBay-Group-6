<%@ page import="org.project.iotprojecttest.model.objects.User" %>
<%@ page import="org.project.iotprojecttest.model.dao.StaffDAO" %>
<%@ page import="org.project.iotprojecttest.model.dao.PaymentDAO" %>
<%@ page import="org.project.iotprojecttest.model.objects.Payment" %>
<%@ page import="org.project.iotprojecttest.model.dao.OrderDAO" %>
<%@ page import="org.project.iotprojecttest.model.objects.Order" %>
<%@ page import="java.util.List" %>
<html>
<head>
    <title>Update Payment</title>
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
        <h2>View Payment</h2>

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
            int paymentId = (int) request.getAttribute("paymentId");
            PaymentDAO paymentDAO = new PaymentDAO();
            Payment payment = paymentDAO.getPaymentById(paymentId);

            OrderDAO orderDAO = new OrderDAO();
            Order order = orderDAO.getOrderById(payment.getOrderId());

            double orderTotalAmount = orderDAO.calculateOrderTotalAmount(order.getOrderId());
            double totalPaid = paymentDAO.getTotalPaidAmountByOrderId(order.getOrderId());

            double remainingAmount = orderTotalAmount - totalPaid;
        %>

        <p>Order Total: <%= orderTotalAmount %></p>
        <p>Total Paid: <%= totalPaid %></p>
        <p>Remaining Amount: <%= remainingAmount %></p>

        <form action="updatepayment" method="post" class="payment-form">
            <input type="hidden" name="paymentId" value="<%= request.getAttribute("paymentId") %>">
            <div class="form-group">
                <label for="paymentMethod">Payment Method:</label>
                <select id="paymentMethod" name="paymentMethod" required>
                    <option value="Credit Card" <%= request.getAttribute("paymentMethod").equals("Credit Card") ? "selected" : "" %>>Credit Card</option>
                    <option value="PayPal" <%= request.getAttribute("paymentMethod").equals("PayPal") ? "selected" : "" %>>PayPal</option>
                </select>
            </div>
            <div class="form-group">
                <label for="creditCardDetails">Credit Card Details:</label>
                <input type="text" id="creditCardDetails" name="creditCardDetails" value="<%= request.getAttribute("creditCardDetails") %>" required>
            </div>
            <div class="form-group">
                <label for="amount">Amount:</label>
                <input type="number" id="amount" name="amount" step="0.01" value="<%= request.getAttribute("amount") %>" required>
            </div>
            <div class="form-group">
                <label for="paymentDate">Payment Date:</label>
                <input type="date" id="paymentDate" name="paymentDate" value="<%= request.getAttribute("paymentDate") %>" required>
            </div>
            <div class="form-actions">
                <input type="submit" value="Update Payment" class="btn btn-primary">
                <a href="deletepayment?paymentId=<%= request.getAttribute("paymentId") %>" class="btn btn-danger">Delete Payment</a>
                <a href="submitpayment?paymentId=<%= request.getAttribute("paymentId") %>" class="btn btn-success">Submit Payment</a>
            </div>
        </form>
    </div>
</main>

</body>
</html>