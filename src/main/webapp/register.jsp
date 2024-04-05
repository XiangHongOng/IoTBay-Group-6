<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration Page</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>
<div class="container">
    <h1>Registration</h1>
    <% if (request.getAttribute("errorMessage") != null) { %>
    <p class="error-message">${errorMessage}</p>
    <% } %>
    <form action="register-servlet" method="post">
        <input type="text" id="username" name="username" placeholder="Username" required><br>
        <input type="password" id="password" name="password" placeholder="Password" required><br>
        <input type="email" id="email" name="email" placeholder="Email" required><br>
        <input type="submit" class="button" value="Register">
    </form>
    <a href="index.jsp" class="button">Back to Home</a>
</div>
</body>
</html>
