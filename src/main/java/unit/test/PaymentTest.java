package unit.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.project.iotprojecttest.model.dao.CustomerDAO;
import org.project.iotprojecttest.model.dao.OrderDAO;
import org.project.iotprojecttest.model.dao.PaymentDAO;
import org.project.iotprojecttest.model.dao.UserDAO;
import org.project.iotprojecttest.model.objects.Customer;
import org.project.iotprojecttest.model.objects.Order;
import org.project.iotprojecttest.model.objects.Payment;
import org.project.iotprojecttest.model.objects.User;

import java.util.Date;
import java.util.List;

public class PaymentTest {
    private PaymentDAO paymentDAO;
    private CustomerDAO customerDAO;
    private OrderDAO orderDAO;
    private UserDAO userDAO;

    @Before
    public void setup() {
        paymentDAO = new PaymentDAO();
        customerDAO = new CustomerDAO();
        orderDAO = new OrderDAO();
        userDAO = new UserDAO();
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
    public void testGetPaymentsByCustomerWithoutUser() {
        PaymentDAO paymentDAO = new PaymentDAO();

        Customer nonUserCustomer = new Customer();
        nonUserCustomer.setFullName("test");
        nonUserCustomer.setEmail("testemail@test.com");
        nonUserCustomer.setCustomerType("Individual");
        nonUserCustomer.setAddress("test");

        int customer = customerDAO.createCustomer(nonUserCustomer);

        Order order = new Order();
        order.setCustomerId(customer);
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

        List<Payment> payments = paymentDAO.getPaymentsByCustomerId(customer);

        Assert.assertFalse(payments.isEmpty());

        customerDAO.deleteCustomer(customer);
    }

    @Test
    public void testGetPaymentsByUser() {
        User user = new User();
        user.setEmail("testuser@test.com");
        user.setPassword("password");
        user.setPhone("1234567890");

        int userId = userDAO.createUser(user);

        Customer registeredCustomer = new Customer();
        registeredCustomer.setUserId(userId);
        registeredCustomer.setFullName("test");
        registeredCustomer.setCustomerType("Individual");
        registeredCustomer.setAddress("test");
        int customer = customerDAO.createCustomer(registeredCustomer);

        Order order = new Order();
        order.setCustomerId(customer);
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

        List<Payment> payments = paymentDAO.getPaymentsByCustomerId(customer);

        Assert.assertFalse(payments.isEmpty());

        userDAO.deleteUser(userId);
        customerDAO.deleteCustomer(customer);
    }


    @Test
    public void testUpdatePaymentsByCustomerWithoutUser() {
        PaymentDAO paymentDAO = new PaymentDAO();

        Customer nonUserCustomer = new Customer();
        nonUserCustomer.setFullName("test");
        nonUserCustomer.setEmail("testemail@test.com");
        nonUserCustomer.setCustomerType("Individual");
        nonUserCustomer.setAddress("test");

        int customer = customerDAO.createCustomer(nonUserCustomer);

        Order order = new Order();
        order.setCustomerId(customer);
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

        List<Payment> payments = paymentDAO.getPaymentsByCustomerId(customer);

        Assert.assertFalse(payments.isEmpty());

        Payment paymentToUpdate = payments.get(0);

        paymentToUpdate.setAmount(200.0);
        paymentDAO.updatePayment(paymentToUpdate);

        List<Payment> newPayments = paymentDAO.getPaymentsByCustomerId(customer);
        Payment paymentToCheck = newPayments.get(0);

        Assert.assertEquals(200.0, paymentToCheck.getAmount(), 0.0);

        customerDAO.deleteCustomer(customer);
    }

    @Test
    public void testUpdatePaymentsByUser() {
        User user = new User();
        user.setEmail("testuser@test.com");
        user.setPassword("password");
        user.setPhone("1234567890");

        int userId = userDAO.createUser(user);

        Customer registeredCustomer = new Customer();
        registeredCustomer.setUserId(userId);
        registeredCustomer.setFullName("test");
        registeredCustomer.setCustomerType("Individual");
        registeredCustomer.setAddress("test");
        int customer = customerDAO.createCustomer(registeredCustomer);

        Order order = new Order();
        order.setCustomerId(customer);
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

        List<Payment> payments = paymentDAO.getPaymentsByCustomerId(customer);

        Assert.assertFalse(payments.isEmpty());

        Payment paymentToUpdate = payments.get(0);

        paymentToUpdate.setAmount(200.0);
        paymentDAO.updatePayment(paymentToUpdate);

        List<Payment> newPayments = paymentDAO.getPaymentsByCustomerId(customer);
        Payment paymentToCheck = newPayments.get(0);

        Assert.assertEquals(200.0, paymentToCheck.getAmount(), 0.0);

        userDAO.deleteUser(userId);
        customerDAO.deleteCustomer(customer);
    }

    @Test
    public void testDeletePaymentWithoutUser() {
        PaymentDAO paymentDAO = new PaymentDAO();

        Customer nonUserCustomer = new Customer();
        nonUserCustomer.setFullName("test");
        nonUserCustomer.setEmail("testemail@test.com");
        nonUserCustomer.setCustomerType("Individual");
        nonUserCustomer.setAddress("test");

        int customer = customerDAO.createCustomer(nonUserCustomer);

        Order order = new Order();
        order.setCustomerId(customer);
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

        List<Payment> payments = paymentDAO.getPaymentsByCustomerId(customer);

        Assert.assertFalse(payments.isEmpty());

        paymentDAO.deletePayment(paymentId);

        List<Payment> newPayments = paymentDAO.getPaymentsByCustomerId(customer);

        Assert.assertTrue(newPayments.isEmpty());

        customerDAO.deleteCustomer(customer);
    }

    @Test
    public void testDeletePaymentWithUser() {
        User user = new User();
        user.setEmail("testuser@test.com");
        user.setPassword("password");
        user.setPhone("1234567890");

        int userId = userDAO.createUser(user);

        Customer registeredCustomer = new Customer();
        registeredCustomer.setUserId(userId);
        registeredCustomer.setFullName("test");
        registeredCustomer.setCustomerType("Individual");
        registeredCustomer.setAddress("test");
        int customer = customerDAO.createCustomer(registeredCustomer);

        Order order = new Order();
        order.setCustomerId(customer);
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

        List<Payment> payments = paymentDAO.getPaymentsByCustomerId(customer);

        Assert.assertFalse(payments.isEmpty());

        paymentDAO.deletePayment(paymentId);

        List<Payment> newPayments = paymentDAO.getPaymentsByCustomerId(customer);

        Assert.assertTrue(newPayments.isEmpty());

        customerDAO.deleteCustomer(customer);
        userDAO.deleteUser(userId);
    }


}