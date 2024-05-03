package org.project.iotprojecttest;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.project.iotprojecttest.model.dao.UserAccessLogDAO;
import org.project.iotprojecttest.model.objects.User;
import org.project.iotprojecttest.model.objects.UserAccessLog;

import java.io.IOException;
import java.util.Date;

@WebServlet(name = "logout", value = "/logout-servlet")
public class LogoutServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");

        if (user != null)
        {
            UserAccessLog log = new UserAccessLog();
            log.setUserId(user.getUserId());
            log.setLoginDateTime(null);
            log.setLogoutDateTime(new Date());

            UserAccessLogDAO logDao = new UserAccessLogDAO();
            logDao.createUserAccessLog(log);
        }

        HttpSession session = request.getSession(false);
        if (session != null)
        {
            session.invalidate();
        }

        HttpSession newSession = request.getSession(true);
        newSession.setAttribute("successMessage", "You have been successfully logged out.");

        // Redirect to the IndexServlet which is mapped to '/home'
        response.sendRedirect("index");
    }
}
