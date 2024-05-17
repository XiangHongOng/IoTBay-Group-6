package org.project.iotprojecttest.order;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.project.iotprojecttest.model.dao.OrderDAO;

import java.io.IOException;

@WebServlet(name = "submitorder", value = "/order/submitorder")
public class SubmitOrderController extends HttpServlet {
    private OrderDAO orderDAO;

    @Override
    public void init() throws ServletException {
        orderDAO = new OrderDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int orderId = Integer.parseInt(request.getParameter("orderId"));

        // Update the order status to "Submitted"
        orderDAO.updateOrderStatus(orderId, "Submitted");

        request.setAttribute("successMessage", "Order #" + orderId + " has been submitted.");

        // Forward the request to the view orders page
        request.getRequestDispatcher("vieworders").forward(request, response);

    }
}