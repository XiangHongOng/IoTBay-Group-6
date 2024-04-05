<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="org.project.iotprojecttest.model.User" %>
<html>
<head>
    <title>Admin Panel</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>
<div class="container table-container">
    <h1>Admin Panel</h1>
    <%
        List<User> users = (List<User>) request.getAttribute("users");
    %>
    <form action="user-management-servlet" method="post">
        <table class="styled-table">
            <thead>
            <tr>
                <th>ID</th>
                <th>Username</th>
                <th>Email</th>
                <th>Admin</th>
                <th>Select</th>
            </tr>
            </thead>
            <tbody>
            <% for (User user : users) { %>
            <tr>
                <td><%= user.getId() %></td>
                <td><%= user.getUsername() %></td>
                <td><%= user.getEmail() %></td>
                <td><%= user.isAdmin() %></td>
                <td>
                    <input type="radio" name="selectedUser" value="<%= user.getId() %>" />
                </td>
            </tr>
            <% } %>
            </tbody>
        </table>
        <div class="actions">
            <a href="main.jsp" class="button">Back to Home</a>
            <input type="submit" name="action" value="Delete" class="btn-action" />
            <input type="submit" name="action" value="Set as Admin" class="btn-action" />
        </div>
    </form>
</div>
</body>
</html>
