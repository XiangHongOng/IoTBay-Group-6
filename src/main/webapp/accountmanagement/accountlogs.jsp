<%@ page import="org.project.iotprojecttest.model.objects.User" %>
<%@ page import="org.project.iotprojecttest.model.dao.UserAccessLogDAO" %>
<%@ page import="org.project.iotprojecttest.model.objects.UserAccessLog" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="org.project.iotprojecttest.model.dao.StaffDAO" %>

<html>
<head>
    <title>Access Logs</title>
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

    <div class="access-logs-container">
        <h2 class="access-logs-title">Access Logs</h2>
        <div class="form-container">
            <form action="accountlogs" method="get" class="search-form">
                <label for="searchDate">
                    <input type="date" id="searchDate" name="searchDate" value="<%= request.getParameter("searchDate") != null ? request.getParameter("searchDate") : "" %>">
                </label>
                <input type="submit" value="Search">
            </form>
        </div>

        <%
            if (user != null) {
                UserAccessLogDAO userAccessLogDAO = new UserAccessLogDAO();
                List<UserAccessLog> userAccessLogs;

                if (request.getParameter("searchDate") != null && !request.getParameter("searchDate").trim().isEmpty()) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    java.util.Date searchDate = dateFormat.parse(request.getParameter("searchDate"));
                    userAccessLogs = userAccessLogDAO.searchUserAccessLogsByDate(new java.sql.Date(searchDate.getTime()));
                } else {
                    userAccessLogs = userAccessLogDAO.getUserAccessLogsByUserId(user.getUserId());
                }

                if (!userAccessLogs.isEmpty()) {
        %>
        <div class="access-logs-table-container">
            <table class="access-logs-table">
                <tr>
                    <th>Log ID</th>
                    <th>Login DateTime</th>
                    <th>Logout DateTime</th>
                </tr>
                <%
                    for (UserAccessLog userAccessLog : userAccessLogs) {
                %>
                <tr>
                    <td><%= userAccessLog.getLogId() %></td>
                    <td><%= userAccessLog.getLoginDateTime() != null ? userAccessLog.getLoginDateTime() : "Invalid" %></td>
                    <td><%= userAccessLog.getLogoutDateTime() != null ? userAccessLog.getLogoutDateTime() : "Invalid" %></td>
                </tr>
                <%
                    }
                %>
            </table>
        </div>
        <%
        } else {
        %>
        <div class="message-container">
            <p class="error-message">No access logs found.</p>
        </div>
        <%
            }
        } else {
        %>
        <p>You are not logged in.</p>
        <%
            }
        %>
    </div>
</main>
</body>
</html>