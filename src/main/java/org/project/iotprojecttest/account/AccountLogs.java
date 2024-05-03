package org.project.iotprojecttest.account;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "accountlogs", urlPatterns = {"/account/accountlogs"})
public class AccountLogs extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Forward to JSP for rendering.
        request.getRequestDispatcher("../accountmanagement/accountlogs.jsp").forward(request, response);
    }
}
