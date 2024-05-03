package org.project.iotprojecttest;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.project.iotprojecttest.model.dao.UserAccessLogDAO;
import org.project.iotprojecttest.model.objects.User;
import org.project.iotprojecttest.model.dao.UserDAO;
import org.project.iotprojecttest.model.objects.UserAccessLog;

import java.io.IOException;
import java.util.Date;


@WebServlet(name = "login", value = "/login")
public class LoginServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUserByEmail(email);

        if (user == null)
        {
            request.setAttribute("errorMessage", "Account does not exist. Please register.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        if (!user.getPassword().equals(password))
        {
            request.setAttribute("errorMessage", "Invalid password. Please try again.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        // After successful login
        UserAccessLog log = new UserAccessLog();
        log.setUserId(user.getUserId());
        log.setLoginDateTime(new Date());
        log.setLogoutDateTime(null);

        UserAccessLogDAO logDao = new UserAccessLogDAO();
        logDao.createUserAccessLog(log);

        request.getSession().setAttribute("user", user);
        response.sendRedirect("index");
    }
}