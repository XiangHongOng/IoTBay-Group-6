package unit.test;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.project.iotprojecttest.model.dao.OrderDAO;
import org.project.iotprojecttest.model.objects.Order;

import java.util.Date;

public class OrderTest
{
    private OrderDAO orderDAO;

    @Before
    public void setup() {
        orderDAO = new OrderDAO();
    }

    @Test
    public void testCreateOrder() {
        Order order = new Order();
        order.setCustomerId(1);
        order.setOrderDate(new Date());

        OrderDAO orderDAO = new OrderDAO();
        int orderId = orderDAO.createOrder(order);

        Assert.assertTrue(orderId > 0);
    }

    @Test
    public void testGetOrderById() {
        OrderDAO orderDAO = new OrderDAO();
        Order order = orderDAO.getOrderById(1);

        Assert.assertNotNull(order);
        Assert.assertEquals(1, order.getOrderId());
    }

    @Test
    public void testUpdateOrder() {
        OrderDAO orderDAO = new OrderDAO();
        Order order = orderDAO.getOrderById(1);

        order.setCustomerId(2);
        order.setOrderDate(new Date());

        orderDAO.updateOrder(order);

        Order updatedOrder = orderDAO.getOrderById(1);
        Assert.assertEquals(2, updatedOrder.getCustomerId());
    }

    @Test
    public void testDeleteOrder() {
        OrderDAO orderDAO = new OrderDAO();
        Order order = new Order();
        order.setCustomerId(1);
        order.setOrderDate(new Date());

        int orderId = orderDAO.createOrder(order);

        orderDAO.deleteOrder(orderId);

        Order deletedOrder = orderDAO.getOrderById(orderId);
        Assert.assertNull(deletedOrder);
    }
}