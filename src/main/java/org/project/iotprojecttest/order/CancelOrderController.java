package org.project.iotprojecttest.order;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.project.iotprojecttest.model.dao.OrderDAO;
import org.project.iotprojecttest.model.dao.OrderLineItemDAO;
import org.project.iotprojecttest.model.dao.ProductDAO;
import org.project.iotprojecttest.model.util.OrderUtil;

import java.io.IOException;


@WebServlet(name = "cancelorder", value = "/order/cancelorder")
public class CancelOrderController extends HttpServlet {
    private OrderDAO orderDAO;
    private OrderLineItemDAO orderLineItemDAO;
    private ProductDAO productDAO;

    @Override
    public void init() throws ServletException {
        orderDAO = new OrderDAO();
        productDAO = new ProductDAO();
        orderLineItemDAO = new OrderLineItemDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int orderId = Integer.parseInt(request.getParameter("orderId"));

        // Get all items in the order and restore the stock of each product
        OrderUtil orderUtil = new OrderUtil();
        orderUtil.restoreProductStockForOrder(orderId);

        // Update the order status to "Cancelled"
        orderDAO.updateOrderStatus(orderId, "Cancelled");

        request.setAttribute("successMessage", "Order #" + orderId + " has been cancelled.");

        // Forward the request to the view orders page
        request.getRequestDispatcher("vieworders").forward(request, response);
}
}