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
import java.util.List;

@WebServlet(name = "submitpayment", value = "/payment/submitpayment")
public class SubmitPaymentController extends HttpServlet {
    private PaymentDAO paymentDAO;
    private OrderDAO orderDAO;

    @Override
    public void init() throws ServletException {
        paymentDAO = new PaymentDAO();
        orderDAO = new OrderDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int paymentId = Integer.parseInt(request.getParameter("paymentId"));

        // Get payment by id
        Payment payment = paymentDAO.getPaymentById(paymentId);
        if (payment != null)
        {
            // Get order by id
            Order order = orderDAO.getOrderById(payment.getOrderId());
            if (order != null)
            {
                // Calculate the total amount of the order
                double orderTotalAmount = orderDAO.calculateOrderTotalAmount(order.getOrderId());
                double totalPaid = paymentDAO.getTotalPaidAmountByOrderId(order.getOrderId());

                payment.setStatus("Paid");
                paymentDAO.updatePayment(payment);

                totalPaid += payment.getAmount();

                // Check if the total amount paid is equal to the total amount of the order
                if (totalPaid >= orderTotalAmount)
                {
                    order.setStatus("Paid");
                    orderDAO.updateOrder(order);
                    request.setAttribute("successMessage", "Payment submitted successfully with #" + payment.getPaymentId() + ". Order is now fully paid.");
                }
                else
                {
                    request.setAttribute("successMessage", "Payment submitted successfully with #" + payment.getPaymentId() + ". Remaining amount to be paid: " + (orderTotalAmount - totalPaid));
                }
            }
            else
            {
                request.setAttribute("errorMessage", "Order not found.");
            }
        }
        else
        {
            request.setAttribute("errorMessage", "Payment not found.");
        }

        request.getRequestDispatcher("../paymentmanagement/paymentmanagement.jsp").forward(request, response);
    }
}