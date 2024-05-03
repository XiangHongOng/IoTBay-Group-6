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


@WebServlet(name = "vieworderpayment", value = "/payment/vieworderpayment")
public class ViewOrderPaymentController extends HttpServlet {
    private PaymentDAO paymentDAO;
    private OrderDAO orderDAO;
    private CustomerDAO customerDAO;

    @Override
    public void init() throws ServletException {
        paymentDAO = new PaymentDAO();
        orderDAO = new OrderDAO();        customerDAO = new CustomerDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        String customerEmail = request.getParameter("customerEmail");

        User user = (User) request.getSession().getAttribute("user");
        boolean isAuthorized = false;

        // Check if the user is authorized to view the payment details
        Order order = orderDAO.getOrderById(orderId);
        if (order != null)
        {
            // Get the customer by order ID
            Customer customer = customerDAO.getCustomerByCustomerId(order.getCustomerId());
            if (customer != null)
            {
                // Get the payment by order ID
                Payment payment = paymentDAO.getPaymentByOrderId(orderId);
                if (payment != null)
                {
                    if (user != null)
                    {
                        // Logged-in user
                        if (customer.getUserId() == user.getUserId())
                        {
                            isAuthorized = true;
                        }
                    }
                    else
                    {
                        // Anonymous user
                        if (customer.getUserId() == 0 && customer.getEmail() != null && customer.getEmail().equals(customerEmail))
                        {
                            isAuthorized = true;
                        }
                    }
                }
            }
        }

        if (isAuthorized)
        {
            // Get the unpaid payment by order ID
            Payment payment = paymentDAO.getUnpaidPaymentByOrderId(orderId);
            request.setAttribute("payment", payment);
            request.setAttribute("paymentId", payment.getPaymentId()); // Pass the paymentId as a request attribute
            request.setAttribute("paymentMethod", payment.getPaymentMethod());
            request.setAttribute("creditCardDetails", payment.getCreditCardDetails());
            request.setAttribute("amount", payment.getAmount());
            request.setAttribute("paymentDate", payment.getPaymentDate());
            request.getRequestDispatcher("../paymentmanagement/updatepayment.jsp").forward(request, response);
        }
        else
        {
            // Handle unauthorized access
            request.setAttribute("errorMessage", "Unauthorized access to payment details.");
            request.getRequestDispatcher("../paymentmanagement/paymentmanagement.jsp").forward(request, response);
        }
    }
}