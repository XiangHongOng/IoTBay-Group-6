<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.project.iotprojecttest.model.User" %>
<html>
<head>
    <title>Operation Result</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
    <meta http-equiv="refresh" content="3;url=admin-servlet">
</head>
<body>
<div class="container">
    <h2>Result</h2>
    <%
        String message = (String) request.getAttribute("message");
        User user = (User) request.getAttribute("user");
        if (message != null) {
    %>
    <p><%= message %></p>
    <%
        }
        if (user != null) {
    %>
    <p>Username: <%= user.getUsername() %></p>
    <p>Email: <%= user.getEmail() %></p>
    <p>Admin Status: <%= user.isAdmin() ? "Admin" : "User" %></p>
    <%
        }
    %>
</div>
</body>
</html>
