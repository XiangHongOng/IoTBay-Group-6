package org.project.iotprojecttest.payment;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.project.iotprojecttest.model.dao.OrderDAO;
import org.project.iotprojecttest.model.dao.PaymentDAO;
import org.project.iotprojecttest.model.objects.Order;
import org.project.iotprojecttest.model.objects.Payment;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

@WebServlet(name = "createpayment", value = "/payment/createpayment")
public class CreatePaymentController extends HttpServlet {
    private PaymentDAO paymentDAO;

    @Override
    public void init() throws ServletException {
        paymentDAO = new PaymentDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("../paymentmanagement/createpayment.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        String paymentMethod = request.getParameter("paymentMethod");
        String creditCardDetails = request.getParameter("creditCardDetails");
        double amount = Double.parseDouble(request.getParameter("amount"));
        Date paymentDate = Date.valueOf(request.getParameter("paymentDate"));

        OrderDAO orderDAO = new OrderDAO();
        PaymentDAO paymentDAO = new PaymentDAO();

        double orderTotalAmount = orderDAO.calculateOrderTotalAmount(orderId);
        double totalPaid = paymentDAO.getTotalPaidAmountByOrderId(orderId);

        double remainingAmount = orderTotalAmount - totalPaid;

        // Check if the payment amount exceeds the remaining amount
        if (amount > remainingAmount) {
            request.setAttribute("errorMessage", "Payment amount cannot exceed the remaining amount.");
            request.getRequestDispatcher("../paymentmanagement/createpayment.jsp").forward(request, response);
            return;
        }

        // Crate payment object and insert it into the database
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
    }
}