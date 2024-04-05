package org.project.iotprojecttest;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.project.iotprojecttest.model.User;
import org.project.iotprojecttest.model.dao.UserDAO;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "adminServlet", value = "/admin-servlet")
public class AdminServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User currentUser = (User) request.getSession().getAttribute("user");
        if (currentUser != null && currentUser.isAdmin()) {
            UserDAO userDAO = new UserDAO();
            List<User> users = userDAO.getAllUsers();
            request.setAttribute("users", users);
            request.getRequestDispatcher("admin.jsp").forward(request, response);
        } else {
            response.sendRedirect("login.jsp");
        }
    }

}