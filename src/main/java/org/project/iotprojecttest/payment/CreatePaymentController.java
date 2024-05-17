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
import java.sql.Date;

@WebServlet(name = "createpayment", value = "/payment/createpayment")
public class CreatePaymentController extends HttpServlet {
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
        String action = request.getParameter("action");

        if (action != null && action.equals("displayForm")) {
            int orderId = Integer.parseInt(request.getParameter("orderId"));
            String customerEmail = request.getParameter("customerEmail");

            User user = (User) request.getSession().getAttribute("user");
            boolean isAuthorized = isAuthorizedToCreatePayment(orderId, customerEmail, user);

            if (isAuthorized) {
                request.setAttribute("orderId", orderId);
                request.getRequestDispatcher("../paymentmanagement/createpayment.jsp").forward(request, response);
            } else {
                // Handle unauthorized access
                request.setAttribute("errorMessage", "Unauthorized access to payment details.");
                request.getRequestDispatcher("../paymentmanagement/paymentmanagement.jsp").forward(request, response);
            }
        } else if (action != null && action.equals("createPayment")) {
            int orderId = Integer.parseInt(request.getParameter("orderId"));
            String paymentMethod = request.getParameter("paymentMethod");
            String creditCardDetails = request.getParameter("creditCardDetails");
            double amount = Double.parseDouble(request.getParameter("amount"));
            java.sql.Date paymentDate = java.sql.Date.valueOf(request.getParameter("paymentDate"));

            double orderTotalAmount = orderDAO.calculateOrderTotalAmount(orderId);
            double totalPaid = paymentDAO.getTotalPaidAmountByOrderId(orderId);

            double remainingAmount = orderTotalAmount - totalPaid;

            // Check if the payment amount exceeds the remaining amount
            if (amount > remainingAmount) {
                request.setAttribute("errorMessage", "Payment amount cannot exceed the remaining amount.");
                request.setAttribute("orderId", orderId);
                request.getRequestDispatcher("../paymentmanagement/createpayment.jsp").forward(request, response);
                return;
            }

            // Create payment object and insert it into the database
            Payment payment = new Payment();
            payment.setOrderId(orderId);
            payment.setPaymentMethod(paymentMethod);
            payment.setCreditCardDetails(creditCardDetails);
            payment.setAmount(amount);
            payment.setPaymentDate(paymentDate);
            payment.setStatus("Unpaid");

            int paymentId = paymentDAO.createPayment(payment);

            request.setAttribute("successMessage", "Payment created successfully with ID #" + paymentId + ".");
            request.getRequestDispatcher("../paymentmanagement/paymentmanagement.jsp").forward(request, response);
        } else {
            // Handle invalid action
            request.setAttribute("errorMessage", "Invalid action.");
            request.getRequestDispatcher("../paymentmanagement/paymentmanagement.jsp").forward(request, response);
        }
    }

    private boolean isAuthorizedToCreatePayment(int orderId, String customerEmail, User user) {
        Order order = orderDAO.getOrderById(orderId);
        if (order != null && order.getStatus() != null && (order.getStatus().equalsIgnoreCase("Saved") || order.getStatus().equalsIgnoreCase("Submitted"))) {
            Customer customer = customerDAO.getCustomerByCustomerId(order.getCustomerId());
            if (customer != null) {
                if (user != null) {
                    // Logged-in user
                    return customer.getUserId() == user.getUserId();
                } else {
                    // Anonymous user
                    return customer.getUserId() == 0 && customer.getEmail() != null && customer.getEmail().equals(customerEmail);
                }
            }
        }
        return false;
    }
}