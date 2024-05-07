package org.project.iotprojecttest.account;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.project.iotprojecttest.model.dao.CustomerDAO;
import org.project.iotprojecttest.model.dao.OrderDAO;
import org.project.iotprojecttest.model.dao.OrderLineItemDAO;
import org.project.iotprojecttest.model.dao.ProductDAO;
import org.project.iotprojecttest.model.dao.UserDAO;
import org.project.iotprojecttest.model.objects.Customer;
import org.project.iotprojecttest.model.objects.Order;
import org.project.iotprojecttest.model.objects.OrderLineItem;
import org.project.iotprojecttest.model.objects.User;
import org.project.iotprojecttest.model.util.OrderUtil;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "accountdelete", urlPatterns = {"/account/accountdelete"})
public class AccountDelete extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("../accountmanagement/accountdelete.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserDAO userDAO = new UserDAO();
        CustomerDAO customerDAO = new CustomerDAO();

        User user = (User) session.getAttribute("user");

        // Check if the user is logged in
        if (user != null)
        {
            String confirmPassword = request.getParameter("confirmPassword");

            // Verify the user's email and password
            if (userDAO.verifyUser(user.getEmail(), confirmPassword))
            {
                Customer customer = customerDAO.getCustomerByUserId(user.getUserId());

                //If a customer account has been made on sign-up, deactivate it
                if (customer != null)
                {
                    OrderUtil orderUtil = new OrderUtil();
                    orderUtil.restoreProductStockForUnpaidOrders(customer.getCustomerId());

                    customer.setActive(false);
                    customerDAO.updateCustomer(customer);
                }

                // Delete the user account
                userDAO.deleteUser(user.getUserId());
                session.invalidate();

                HttpSession newSession = request.getSession(true);
                newSession.setAttribute("successMessage", "Your account has been successfully deleted.");
                response.sendRedirect("../index");
            }
            else
            {
                session.setAttribute("errorMessage", "Failed to delete your account.");
                response.sendRedirect("../index");
            }
        }
        else
        {
            session.setAttribute("errorMessage", "Failed to delete your account.");
            response.sendRedirect("../index");
        }
    }
}
