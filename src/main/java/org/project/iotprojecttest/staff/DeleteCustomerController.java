package org.project.iotprojecttest.staff;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.project.iotprojecttest.model.dao.CustomerDAO;
import org.project.iotprojecttest.model.dao.OrderDAO;
import org.project.iotprojecttest.model.dao.OrderLineItemDAO;
import org.project.iotprojecttest.model.dao.ProductDAO;
import org.project.iotprojecttest.model.dao.StaffDAO;
import org.project.iotprojecttest.model.dao.UserDAO;
import org.project.iotprojecttest.model.objects.Customer;
import org.project.iotprojecttest.model.objects.Order;
import org.project.iotprojecttest.model.objects.OrderLineItem;
import org.project.iotprojecttest.model.objects.User;
import org.project.iotprojecttest.model.util.OrderUtil;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "deletecustomer", value = "/staff/deletecustomer")
public class DeleteCustomerController extends HttpServlet {
    private CustomerDAO customerDAO;
    private StaffDAO staffDAO;
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        customerDAO = new CustomerDAO();
        staffDAO = new StaffDAO();
        userDAO = new UserDAO();
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
            //Restore unpaid order stock
            OrderUtil orderUtil = new OrderUtil();
            orderUtil.restoreProductStockForUnpaidOrders(customer.getCustomerId());

            // Delete the customer
            customerDAO.deleteCustomer(customerId);

            // Delete the user if it exists
            if (customer.getUserId() != 0)
            {
                userDAO.deleteUser(customer.getUserId());
            }

            request.setAttribute("successMessage", "Customer deleted successfully.");
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