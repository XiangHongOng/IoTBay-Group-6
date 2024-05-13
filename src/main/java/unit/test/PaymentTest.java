package unit.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.project.iotprojecttest.model.dao.PaymentDAO;
import org.project.iotprojecttest.model.objects.Payment;

import java.util.Date;

public class PaymentTest {
    private PaymentDAO paymentDAO;

    @Before
    public void setup() {
        paymentDAO = new PaymentDAO();
    }

    @Test
    public void testCreatePayment() {
        Payment payment = new Payment();
        payment.setPaymentMethod("Credit Card");
        payment.setCreditCardDetails("1234567890123456");
        payment.setAmount(100.0);
        payment.setPaymentDate(new Date());
        payment.setStatus("Unpaid");
        payment.setOrderId(1);

        int paymentId = paymentDAO.createPayment(payment);

        Assert.assertTrue(paymentId > 0);
    }

}