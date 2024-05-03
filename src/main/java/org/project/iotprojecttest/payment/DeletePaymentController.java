package org.project.iotprojecttest.payment;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.project.iotprojecttest.model.dao.PaymentDAO;
import org.project.iotprojecttest.model.objects.Payment;

import java.io.IOException;

@WebServlet(name = "deletepayment", value = "/payment/deletepayment")
public class DeletePaymentController extends HttpServlet {
    private PaymentDAO paymentDAO;

    @Override
    public void init() throws ServletException {
        paymentDAO = new PaymentDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int paymentId = Integer.parseInt(request.getParameter("paymentId"));

        // Get payment by id
        Payment payment = paymentDAO.getPaymentById(paymentId);
        if (payment != null)
        {
            // Attempts to delete the payment
            if (paymentDAO.deletePayment(paymentId))
            {
                request.setAttribute("successMessage", "Payment #" + payment.getPaymentId() + " deleted successfully.");
            }
            else
            {
                request.setAttribute("errorMessage", "Failed to delete payment.");
            }
        } else {
            request.setAttribute("errorMessage", "Payment not found.");
        }

        request.getRequestDispatcher("../paymentmanagement/paymentmanagement.jsp").forward(request, response);
    }
}