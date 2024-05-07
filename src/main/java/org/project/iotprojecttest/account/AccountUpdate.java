package org.project.iotprojecttest.account;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.project.iotprojecttest.model.dao.CustomerDAO;
import org.project.iotprojecttest.model.dao.UserDAO;
import org.project.iotprojecttest.model.objects.Customer;
import org.project.iotprojecttest.model.objects.User;

import java.io.IOException;

@WebServlet(name = "accountupdate", urlPatterns = {"/account/accountupdate"})
public class AccountUpdate extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Forward to JSP for rendering.
        request.getRequestDispatcher("../accountmanagement/accountupdate.jsp").forward(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User originalUser = (User) session.getAttribute("user");
        UserDAO userDAO = new UserDAO();
        CustomerDAO customerDAO = new CustomerDAO();

        // Check if the user is logged in
        if (originalUser != null)
        {
            Customer customer = customerDAO.getCustomerByUserId(originalUser.getUserId());

            // Update the user details
            User updatedUser = new User(originalUser);
            String fullName = request.getParameter("fullName");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String phone = request.getParameter("phone");

            // Check if the name field is empty
            if (!fullName.isEmpty())
            {
                customer.setFullName(fullName);
                customerDAO.updateCustomer(customer);
            }

            // Check if the email field is empty
            if (!email.isEmpty())
            {
                // Check if the email address already exists
                if (userDAO.emailExists(email) && !email.equals(originalUser.getEmail()))
                {
                    request.setAttribute("errorMessage", "The email address already exists.");
                    request.getRequestDispatcher("../accountmanagement/accountupdate.jsp").forward(request, response);
                    return;
                }

                updatedUser.setEmail(email);
            }

            // Check if the password field is empty
            if (!password.isEmpty())
            {
                updatedUser.setPassword(password);
            }

            // Check if the phone field is empty
            if (!phone.isEmpty())
            {
                updatedUser.setPhone(phone);
            }

            // Update the user details
            if (userDAO.updateUser(updatedUser))
            {
                session.setAttribute("user", updatedUser);
                request.setAttribute("successMessage", "Your details have been updated successfully.");
                request.getRequestDispatcher("../accountmanagement/accountupdate.jsp").forward(request, response);
            }
            else
            {
                response.sendRedirect("../account/accountdetails");
            }
        }
        else
        {
            response.sendRedirect("../account/accountdetails");
        }
    }
}
