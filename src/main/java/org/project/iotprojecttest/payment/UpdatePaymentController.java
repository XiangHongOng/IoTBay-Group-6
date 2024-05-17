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

@WebServlet(name = "updatepayment", value = "/payment/updatepayment")
public class UpdatePaymentController extends HttpServlet {
    private PaymentDAO paymentDAO;

    @Override
    public void init() throws ServletException {
        paymentDAO = new PaymentDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int paymentId = Integer.parseInt(request.getParameter("paymentId"));
        String paymentMethod = request.getParameter("paymentMethod");
        String creditCardDetails = request.getParameter("creditCardDetails");
        double amount = Double.parseDouble(request.getParameter("amount"));
        Date paymentDate = Date.valueOf(request.getParameter("paymentDate"));

        OrderDAO orderDAO = new OrderDAO();

        Payment payment = paymentDAO.getPaymentById(paymentId);
        Order order = orderDAO.getOrderById(payment.getOrderId());

        // Calculate the total amount of the order
        double orderTotalAmount = orderDAO.calculateOrderTotalAmount(order.getOrderId());
        double totalPaid = paymentDAO.getTotalPaidAmountByOrderId(order.getOrderId());

        double remainingAmount = Math.round((orderTotalAmount - totalPaid) * 100.0) / 100.0;

        // Check if the payment amount exceeds the remaining amount
        if (amount > remainingAmount)
        {
            request.setAttribute("errorMessage", "Payment amount cannot exceed the remaining amount.");
            request.getRequestDispatcher("../paymentmanagement/paymentmanagement.jsp").forward(request, response);
            return;
        }

        // Update payment object and insert it into the database
        payment.setPaymentMethod(paymentMethod);
        payment.setCreditCardDetails(creditCardDetails);
        payment.setAmount(amount);
        payment.setPaymentDate(paymentDate);

        paymentDAO.updatePayment(payment);

        request.setAttribute("successMessage", "Payment #" + paymentId + " updated successfully.");
        request.getRequestDispatcher("../paymentmanagement/paymentmanagement.jsp").forward(request, response);
    }
}