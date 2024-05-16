package unit.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.project.iotprojecttest.model.dao.OrderDAO;
import org.project.iotprojecttest.model.dao.PaymentDAO;
import org.project.iotprojecttest.model.objects.Order;
import org.project.iotprojecttest.model.objects.Payment;

import java.util.Date;

public class PaymentTest {
    private PaymentDAO paymentDAO;
    private OrderDAO orderDAO;

    @Before
    public void setup() {
        paymentDAO = new PaymentDAO();
        orderDAO = new OrderDAO();
    }

    @Test
    public void testCreatePayment() {
        Order order = new Order();
        order.setCustomerId(1);
        order.setOrderDate(new Date());
        int orderId = orderDAO.createOrder(order);

        Payment payment = new Payment();
        payment.setPaymentMethod("Credit Card");
        payment.setCreditCardDetails("1234567890123456");
        payment.setAmount(100.0);
        payment.setPaymentDate(new Date());
        payment.setStatus("Unpaid");
        payment.setOrderId(orderId);

        int paymentId = paymentDAO.createPayment(payment);

        Assert.assertTrue(paymentId > 0);
    }

    @Test
    public void testUpdatePayment() {
        Payment payment = new Payment();
        payment.setPaymentMethod("Credit Card");
        payment.setCreditCardDetails("1234567890123456");
        payment.setAmount(100.0);
        payment.setPaymentDate(new Date());
        payment.setStatus("Unpaid");
        payment.setOrderId(1);
        int paymentId = paymentDAO.createPayment(payment);

        Payment updatedPayment = paymentDAO.getPaymentById(paymentId);
        updatedPayment.setAmount(200.0);
        boolean isUpdated = paymentDAO.updatePayment(updatedPayment);

        Assert.assertTrue(isUpdated);
    }

    @Test
    public void testDeletePayment() {
        Payment payment = new Payment();
        payment.setPaymentMethod("Credit Card");
        payment.setCreditCardDetails("1234567890123456");
        payment.setAmount(100.0);
        payment.setPaymentDate(new Date());
        payment.setStatus("Unpaid");
        payment.setOrderId(1);
        int paymentId = paymentDAO.createPayment(payment);

        boolean isDeleted = paymentDAO.deletePayment(paymentId);

        Assert.assertTrue(isDeleted);
    }


}