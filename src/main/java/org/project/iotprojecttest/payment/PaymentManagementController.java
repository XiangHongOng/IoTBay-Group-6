package org.project.iotprojecttest.payment;

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
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "paymentmanagement", value = "/payment/paymentmanagement")
public class PaymentManagementController extends HttpServlet {
    private OrderDAO orderDAO;
    private CustomerDAO customerDAO;

    @Override
    public void init() throws ServletException {
        orderDAO = new OrderDAO();
        customerDAO = new CustomerDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        String customerEmail = request.getParameter("customerEmail");
        String orderId = request.getParameter("orderId");
        String orderDate = request.getParameter("orderDate");

        List<Order> orders = null;

        // Check if the user is logged in
        if (user != null)
        {
            // Logged-in user
            Customer customer = customerDAO.getCustomerByUserId(user.getUserId());
            if (customer != null)
            {
                if (!customerDAO.isCustomerActive(customer.getCustomerId()))
                {
                    request.setAttribute("errorMessage", "The customer account has been deactivated. Please contact staff for assistance.");
                    request.getRequestDispatcher("../paymentmanagement/paymentmanagement.jsp").forward(request, response);
                    return;
                }

                orders = orderDAO.getUnpaidOrdersByCustomerId(customer.getCustomerId());
            }
        }
        // Check if the user is not logged in
        else if (customerEmail != null && !customerEmail.isEmpty())
        {
            // Anonymous user
            Customer customer = customerDAO.getCustomerByEmail(customerEmail);

            if (customer != null)
            {
                if (!customerDAO.isCustomerActive(customer.getCustomerId()))
                {
                    request.setAttribute("errorMessage", "The customer account has been deactivated. Please contact staff for assistance.");
                    request.getRequestDispatcher("../paymentmanagement/paymentmanagement.jsp").forward(request, response);
                    return;
                }

                // Search by order ID and customer
                if (orderId != null && !orderId.isEmpty())
                {
                    Order order = orderDAO.getOrderByIdAndCustomerId(Integer.parseInt(orderId), customer.getCustomerId());
                    if (order != null && order.getStatus().equals("Submitted"))
                    {
                        orders = new ArrayList<>();
                        orders.add(order);
                    }
                }
                // Search by order date and customer
                else if (orderDate != null && !orderDate.isEmpty())
                {
                    orders = orderDAO.getUnpaidOrdersByCustomerIdAndDate(customer.getCustomerId(), Date.valueOf(orderDate));
                }
            }
        }

        request.setAttribute("orders", orders);
        request.getRequestDispatcher("../paymentmanagement/paymentmanagement.jsp").forward(request, response);
    }
}