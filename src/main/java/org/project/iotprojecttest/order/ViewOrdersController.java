package org.project.iotprojecttest.order;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.project.iotprojecttest.model.dao.CustomerDAO;
import org.project.iotprojecttest.model.dao.OrderDAO;
import org.project.iotprojecttest.model.objects.Customer;
import org.project.iotprojecttest.model.objects.Order;
import org.project.iotprojecttest.model.objects.User;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "vieworders", value = "/order/vieworders")
public class ViewOrdersController extends HttpServlet {
    private OrderDAO orderDAO;
    private CustomerDAO customerDAO;

    @Override
    public void init() throws ServletException {
        orderDAO = new OrderDAO();
        customerDAO = new CustomerDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("../ordermanagement/vieworders.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        String customerEmail = request.getParameter("customerEmail");
        String orderId = request.getParameter("orderId");
        String orderDate = request.getParameter("orderDate");

        List<Order> orders = new ArrayList<>();

        // Check if the user is logged in
        if (user != null) {
            // Check if the user is a customer
            Customer customer = customerDAO.getCustomerByUserId(user.getUserId());
            if (customer != null) {
                if (!customerDAO.isCustomerActive(customer.getCustomerId())) {
                    request.setAttribute("errorMessage", "The customer account has been deactivated. Please contact staff for assistance.");
                    request.getRequestDispatcher("../ordermanagement/vieworders.jsp").forward(request, response);
                    return;
                }

                // Checks if an orderId or orderDate parameter is provided
                if (orderId != null && !orderId.isEmpty()) {
                    // Get the order by orderId and customerId
                    Order order = orderDAO.getOrderByIdAndCustomerId(Integer.parseInt(orderId), customer.getCustomerId());
                    if (order != null) {
                        orders.add(order);
                    }
                } else if (orderDate != null && !orderDate.isEmpty()) {
                    try {
                        LocalDate localDate = LocalDate.parse(orderDate);
                        java.sql.Date sqlDate = java.sql.Date.valueOf(localDate);
                        orders = orderDAO.searchOrdersByDateAndCustomerId(sqlDate, customer.getCustomerId());
                    } catch (DateTimeParseException e) {
                        // Handle the case when the orderDate parameter is not a valid date
                        request.setAttribute("errorMessage", "Invalid order date format.");
                    }
                } else {
                    orders = orderDAO.getOrdersByCustomerId(customer.getCustomerId());
                }
            }
        }
        // Check if the customerEmail parameter is provided
        else if (customerEmail != null && !customerEmail.isEmpty()) {
            // Check if the customer exists
            Customer customer = customerDAO.getCustomerByEmail(customerEmail);
            if (customer != null) {
                if (!customerDAO.isCustomerActive(customer.getCustomerId())) {
                    System.out.println("customer deactivated");
                    request.setAttribute("errorMessage", "The customer account has been deactivated. Please contact staff for assistance.");
                    request.getRequestDispatcher("../ordermanagement/vieworders.jsp").forward(request, response);
                    return;
                }

                // Checks if an orderId or orderDate parameter is provided
                if (orderId != null && !orderId.isEmpty()) {
                    // Get the order by orderId and customerId
                    Order order = orderDAO.getOrderByIdAndCustomerId(Integer.parseInt(orderId), customer.getCustomerId());
                    if (order != null) {
                        orders.add(order);
                    }
                } else if (orderDate != null && !orderDate.isEmpty()) {
                    try {
                        LocalDate localDate = LocalDate.parse(orderDate);
                        java.sql.Date sqlDate = java.sql.Date.valueOf(localDate);
                        orders = orderDAO.searchOrdersByDateAndCustomerId(sqlDate, customer.getCustomerId());
                    } catch (DateTimeParseException e) {
                        // Handle the case when the orderDate parameter is not a valid date
                        request.setAttribute("errorMessage", "Invalid order date format.");
                    }
                } else {
                    orders = orderDAO.getOrdersByCustomerId(customer.getCustomerId());
                }
            } else {
                System.out.println("no customer found");
            }
        }

        request.setAttribute("orders", orders);
        request.getRequestDispatcher("../ordermanagement/vieworders.jsp").forward(request, response);
    }
}