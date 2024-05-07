<%@ page import="org.project.iotprojecttest.model.objects.User" %>
<%@ page import="org.project.iotprojecttest.model.dao.StaffDAO" %>
<%@ page import="org.project.iotprojecttest.model.objects.Customer" %>
<%@ page import="java.util.List" %>
<%@ page import="org.project.iotprojecttest.model.dao.CustomerDAO" %>
<%@ page import="org.project.iotprojecttest.model.dao.UserDAO" %><%--
  Created by IntelliJ IDEA.
  User: camse
  Date: 5/05/2024
  Time: 5:15 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Manage Customer</title>
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
          <li><a href="customermanagement">Customer Management</a></li>
        </ul>
      </li>
      <% } %>
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
  <div>
    <h2 class="access-logs-title">Customer Management</h2>

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

    <div class="form-container">
      <form action="customermanagement" method="get" class="search-form">
        <input type="text" name="name" placeholder="Search by name">
        <select name="type">
          <option value="">All Types</option>
          <option value="Individual">Individual</option>
          <option value="Company">Company</option>
        </select>
        <input type="submit" value="Search" class="btn btn-primary">
      </form>

      <div class="form-actions">
        <a href="createcustomer" class="btn btn-add">Create Customer</a>
      </div>

    </div>


    <table class="order-table-styled">
      <thead>
      <tr>
        <th>Customer ID</th>
        <th>Name</th>
        <th>Email</th>
        <th>Type</th>
        <th>Address</th>
        <th>Actions</th>
      </tr>
      </thead>
      <tbody>
      <% List<Customer> customers = (List<Customer>) request.getAttribute("customers");
        for (Customer customer : customers) {
      %>
      <tr>
        <td><%= customer.getCustomerId() %></td>
        <td><%= customer.getFullName() %></td>
        <td><%= customer.getEmail() %></td>
        <td><%= customer.getCustomerType() %></td>
        <td><%= customer.getAddress() %></td>
        <td class="action-buttons">
          <a href="viewcustomer?id=<%= customer.getCustomerId() %>" class="btn btn-view btn-sm">Modify</a>
        </td>
      </tr>
      <% } %>
      </tbody>
    </table>
  </div>
</main>

</body>
</html>
