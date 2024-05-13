package unit.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.project.iotprojecttest.model.dao.CustomerDAO;
import org.project.iotprojecttest.model.dao.UserDAO;
import org.project.iotprojecttest.model.objects.Customer;
import org.project.iotprojecttest.model.objects.User;

public class CustomerAccountManagementTest {
    private CustomerDAO customerDAO;
    private UserDAO userDAO;

    @Before
    public void setup() {
        customerDAO = new CustomerDAO();
        userDAO = new UserDAO();
    }

    @Test
    public void testCreateCustomer() {
        Customer customer = new Customer();
        customer.setFullName("John Doe");
        customer.setCustomerType("Individual");
        customer.setAddress("123 Street");
        customer.setEmail("john@example.com");
        customer.setActive(true);

        int customerId = customerDAO.createCustomer(customer);

        Assert.assertTrue(customerId > 0);
    }

    @Test
    public void testCreateCustomerWithExistingUser() {
        User user = new User();
        user.setEmail("jane@example.com");
        user.setPassword("password");
        user.setPhone("9876543210");
        int userId = userDAO.createUser(user);

        Customer customer = new Customer();
        customer.setUserId(userId);
        customer.setFullName("Jane Smith");
        customer.setCustomerType("Individual");
        customer.setAddress("456 Street");
        customer.setEmail("jane@example.com");
        customer.setActive(true);

        int customerId = customerDAO.createCustomer(customer);

        Assert.assertTrue(customerId > 0);
    }

    @Test
    public void testUpdateCustomer() {
        Customer customer = new Customer();
        customer.setFullName("John Doe");
        customer.setCustomerType("Individual");
        customer.setAddress("123 Street");
        customer.setEmail("john@example.com");
        customer.setActive(true);
        int customerId = customerDAO.createCustomer(customer);

        Customer updatedCustomer = customerDAO.getCustomerByCustomerId(customerId);
        updatedCustomer.setFullName("John Updated");
        updatedCustomer.setAddress("456 Updated Street");
        customerDAO.updateCustomer(updatedCustomer);

        Customer retrievedCustomer = customerDAO.getCustomerByCustomerId(customerId);

        Assert.assertEquals("John Updated", retrievedCustomer.getFullName());
        Assert.assertEquals("456 Updated Street", retrievedCustomer.getAddress());
    }

    @Test
    public void testUpdateNonExistingCustomer() {
        Customer customer = new Customer();
        customer.setCustomerId(999);
        customer.setFullName("Non-existing Customer");
        customer.setCustomerType("Individual");
        customer.setAddress("Non-existing Address");
        customer.setEmail("nonexisting@example.com");
        customer.setActive(true);

        customerDAO.updateCustomer(customer);

        Customer retrievedCustomer = customerDAO.getCustomerByCustomerId(999);

        Assert.assertNull(retrievedCustomer);
    }

    @Test
    public void testDeactivateCustomer() {
        Customer customer = new Customer();
        customer.setFullName("John Doe");
        customer.setCustomerType("Individual");
        customer.setAddress("123 Street");
        customer.setEmail("john@example.com");
        customer.setActive(true);
        int customerId = customerDAO.createCustomer(customer);

        Customer retrievedCustomer = customerDAO.getCustomerByCustomerId(customerId);
        retrievedCustomer.setActive(false);
        customerDAO.updateCustomer(retrievedCustomer);

        Customer deactivatedCustomer = customerDAO.getCustomerByCustomerId(customerId);

        Assert.assertFalse(deactivatedCustomer.getActive());
    }

    @Test
    public void testDeactivateNonExistingCustomer() {
        Customer customer = new Customer();
        customer.setCustomerId(999);
        customer.setActive(false);

        customerDAO.updateCustomer(customer);

        Customer retrievedCustomer = customerDAO.getCustomerByCustomerId(999);

        Assert.assertNull(retrievedCustomer);
    }

    @Test
    public void testDeactivateInactiveCustomer() {
        Customer customer = new Customer();
        customer.setFullName("John Doe");
        customer.setCustomerType("Individual");
        customer.setAddress("123 Street");
        customer.setEmail("john@example.com");
        customer.setActive(false);
        int customerId = customerDAO.createCustomer(customer);

        Customer retrievedCustomer = customerDAO.getCustomerByCustomerId(customerId);
        retrievedCustomer.setActive(false);
        customerDAO.updateCustomer(retrievedCustomer);

        Customer deactivatedCustomer = customerDAO.getCustomerByCustomerId(customerId);

        Assert.assertFalse(deactivatedCustomer.getActive());
    }

    @Test
    public void testDeleteCustomer() {
        Customer customer = new Customer();
        customer.setFullName("John Doe");
        customer.setCustomerType("Individual");
        customer.setAddress("123 Street");
        customer.setEmail("john@example.com");
        customer.setActive(true);
        int customerId = customerDAO.createCustomer(customer);

        customerDAO.deleteCustomer(customerId);

        Customer deletedCustomer = customerDAO.getCustomerByCustomerId(customerId);

        Assert.assertNull(deletedCustomer);
    }

    @Test
    public void testDeleteNonExistingCustomer() {
        customerDAO.deleteCustomer(999);

        Customer deletedCustomer = customerDAO.getCustomerByCustomerId(999);

        Assert.assertNull(deletedCustomer);
    }

    @Test
    public void testDeleteCustomerWithAssociatedUser() {
        User user = new User();
        user.setEmail("jane@example.com");
        user.setPassword("password");
        user.setPhone("9876543210");
        int userId = userDAO.createUser(user);

        Customer customer = new Customer();
        customer.setUserId(userId);
        customer.setFullName("Jane Smith");
        customer.setCustomerType("Individual");
        customer.setAddress("456 Street");
        customer.setEmail("jane@example.com");
        customer.setActive(true);
        int customerId = customerDAO.createCustomer(customer);

        customerDAO.deleteCustomer(customerId);

        Customer deletedCustomer = customerDAO.getCustomerByCustomerId(customerId);
        User associatedUser = userDAO.getUserById(userId);

        Assert.assertNull(deletedCustomer);
        Assert.assertNull(associatedUser);
    }
}