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

@WebServlet(name = "deactivatecustomer", value = "/staff/deactivatecustomer")
public class DeactivateCustomerController extends HttpServlet {
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

        if (user == null || !staffDAO.isUserStaff(user.getUserId()))
        {
            response.sendRedirect("../login");
            return;
        }

        int customerId = Integer.parseInt(request.getParameter("id"));
        Customer customer = customerDAO.getCustomerByCustomerId(customerId);

        // Check if the customer exists
        if (customer != null)
        {
            // Deactivate the customer
            customer.setActive(false);
            customerDAO.updateCustomer(customer);

            request.setAttribute("successMessage", "Customer deactivated successfully.");
        }
        else
        {
            request.setAttribute("errorMessage", "Customer not found.");
        }

        List<Customer> customers = customerDAO.getAllCustomers();
        request.setAttribute("customers", customers);
        request.getRequestDispatcher("../customermanagement/customermanagement.jsp").forward(request, response);
    }
}