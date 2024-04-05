<%@ page import="org.project.iotprojecttest.model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Welcome</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>
<div class="container">
    <%
        User user = (User) session.getAttribute("user");
        if (user != null) {
    %>
    <h1>Welcome, ${user.username}!</h1>
    <p>Your Email is ${user.email}.</p>
    <p>Your password is ${user.password}.</p>
    <a href='main.jsp' class='button welcome-button'>Click here to proceed to the main page.</a>
    <%
        } else {
            response.sendRedirect("login.jsp");
        }
    %>
</div>
</body>
</html>
