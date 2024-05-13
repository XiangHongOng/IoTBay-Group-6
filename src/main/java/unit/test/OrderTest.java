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

        int orderId = orderDAO.createOrder(order);

        Assert.assertTrue(orderId > 0);
    }

}