package org.project.iotprojecttest.product;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.project.iotprojecttest.model.dao.ProductDAO;
import org.project.iotprojecttest.model.dao.StaffDAO;
import org.project.iotprojecttest.model.objects.Product;
import org.project.iotprojecttest.model.objects.User;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "product-management", value = "/product/product-management")
public class ProductManagementController extends HttpServlet {
    private ProductDAO productDAO;
    private StaffDAO staffDAO;

    @Override
    public void init() {
        productDAO = new ProductDAO();
        staffDAO = new StaffDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null)
        {
            // Check if the user is a staff member
            User user = (User) session.getAttribute("user");
            if (user != null && staffDAO.isUserStaff(user.getUserId()))
            {
                // Check if the user is trying to edit a product
                String action = request.getParameter("action");
                if (action != null && action.equals("edit"))
                {
                    // Set the action and id parameters as request attributes
                    request.setAttribute("action", action);
                    request.setAttribute("id", request.getParameter("id"));
                }
                // Retrieve all products
                List<Product> products = productDAO.getAllProducts();
                request.setAttribute("products", products);
                // Forward to the product management page
                request.getRequestDispatcher("productmanagement.jsp").forward(request, response);
            }
            else
            {
                // Redirect to the login page or display an error message
                response.sendRedirect("login");
            }
        }
        else
        {
            // Redirect to the login page or display an error message
            response.sendRedirect("login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check if the user is a staff member
        HttpSession session = request.getSession(false);
        if (session != null) {
            User user = (User) session.getAttribute("user");
            if (user != null && staffDAO.isUserStaff(user.getUserId())) {
                // Handle product creation, update, and delete operations based on form input
                String action = request.getParameter("action");
                switch (action) {
                    case "add":
                        // Add new product logic
                        String productName = request.getParameter("productName");
                        String productType = request.getParameter("productType");
                        double unitPrice = Double.parseDouble(request.getParameter("unitPrice"));
                        int quantity = Integer.parseInt(request.getParameter("quantity"));

                        Product newProduct = new Product();
                        newProduct.setProductName(productName);
                        newProduct.setProductType(productType);
                        newProduct.setUnitPrice(unitPrice);
                        newProduct.setQuantity(quantity);

                        productDAO.createProduct(newProduct);
                        break;
                    case "update":
                        // Update existing product logic
                        int productId = Integer.parseInt(request.getParameter("productId"));
                        productName = request.getParameter("productName");
                        productType = request.getParameter("productType");
                        unitPrice = Double.parseDouble(request.getParameter("unitPrice"));
                        quantity = Integer.parseInt(request.getParameter("quantity"));

                        Product existingProduct = productDAO.getProductById(productId);
                        existingProduct.setProductName(productName);
                        existingProduct.setProductType(productType);
                        existingProduct.setUnitPrice(unitPrice);
                        existingProduct.setQuantity(quantity);

                        productDAO.updateProduct(existingProduct);
                        break;
                    case "delete":
                        // Delete product logic
                        productId = Integer.parseInt(request.getParameter("id"));
                        productDAO.deleteProduct(productId);
                        break;
                }
            }
        }
        response.sendRedirect("product-management");
    }
}