<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login Page</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>
<div class="container">
    <div class="content">
        <h1>Login</h1>
        <% if (request.getAttribute("errorMessage") != null) { %>
        <p style="color: red;">${errorMessage}</p>
        <% } %>
        <form action="login-servlet" method="post">
            <input type="text" id="username" name="username" placeholder="Username" required><br>
            <input type="password" id="password" name="password" placeholder="Password" required><br>
            <input type="submit" value="Login">
        </form>
        <a href="index.jsp"><button type="button">Back to Home</button></a>
    </div>
</div>
</body>
</html>