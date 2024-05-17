package org.project.iotprojecttest.staff;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.project.iotprojecttest.model.dao.CustomerDAO;
import org.project.iotprojecttest.model.dao.StaffDAO;
import org.project.iotprojecttest.model.dao.UserDAO;
import org.project.iotprojecttest.model.objects.Customer;
import org.project.iotprojecttest.model.objects.User;

import java.io.IOException;

@WebServlet(name = "createcustomer", value = "/staff/createcustomer")
public class CreateCustomerController extends HttpServlet {
    private CustomerDAO customerDAO;
    private UserDAO userDAO;
    private StaffDAO staffDAO;

    @Override
    public void init() throws ServletException {
        customerDAO = new CustomerDAO();
        userDAO = new UserDAO();
        staffDAO = new StaffDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User loggedUser = (User) request.getSession().getAttribute("user");

        if (loggedUser == null || !staffDAO.isUserStaff(loggedUser.getUserId())) {
            response.sendRedirect("../login");
            return;
        }

        request.getRequestDispatcher("../customermanagement/createcustomer.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String customerType = request.getParameter("customerType");
        String address = request.getParameter("address");

        Customer customer = new Customer();
        customer.setFullName(fullName);
        customer.setEmail(email);
        customer.setCustomerType(customerType);
        customer.setAddress(address);

        // Check if the email already exists in the database
        User user = userDAO.getUserByEmail(email);
        if (user != null) {
            customer.setUserId(user.getUserId());
        }

        // Create the customer
        int customerId = customerDAO.createCustomer(customer);

        if (customerId != 0) {
            response.sendRedirect("customermanagement");
        } else {
            request.setAttribute("errorMessage", "Failed to create customer.");
            request.getRequestDispatcher("../customermanagement/createcustomer.jsp").forward(request, response);
        }
    }
}