package org.project.iotprojecttest;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.project.iotprojecttest.model.User;
import org.project.iotprojecttest.model.dao.UserDAO;

import java.io.IOException;

@WebServlet(name = "userManagementServlet", value = "/user-management-servlet")
public class UserManagementServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        int selectedUserId = Integer.parseInt(request.getParameter("selectedUser"));

        UserDAO userDAO = new UserDAO();
        final User user = userDAO.getUserById(selectedUserId);

        User currentUser = (User) request.getSession().getAttribute("user");

        if (currentUser != null && currentUser.getId() == selectedUserId) {
            request.setAttribute("message", "You cannot modify your own account.");
            request.getRequestDispatcher("/result.jsp").forward(request, response);
            return;
        }

        switch (action) {
            case "Delete":
                userDAO.deleteUser(selectedUserId);
                request.setAttribute("message", "User has been deleted.");
                break;
            case "Set as Admin":
                if (user != null) {
                    boolean newAdminStatus = !user.isAdmin();
                    userDAO.setAsAdmin(selectedUserId, newAdminStatus);
                    user.setAdmin(newAdminStatus);
                    request.setAttribute("message", "User admin status changed to: " + (newAdminStatus ? "Admin" : "User"));
                    break;
                }
                break;
        }

        request.setAttribute("user", user);
        request.getRequestDispatcher("/result.jsp").forward(request, response);
    }

}