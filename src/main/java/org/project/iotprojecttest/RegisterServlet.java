package org.project.iotprojecttest;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.project.iotprojecttest.model.User;
import org.project.iotprojecttest.model.dao.UserDAO;

import java.io.IOException;

@WebServlet(name = "register", value = "/register-servlet")
public class RegisterServlet extends HttpServlet
{
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        UserDAO userDAO = new UserDAO();

        if (userDAO.doesUsernameExist(username)) {
            request.setAttribute("errorMessage", "Username already exists. Please choose a different username.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        if (userDAO.doesEmailExist(email)) {
            request.setAttribute("errorMessage", "Email already exists. Please use a different email.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        User registeredUser = new User();
        registeredUser.setUsername(username);
        registeredUser.setPassword(password);
        registeredUser.setEmail(email);

        // Submit registered user to the database, commented so no spam. Uncomment if you wish to submit users
        userDAO.registerUser(registeredUser);

        request.getSession().setAttribute("user", registeredUser);
        request.getRequestDispatcher("welcome.jsp").forward(request, response);
    }
}