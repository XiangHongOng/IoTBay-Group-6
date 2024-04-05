<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.project.iotprojecttest.model.User" %>
<html>
<head>
    <title>Main Page</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css"> <!-- Ensure the path is correct -->
</head>
<body>
<div class="container">
    <%
        User user = (User) session.getAttribute("user");
        if (user != null) {
    %>
    <h1>Main Page - Welcome, <%= user.getUsername() %>!</h1> <!-- Updated for consistent scriptlet usage -->
    <div class="button-container">
        <% if (user.isAdmin()) { %>
        <a href="admin-servlet" class="button">Admin Panel</a>
        <% } %>
        <a href="logout-servlet" class="button">Logout</a>
    </div>
    <%
        } else {
            response.sendRedirect("login.jsp");
        }
    %>
</div>
</body>
</html>
