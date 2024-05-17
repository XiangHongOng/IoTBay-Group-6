package org.project.iotprojecttest;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.project.iotprojecttest.model.dao.CustomerDAO;
import org.project.iotprojecttest.model.dao.UserDAO;
import org.project.iotprojecttest.model.objects.Customer;
import org.project.iotprojecttest.model.objects.User;

import java.io.IOException;

@WebServlet(name = "register", value = "/register")
public class RegisterServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String type = request.getParameter("customerType");

        UserDAO userDAO = new UserDAO();
        CustomerDAO customerDAO = new CustomerDAO();

        if (userDAO.getUserByEmail(email) != null)
        {
            request.setAttribute("errorMessage", "Email already exists. Please use a different email.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        User registeredUser = new User();
        registeredUser.setEmail(email);
        registeredUser.setPassword(password);
        registeredUser.setPhone(phone);

        // Submit registered user to the database
        int userId = userDAO.createUser(registeredUser);
        if (userId == 0)
        {
            request.setAttribute("errorMessage", "User registration failed. Please try again.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        User fetchedUser = userDAO.getUserById(userId);
        if (fetchedUser == null)
        {
            request.setAttribute("errorMessage", "Failed to retrieve user data after registration.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        Customer registeredCustomer = new Customer();
        registeredCustomer.setUserId(userId); // Set the UserId from the registeredUser
        registeredCustomer.setFullName(fullName);
        registeredCustomer.setCustomerType(type);
        registeredCustomer.setAddress(address);
        registeredCustomer.setEmail(email); // Store the email in the Customer object
        customerDAO.createCustomer(registeredCustomer);

        request.getSession().setAttribute("user", fetchedUser);
        response.sendRedirect("index");
    }
}