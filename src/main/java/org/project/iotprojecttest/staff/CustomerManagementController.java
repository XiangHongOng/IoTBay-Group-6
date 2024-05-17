package org.project.iotprojecttest.staff;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.project.iotprojecttest.model.dao.CustomerDAO;
import org.project.iotprojecttest.model.dao.StaffDAO;
import org.project.iotprojecttest.model.objects.Customer;
import org.project.iotprojecttest.model.objects.User;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "customermanagement", value = "/staff/customermanagement")
public class CustomerManagementController extends HttpServlet {
    private CustomerDAO customerDAO;
    private StaffDAO staffDAO;

    @Override
    public void init() throws ServletException {
        customerDAO = new CustomerDAO();
        staffDAO = new StaffDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");

        if (user == null || !staffDAO.isUserStaff(user.getUserId())) {
            response.sendRedirect("../login");
            return;
        }

        List<Customer> customers = customerDAO.getAllCustomers();
        request.setAttribute("customers", customers);
        request.getRequestDispatcher("../customermanagement/customermanagement.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");

        if (user == null || !staffDAO.isUserStaff(user.getUserId())) {
            response.sendRedirect("../login");
            return;
        }

        String action = request.getParameter("action");
        if (action != null && action.equals("search")) {
            // Get filter types
            String name = request.getParameter("name");
            String type = request.getParameter("type");

            // Get customers based on the filter
            List<Customer> customers;
            if (name != null && !name.isEmpty()) {
                customers = customerDAO.searchCustomersByName(name);
            } else if (type != null && !type.isEmpty()) {
                customers = customerDAO.searchCustomersByType(type);
            } else {
                customers = customerDAO.getAllCustomers();
            }

            request.setAttribute("customers", customers);
        }

        request.getRequestDispatcher("../customermanagement/customermanagement.jsp").forward(request, response);
    }
}