<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.project.iotprojecttest.model.User" %>
<html>
<head>
    <title>Main Page</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>
<div class="container">
    <%
        User user = (User) session.getAttribute("user");
        if (user != null) {
    %>
    <h1>Main Page - Welcome, ${user.username}!</h1>
    <div class="button-container">
        <a href="logout-servlet" class="button">Logout</a>
        <a href="#" class="button">Todo</a>
    </div>
    <%
        } else {
            response.sendRedirect("login.jsp");
        }
    %>
</div>
</body>
</html>
