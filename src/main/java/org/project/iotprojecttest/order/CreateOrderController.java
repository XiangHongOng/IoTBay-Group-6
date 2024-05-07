package org.project.iotprojecttest.order;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.project.iotprojecttest.model.dao.CustomerDAO;
import org.project.iotprojecttest.model.dao.OrderDAO;
import org.project.iotprojecttest.model.dao.OrderLineItemDAO;
import org.project.iotprojecttest.model.dao.ProductDAO;
import org.project.iotprojecttest.model.objects.Customer;
import org.project.iotprojecttest.model.objects.Order;
import org.project.iotprojecttest.model.objects.OrderLineItem;
import org.project.iotprojecttest.model.objects.Product;
import org.project.iotprojecttest.model.objects.User;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@WebServlet(name = "createorder", value = "/order/createorder")
public class CreateOrderController extends HttpServlet {
    private OrderDAO orderDAO;
    private ProductDAO productDAO;
    private CustomerDAO customerDAO;
    private OrderLineItemDAO orderLineItemDAO;

    @Override
    public void init() throws ServletException {
        orderDAO = new OrderDAO();
        productDAO = new ProductDAO();
        customerDAO = new CustomerDAO();
        orderLineItemDAO = new OrderLineItemDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Product> products = productDAO.getAllProducts();
        request.setAttribute("products", products);

        boolean isLoggedIn = request.getSession().getAttribute("user") != null;
        request.setAttribute("isLoggedIn", isLoggedIn);

        request.getRequestDispatcher("../ordermanagement/createorder.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int productId = Integer.parseInt(request.getParameter("productId"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        // Check if the quantity is available in stock
        Product product = productDAO.getProductById(productId);
        if (product.getQuantity() < quantity) {
            request.setAttribute("errorMessage", "Insufficient stock for the selected product.");
            request.getRequestDispatcher("../ordermanagement/createorder.jsp").forward(request, response);
            return;
        }

        Order order = new Order();
        order.setOrderDate(new Date());

        User user = (User) request.getSession().getAttribute("user");
        Customer customer = null;
        if (user != null)
        {
            // Check if the user is a customer
            customer = customerDAO.getCustomerByUserId(user.getUserId());
            if (customer == null)
            {
                return;
            }
        }
        else
        {
            // Check if the customer exists
            String customerEmail = request.getParameter("customerEmail");
            String customerName = request.getParameter("customerName");
            customer = customerDAO.getCustomerByEmail(customerEmail);
            if (customer == null)
            {
                // Create a new customer
                customer = new Customer();
                customer.setFullName(customerName);
                customer.setCustomerType("Individual");
                customer.setEmail(customerEmail);
                int customerId = customerDAO.createCustomer(customer);
                customer.setCustomerId(customerId);
            }
        }

        if (!customerDAO.isCustomerActive(customer.getCustomerId()))
        {
            request.setAttribute("errorMessage", "The customer account has been deactivated. Please contact staff for assistance.");
            request.getRequestDispatcher("../ordermanagement/createorder.jsp").forward(request, response);
            return;
        }

        order.setCustomerId(customer.getCustomerId());

        // Set the order status
        OrderLineItem orderLineItem = new OrderLineItem();
        int orderId = orderDAO.createOrder(order);
        orderLineItem.setOrderId(orderId);
        orderLineItem.setProductId(productId);
        orderLineItem.setOrderedQuantity(quantity);

        // Create the order line item
        orderLineItemDAO.createOrderLineItem(orderLineItem);

        // Update the product stock
        productDAO.updateProductStock(productId, quantity);

        request.setAttribute("successMessage", "Order saved successfully with Order ID: " + orderId + ".");
        request.getRequestDispatcher("../ordermanagement/createorder.jsp").forward(request, response);
    }
}