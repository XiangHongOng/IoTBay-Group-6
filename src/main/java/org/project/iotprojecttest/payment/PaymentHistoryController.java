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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "paymenthistory", value = "/payment/paymenthistory")
public class PaymentHistoryController extends HttpServlet {
    private PaymentDAO paymentDAO;
    private CustomerDAO customerDAO;
    private OrderDAO orderDAO;

    @Override
    public void init() throws ServletException {
        paymentDAO = new PaymentDAO();
        customerDAO = new CustomerDAO();
        orderDAO = new OrderDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("../paymentmanagement/paymenthistory.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        String customerEmail = request.getParameter("customerEmail");
        String paymentId = request.getParameter("paymentId");
        String paymentDate = request.getParameter("paymentDate");

        List<Payment> payments = new ArrayList<>();
        Customer customer = null;

        // Check if the user is logged in
        if (user != null)
        {
            // Logged-in user
            customer = customerDAO.getCustomerByUserId(user.getUserId());
            if (customer != null)
            {
                if (!customerDAO.isCustomerActive(customer.getCustomerId()))
                {
                    request.setAttribute("errorMessage", "The customer account has been deactivated. Please contact staff for assistance.");
                    request.getRequestDispatcher("../paymentmanagement/paymenthistory.jsp").forward(request, response);
                    return;
                }

                payments = paymentDAO.getPaymentsByCustomerId(customer.getCustomerId());
            }
        }
        // Check if the user is not logged in
        else if (customerEmail != null && !customerEmail.isEmpty())
        {
            // Anonymous user
            if (paymentId != null && !paymentId.isEmpty())
            {
                // Search by payment ID and customer email
                Payment payment = paymentDAO.getPaymentById(Integer.parseInt(paymentId));

                if (payment != null)
                {
                    // Get the order and customer associated with the payment
                    Order order = orderDAO.getOrderById(payment.getOrderId());
                    customer = customerDAO.getCustomerByCustomerId(order.getCustomerId());

                    if (customer != null)
                    {
                        if (!customerDAO.isCustomerActive(customer.getCustomerId()))
                        {
                            request.setAttribute("errorMessage", "The customer account has been deactivated. Please contact staff for assistance.");
                            request.getRequestDispatcher("../paymentmanagement/paymenthistory.jsp").forward(request, response);
                            return;
                        }

                        if (customer.getEmail() != null && customer.getEmail().equals(customerEmail))
                        {
                            payments.add(payment);
                        }
                    }
                }
            }
            //Check if the payment date is not empty
            else if (paymentDate != null && !paymentDate.isEmpty())
            {
                // Search by payment date and customer email
                java.sql.Date sqlDate = java.sql.Date.valueOf(LocalDate.parse(paymentDate));
                List<Payment> paymentsByDate = paymentDAO.searchPaymentsByDate(sqlDate);

                // Get the order and customer associated with the payment
                for (Payment payment : paymentsByDate)
                {
                    Order order = orderDAO.getOrderById(payment.getOrderId());
                    customer = customerDAO.getCustomerByCustomerId(order.getCustomerId());

                    if (customer != null)
                    {
                        if (!customerDAO.isCustomerActive(customer.getCustomerId()))
                        {
                            request.setAttribute("errorMessage", "The customer account has been deactivated. Please contact staff for assistance.");
                            request.getRequestDispatcher("../paymentmanagement/paymenthistory.jsp").forward(request, response);
                            return;
                        }

                        if (customer.getEmail() != null && customer.getEmail().equals(customerEmail))
                        {
                            payments.add(payment);
                        }
                    }

                }
            }
        }

        request.setAttribute("payments", payments);
        request.getRequestDispatcher("../paymentmanagement/paymenthistory.jsp").forward(request, response);
    }
}