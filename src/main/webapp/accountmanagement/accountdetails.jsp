<%@ page import="org.project.iotprojecttest.model.objects.User" %>
<%@ page import="org.project.iotprojecttest.model.dao.UserDAO" %>
<%@ page import="java.util.List" %>
<%@ page import="org.project.iotprojecttest.model.objects.UserAccessLog" %>
<%@ page import="org.project.iotprojecttest.model.dao.UserAccessLogDAO" %>
<%@ page import="org.project.iotprojecttest.model.dao.StaffDAO" %>
<%@ page import="org.project.iotprojecttest.model.dao.CustomerDAO" %>
<%@ page import="org.project.iotprojecttest.model.objects.Customer" %>

<html>
<head>
    <title>Account</title>
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
                    <li><a href="../staff/customermanagement">Customer Management</a></li>
                </ul>
            </li>
            <% } %>
        </ul>
        <div class="user-actions">
            <% if (session.getAttribute("user") != null) { %>
            <a href="accountdetails" class="<%= request.getRequestURI().endsWith("accountdetails.jsp") ? "active" : "" %>">Account</a>
            <a href="../logout-servlet">Logout</a>
            <% } else { %>
            <a href="../login">Login</a>
            <a href="../register">Register</a>
            <% } %>
        </div>
    </nav>
</header>

<main>
    <ul class="account-nav">
        <li><a href="accountdetails" class="<%= request.getRequestURI().endsWith("accountdetails.jsp") ? "active" : "" %>">Account Details</a></li>
        <li><a href="accountupdate" class="<%= request.getRequestURI().endsWith("account-update-details.jsp") ? "active" : "" %>">Update Details</a></li>
        <li><a href="accountdelete" class="<%= request.getRequestURI().endsWith("account-delete.jsp") ? "active" : "" %>">Delete Account</a></li>
        <li><a href="accountlogs" class="<%= request.getRequestURI().endsWith("account-access-log.jsp") ? "active" : "" %>">Access Logs</a></li>
    </ul>
    <div>
        <div class="form-container">
            <h2>Account Details</h2>
            <%
                if (user != null) {
                    CustomerDAO customerDAO = new CustomerDAO();
                    Customer customer = customerDAO.getCustomerByUserId(user.getUserId());
            %>
            <ul class="account-details">
                <li><strong>Full Name:</strong> <%= customer != null ? customer.getFullName() : "Not set"%></li>
                <li><strong>Email:</strong> <%= user.getEmail() %></li>
                <li><strong>Address:</strong> <%= customer != null ? customer.getAddress() : "Not set" %></li>
                <li><strong>Phone:</strong> <%= user.getPhone() %></li>
            </ul>
            <% } else { %>
            <p>You are not logged in.</p>
            <% } %>
        </div>
    </div>
</main>
</body>
</html>