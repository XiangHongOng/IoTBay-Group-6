<%@ page import="org.project.iotprojecttest.model.objects.Product" %>
<%@ page import="java.util.List" %>
<%@ page import="org.project.iotprojecttest.model.objects.User" %>
<%@ page import="org.project.iotprojecttest.model.dao.StaffDAO" %>
<%@ page import="org.project.iotprojecttest.model.dao.ProductDAO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Product Management</title>
    <!-- Linking the external CSS file for styling -->
    <link rel="stylesheet" href="../css/styles.css">
</head>
<body>
<header>
    <nav>
        <!-- Logo of the IoTBay application -->
        <div class="logo">IoTBay</div>
        <!-- Navigation links for the application -->
        <ul class="nav-links">
            <!-- Conditionally set the class to 'active' if the current page is 'index' -->
            <li><a href="../index" class="<%= request.getRequestURI().endsWith("index.jsp") ? "active" : "" %>">Home</a></li>
            <!-- Conditionally set the class to 'active' if the current page is '/products' -->
            <li><a href="products" class="<%= request.getRequestURI().endsWith("/products.jsp") ? "active" : "" %>">Products</a></li>
            <%
                // Check if the current user is a staff member to display the product management link
                User user = (User) session.getAttribute("user");
                StaffDAO staffDAO = new StaffDAO();
                if (user != null && staffDAO.isUserStaff(user.getUserId())) { %>
            <!-- Show product management link only for staff members -->
            <li><a href="product-management" class="<%= request.getRequestURI().endsWith("/product-management.jsp") ? "active" : "" %>">Product Management</a></li>
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
            <!-- Display account and logout links if the user is logged in -->
            <a href="../account/accountdetails" class="<%= request.getRequestURI().endsWith("accountdetails.jsp") ? "active" : "" %>">Account</a>
            <a href="../logout-servlet">Logout</a>
            <% } else { %>
            <!-- Display login and register links if the user is not logged in -->
            <a href="../login">Login</a>
            <a href="../register">Register</a>
            <% } %>
        </div>
    </nav>
</header>

<main>
    <div class="product-management-container">
        <h2>Product Management</h2>

        <!-- Determine if we're adding a new product or editing an existing one based on the action parameter -->
        <h3><%= request.getParameter("action") != null && request.getParameter("action").equals("edit") ? "Edit" : "Add" %> Product</h3>
        <form action="product-management" method="post">
            <!-- Hidden input to tell the server what action to perform on form submission -->
            <input type="hidden" name="action" value="<%= request.getParameter("action") != null && request.getParameter("action").equals("edit") ? "update" : "add" %>">
            <%
                // If we're editing a product, get the product details and populate the form
                ProductDAO productDAO = new ProductDAO();
                if (request.getParameter("action") != null && request.getParameter("action").equals("edit")) { %>
            <input type="hidden" name="productId" value="<%= request.getParameter("id") %>">
            <% } %>
            <!-- Input fields for product details -->
            <!-- Each field is populated with data if editing, or left blank if adding -->
            <label for="productName">Product Name:</label>
            <input type="text" id="productName" name="productName" value="<%= request.getParameter("action") != null && request.getParameter("action").equals("edit") ? productDAO.getProductById(Integer.parseInt(request.getParameter("id"))).getProductName() : "" %>" required>
            <label for="productType">Product Type:</label>
            <input type="text" id="productType" name="productType" value="<%= request.getParameter("action") != null && request.getParameter("action").equals("edit") ? productDAO.getProductById(Integer.parseInt(request.getParameter("id"))).getProductType() : "" %>" required>
            <label for="unitPrice">Unit Price:</label>
            <input type="number" id="unitPrice" name="unitPrice" step="0.01" value="<%= request.getParameter("action") != null && request.getParameter("action").equals("edit") ? productDAO.getProductById(Integer.parseInt(request.getParameter("id"))).getUnitPrice() : "" %>" required>
            <label for="quantity">Quantity:</label>
            <input type="number" id="quantity" name="quantity" value="<%= request.getParameter("action") != null && request.getParameter("action").equals("edit") ? productDAO.getProductById(Integer.parseInt(request.getParameter("id"))).getQuantity() : "" %>" required>
            <!-- Conditionally show update or add button based on the action -->
            <% if (request.getParameter("action") != null && request.getParameter("action").equals("edit")) { %>
            <div class="form-actions">
                <input type="submit" value="Update Product" class="btn-update">
                <a href="product-management" class="btn-cancel">Cancel</a>
            </div>
            <% } else { %>
            <div class="form-actions">
                <input type="submit" value="Add Product" class="btn-add">
            </div>
            <% } %>
        </form>

        <!-- Displaying the list of products -->
        <h3>Product List</h3>
        <table>
            <tr>
                <th>Product Name</th>
                <th>Product Type</th>
                <th>Unit Price</th>
                <th>Quantity</th>
                <th>Actions</th>
            </tr>
            <%
                // Retrieve the list of products from the request attribute set in the servlet
                List<Product> products = (List<Product>) request.getAttribute("products");
                // Check if there are products to display
                if (products != null && !products.isEmpty()) {
                    // Iterate through the list of products and create a table row for each
                    for (Product product : products) {
            %>
            <tr>
                <td><%= product.getProductName() %></td>
                <td><%= product.getProductType() %></td>
                <td><%= product.getUnitPrice() %></td>
                <td><%= product.getQuantity() %></td>
                <td>
                    <!-- Action buttons for each product: edit and delete -->
                    <div class="action-buttons">
                        <!-- Edit button sends the user to the same page with action=edit and the product's ID -->
                        <a href="product-management?action=edit&id=<%= product.getProductId() %>" class="btn">Edit</a>
                        <!-- Delete form with hidden inputs to tell the server to delete the product on submission -->
                        <form action="product-management" method="post" class="action-form">
                            <input type="hidden" name="action" value="delete">
                            <input type="hidden" name="id" value="<%= product.getProductId() %>">
                            <input type="submit" value="Delete" class="btn">
                        </form>
                    </div>
                </td>
            </tr>
            <%
                }
            } else {
            %>
            <!-- If no products are found, display a message in a single table row -->
            <tr>
                <td colspan="5">No products found.</td>
            </tr>
            <%
                }
            %>
        </table>
    </div>
</main>
</body>
</html>
