package org.project.iotprojecttest;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.project.iotprojecttest.model.User;
import org.project.iotprojecttest.model.dao.UserDAO;

import java.io.IOException;


@WebServlet(name = "login", value = "/login-servlet")
public class LoginServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUserByUsername(username);

        if (user == null) {
            request.setAttribute("errorMessage", "Account does not exist. Please register.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        if (!user.getPassword().equals(password)) {
            request.setAttribute("errorMessage", "Invalid password. Please try again.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        request.getSession().setAttribute("user", user);
        request.getRequestDispatcher("welcome.jsp").forward(request, response);
    }
}