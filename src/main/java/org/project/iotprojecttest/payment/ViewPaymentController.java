package org.project.iotprojecttest.payment;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.project.iotprojecttest.model.dao.CustomerDAO;
import org.project.iotprojecttest.model.dao.OrderDAO;
import org.project.iotprojecttest.model.dao.PaymentDAO;
import org.project.iotprojecttest.model.objects.Customer;
import org.project.iotprojecttest.model.objects.Order;
import org.project.iotprojecttest.model.objects.Payment;
import org.project.iotprojecttest.model.objects.User;

import java.io.IOException;

@WebServlet(name = "viewpayment", value = "/payment/viewpayment")
public class ViewPaymentController extends HttpServlet {
    private PaymentDAO paymentDAO;
    private OrderDAO orderDAO;
    private CustomerDAO customerDAO;

    @Override
    public void init() throws ServletException {
        paymentDAO = new PaymentDAO();
        orderDAO = new OrderDAO();
        customerDAO = new CustomerDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int paymentId = Integer.parseInt(request.getParameter("id"));
        String customerEmail = request.getParameter("customerEmail");

        User user = (User) request.getSession().getAttribute("user");
        boolean isAuthorized = false;

        // Check if the user is authorized to view the payment details
        Payment payment = paymentDAO.getPaymentById(paymentId);
        if (payment != null)
        {
            // Get the order by payment ID
            Order order = orderDAO.getOrderById(payment.getOrderId());
            if (order != null)
            {
                // Get the customer by order ID
                Customer customer = customerDAO.getCustomerByCustomerId(order.getCustomerId());
                if (user != null)
                {
                    // Logged-in user
                    if (customer != null && user.getUserId() == customer.getUserId())
                    {
                        isAuthorized = true;
                    }
                }
                else
                {
                    // Anonymous user
                    if (customer != null && customer.getEmail() != null && customer.getEmail().equals(customerEmail))
                    {
                        isAuthorized = true;
                    }
                }
            }
        }

        if (isAuthorized)
        {
            // Display payment details
            request.setAttribute("payment", payment);
            request.getRequestDispatcher("../paymentmanagement/viewpayment.jsp").forward(request, response);
        }
        else
        {
            // Handle unauthorized access
            request.setAttribute("errorMessage", "Unauthorized access to payment details.");
            request.getRequestDispatcher("../paymentmanagement/paymenthistory.jsp").forward(request, response);
        }
    }
}