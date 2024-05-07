package org.project.iotprojecttest.model.util;

import org.project.iotprojecttest.model.dao.OrderDAO;
import org.project.iotprojecttest.model.dao.OrderLineItemDAO;
import org.project.iotprojecttest.model.dao.ProductDAO;
import org.project.iotprojecttest.model.objects.Order;
import org.project.iotprojecttest.model.objects.OrderLineItem;

import java.util.List;

public class OrderUtil {
    private OrderDAO orderDAO;
    private OrderLineItemDAO orderLineItemDAO;
    private ProductDAO productDAO;

    public OrderUtil() {
        orderDAO = new OrderDAO();
        orderLineItemDAO = new OrderLineItemDAO();
        productDAO = new ProductDAO();
    }

    public void restoreProductStockForUnpaidOrders(int customerId) {
        List<Order> orders = orderDAO.getUnpaidOrdersByCustomerId(customerId);

        for (Order order : orders) {
            List<OrderLineItem> items = orderLineItemDAO.getOrderLineItemsByOrderId(order.getOrderId());
            for (OrderLineItem item : items) {
                int productId = item.getProductId();
                int quantity = item.getOrderedQuantity();
                productDAO.restoreProductStock(productId, quantity);
            }
        }
    }

    public void restoreProductStockForOrder(int orderId) {
        List<OrderLineItem> items = orderLineItemDAO.getOrderLineItemsByOrderId(orderId);
        for (OrderLineItem item : items) {
            int productId = item.getProductId();
            int quantity = item.getOrderedQuantity();
            productDAO.restoreProductStock(productId, quantity);
        }
    }
}