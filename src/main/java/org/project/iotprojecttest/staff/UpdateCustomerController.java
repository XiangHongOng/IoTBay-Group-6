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
import org.project.iotprojecttest.model.util.OrderUtil;

import java.io.IOException;

@WebServlet(name = "viewcustomer", value = "/staff/viewcustomer")
public class UpdateCustomerController extends HttpServlet {
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User loggedUser = (User) request.getSession().getAttribute("user");

        if (loggedUser == null || !staffDAO.isUserStaff(loggedUser.getUserId())) {
            response.sendRedirect("../login");
            return;
        }

        String action = request.getParameter("action");

        if (action != null && action.equals("edit")) {
            String customerIdParam = request.getParameter("customerId");
            if (customerIdParam != null && !customerIdParam.isEmpty()) {
                int customerId = Integer.parseInt(customerIdParam);
                Customer customer = customerDAO.getCustomerByCustomerId(customerId);
                if (customer != null) {
                    request.setAttribute("customer", customer);
                    request.getRequestDispatcher("../customermanagement/viewcustomer.jsp").forward(request, response);
                } else {
                    response.sendRedirect("customermanagement");
                }
            } else {
                response.sendRedirect("customermanagement");
            }
        } else {
            int customerId = Integer.parseInt(request.getParameter("customerId"));
            String fullName = request.getParameter("fullName");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String customerType = request.getParameter("customerType");
            String address = request.getParameter("address");
            boolean isActive = request.getParameter("isActive") != null;

            Customer customer = customerDAO.getCustomerByCustomerId(customerId);
            Customer customerByEmail = customerDAO.getCustomerByEmail(email);

            // Checks if the customer exists
            if (customer != null) {
                // Checks if the email already exists in the database
                if (customerByEmail != null && customerByEmail.getCustomerId() != customerId) {
                    request.setAttribute("errorMessage", "Email already exists.");
                    request.setAttribute("customer", customer);
                    request.getRequestDispatcher("../customermanagement/viewcustomer.jsp").forward(request, response);
                    return;
                }

                if (!isActive) {
                    // Restore unpaid order stock
                    OrderUtil orderUtil = new OrderUtil();
                    orderUtil.restoreProductStockForUnpaidOrders(customer.getCustomerId());
                }

                // Updates the customer
                customer.setFullName(fullName);
                customer.setCustomerType(customerType);
                customer.setAddress(address);
                customer.setEmail(email);
                customer.setActive(isActive);
                customerDAO.updateCustomer(customer);

                User user = userDAO.getUserById(customerId);
                if (user != null) {
                    // Updates the user if there is a match since the user object stores the phone number
                    user.setPhone(phone);
                    userDAO.updateUser(user);
                }

                response.sendRedirect("customermanagement");
            } else {
                request.setAttribute("errorMessage", "Customer not found.");
                request.getRequestDispatcher("../customermanagement/viewcustomer.jsp").forward(request, response);
            }
        }
    }
}