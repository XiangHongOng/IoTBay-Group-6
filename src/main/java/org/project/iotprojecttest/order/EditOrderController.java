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

import java.io.IOException;
import java.util.Date;
import java.util.List;

@WebServlet(name = "editorder", value = "/order/editorder")
public class EditOrderController extends HttpServlet {
    private OrderDAO orderDAO;
    private OrderLineItemDAO orderLineItemDAO;
    private ProductDAO productDAO;
    private CustomerDAO customerDAO;

    @Override
    public void init() throws ServletException {
        orderDAO = new OrderDAO();
        orderLineItemDAO = new OrderLineItemDAO();
        productDAO = new ProductDAO();
        customerDAO = new CustomerDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int orderId = Integer.parseInt(request.getParameter("id"));
        Order order = orderDAO.getOrderById(orderId);
        List<Product> products = productDAO.getAllProducts();
        List<OrderLineItem> orderLineItems = orderLineItemDAO.getOrderLineItemsByOrderId(orderId);

        request.setAttribute("order", order);
        request.setAttribute("products", products);
        request.setAttribute("orderLineItems", orderLineItems);

        request.getRequestDispatcher("../ordermanagement/editorder.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        int productId = Integer.parseInt(request.getParameter("productId"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        String customerEmail = request.getParameter("customerEmail");

        Order order = orderDAO.getOrderById(orderId);
        Customer customer = customerDAO.getCustomerByCustomerId(order.getCustomerId());
        List<OrderLineItem> existingOrderLineItems = orderLineItemDAO.getOrderLineItemsByOrderId(orderId);


        // Get existing items in the order and update the stock of each product
        if (existingOrderLineItems.isEmpty())
        {
            Product product = productDAO.getProductById(productId);

            // Check if the quantity is available in stock
            if (product.getQuantity() < quantity)
            {
                request.setAttribute("errorMessage", "Insufficient stock for the selected product.");
                request.getRequestDispatcher("../ordermanagement/editorder.jsp").forward(request, response);
                return;
            }

            productDAO.updateProductStock(productId, quantity);
        }
        else
        {
            OrderLineItem existingOrderLineItem = existingOrderLineItems.get(0);
            int oldProductId = existingOrderLineItem.getProductId();
            int oldQuantity = existingOrderLineItem.getOrderedQuantity();

            // Check if the product has changed
            if (oldProductId != productId)
            {
                // Restore the stock of the old product
                Product oldProduct = productDAO.getProductById(oldProductId);
                productDAO.restoreProductStock(oldProductId, oldQuantity);

                // Update the stock of the new product
                Product newProduct = productDAO.getProductById(productId);
                if (newProduct.getQuantity() < quantity)
                {
                    request.setAttribute("errorMessage", "Insufficient stock for the selected product.");
                    request.getRequestDispatcher("../ordermanagement/editorder.jsp").forward(request, response);
                    return;
                }

                productDAO.updateProductStock(productId, quantity);
            }
            else if (oldQuantity != quantity)
            {
                // Get the difference in stock
                int stockDifference = quantity - oldQuantity;
                if (stockDifference > 0)
                {
                    // Check if the quantity is available in stock
                    Product product = productDAO.getProductById(productId);
                    if (product.getQuantity() < stockDifference)
                    {
                        request.setAttribute("errorMessage", "Insufficient stock for the selected product.");
                        request.getRequestDispatcher("../ordermanagement/editorder.jsp").forward(request, response);
                        return;
                    }

                    // Update the stock of the product
                    productDAO.updateProductStock(productId, stockDifference);
                }
                else
                {
                    // Restore the stock of the product
                    productDAO.restoreProductStock(productId, -stockDifference);
                }
            }
        }

        // Check if the customer exists
        if (customer == null)
        {
            // Create a new customer for this order
            customer = new Customer();
            customer.setCustomerType("Guest");
            customer.setEmail(customerEmail);
            int customerId = customerDAO.createCustomer(customer);
            order.setCustomerId(customerId);
        }

        order.setOrderDate(new Date());

        orderDAO.updateOrder(order);

        OrderLineItem orderLineItem = new OrderLineItem();
        orderLineItem.setOrderId(orderId);
        orderLineItem.setProductId(productId);
        orderLineItem.setOrderedQuantity(quantity);

        // Create or update the order line item
        if (existingOrderLineItems.isEmpty())
        {
            orderLineItemDAO.createOrderLineItem(orderLineItem);
        }
        else
        {
            // Update the existing order line item
            orderLineItem.setOrderLineId(existingOrderLineItems.get(0).getOrderLineId());
            orderLineItemDAO.updateOrderLineItem(orderLineItem);
        }

        request.setAttribute("successMessage", "Order updated successfully.");
        request.getRequestDispatcher("../ordermanagement/vieworders.jsp").forward(request, response);
    }
}