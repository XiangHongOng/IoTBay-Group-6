package org.project.iotprojecttest.product;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.project.iotprojecttest.model.dao.ProductDAO;
import org.project.iotprojecttest.model.objects.Product;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "products", value = "/product/products")
public class ProductController extends HttpServlet {
    private ProductDAO productDAO;

    @Override
    public void init() {
        productDAO = new ProductDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve all products
        List<Product> products = productDAO.getAllProducts();

        // Filter products based on search criteria
        String searchQuery = request.getParameter("search");
        if (searchQuery != null && !searchQuery.isEmpty())
        {
            products = filterProducts(products, searchQuery);
        }

        request.setAttribute("products", products);

        // Forward to the product list page
        request.getRequestDispatcher("products.jsp").forward(request, response);
    }

    private List<Product> filterProducts(List<Product> products, String searchQuery) {
        List<Product> filteredProducts = new ArrayList<>();
        for (Product product : products) {
            if (product.getProductName().toLowerCase().contains(searchQuery.toLowerCase()) ||
                    product.getProductType().toLowerCase().contains(searchQuery.toLowerCase())) {
                filteredProducts.add(product);
            }
        }
        return filteredProducts;
    }
}