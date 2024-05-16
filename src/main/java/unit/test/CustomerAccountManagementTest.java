package unit.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.project.iotprojecttest.model.dao.CustomerDAO;
import org.project.iotprojecttest.model.dao.UserDAO;
import org.project.iotprojecttest.model.objects.Customer;
import org.project.iotprojecttest.model.objects.User;
import java.util.List;

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
        customer.setEmail("john1@example.com");
        customer.setActive(true);

        int customerId = customerDAO.createCustomer(customer);

        Assert.assertTrue(customerId > 0);
        customerDAO.deleteCustomer(customerId);
    }

    @Test
    public void testCreateCustomerWithExistingUser() {
        User user = new User();
        user.setEmail("jane1@example.com");
        user.setPassword("password");
        user.setPhone("9876543210");
        int userId = userDAO.createUser(user);

        Customer customer = new Customer();
        customer.setUserId(userId);
        customer.setFullName("Jane Smith");
        customer.setCustomerType("Individual");
        customer.setAddress("456 Street");
        customer.setEmail("jane1@example.com");
        customer.setActive(true);

        int customerId = customerDAO.createCustomer(customer);

        Assert.assertTrue(customerId > 0);
        userDAO.deleteUser(userId);
        customerDAO.deleteCustomer(customerId);
    }

    @Test
    public void testUpdateCustomer() {
        Customer customer = new Customer();
        customer.setFullName("John Doe");
        customer.setCustomerType("Individual");
        customer.setAddress("123 Street");
        customer.setEmail("john2@example.com");
        customer.setActive(true);
        int customerId = customerDAO.createCustomer(customer);

        Customer updatedCustomer = customerDAO.getCustomerByCustomerId(customerId);
        updatedCustomer.setFullName("John Updated");
        updatedCustomer.setAddress("456 Updated Street");
        customerDAO.updateCustomer(updatedCustomer);

        Customer retrievedCustomer = customerDAO.getCustomerByCustomerId(customerId);

        Assert.assertEquals("John Updated", retrievedCustomer.getFullName());
        Assert.assertEquals("456 Updated Street", retrievedCustomer.getAddress());
        customerDAO.deleteCustomer(customerId);
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
        customer.setEmail("john3@example.com");
        customer.setActive(true);
        int customerId = customerDAO.createCustomer(customer);

        Customer retrievedCustomer = customerDAO.getCustomerByCustomerId(customerId);
        retrievedCustomer.setActive(false);
        customerDAO.updateCustomer(retrievedCustomer);

        Customer deactivatedCustomer = customerDAO.getCustomerByCustomerId(customerId);

        Assert.assertFalse(deactivatedCustomer.getActive());
        customerDAO.deleteCustomer(customerId);
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
        customer.setEmail("john4@example.com");
        customer.setActive(false);
        int customerId = customerDAO.createCustomer(customer);

        Customer retrievedCustomer = customerDAO.getCustomerByCustomerId(customerId);
        retrievedCustomer.setActive(false);
        customerDAO.updateCustomer(retrievedCustomer);

        Customer deactivatedCustomer = customerDAO.getCustomerByCustomerId(customerId);

        Assert.assertFalse(deactivatedCustomer.getActive());
        customerDAO.deleteCustomer(customerId);
    }

    @Test
    public void testDeleteCustomer() {
        Customer customer = new Customer();
        customer.setFullName("John Doe");
        customer.setCustomerType("Individual");
        customer.setAddress("123 Street");
        customer.setEmail("john5@example.com");
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
        user.setEmail("jane2@example.com");
        user.setPassword("password");
        user.setPhone("9876543210");
        int userId = userDAO.createUser(user);

        Customer customer = new Customer();
        customer.setUserId(userId);
        customer.setFullName("Jane Smith");
        customer.setCustomerType("Individual");
        customer.setAddress("456 Street");
        customer.setEmail("jane2@example.com");
        customer.setActive(true);
        int customerId = customerDAO.createCustomer(customer);

        customerDAO.deleteCustomer(customerId);
        userDAO.deleteUser(userId);

        Customer deletedCustomer = customerDAO.getCustomerByCustomerId(customerId);
        User associatedUser = userDAO.getUserById(userId);

        Assert.assertNull(deletedCustomer);
        Assert.assertNull(associatedUser);
    }

    @Test
    public void testViewCustomerRecord() {
        Customer customer = new Customer();
        customer.setFullName("John Doe");
        customer.setCustomerType("Individual");
        customer.setAddress("123 Street");
        customer.setEmail("john5@example.com");
        customer.setActive(true);
        int customerId = customerDAO.createCustomer(customer);

        Customer customerViewed = customerDAO.getCustomerByCustomerId(customerId);
        Assert.assertEquals(customerId, customerViewed.getCustomerId());
        customerDAO.deleteCustomer(customerId);
    }

    @Test
    public void testViewCustomerList() {
        Customer customer = new Customer();
        customer.setFullName("John Doe");
        customer.setCustomerType("Individual");
        customer.setAddress("123 Street");
        customer.setEmail("john6@example.com");
        customer.setActive(true);
        int customerId = customerDAO.createCustomer(customer);

        Customer customer2 = new Customer();
        customer2.setFullName("Jane Smith");
        customer2.setCustomerType("Individual");
        customer2.setAddress("456 Street");
        customer2.setEmail("jane3@example.com");
        customer2.setActive(true);
        int customerId2 = customerDAO.createCustomer(customer2);

        List<Customer> customers = customerDAO.getAllCustomers();

        Assert.assertFalse(customers.isEmpty());

        customerDAO.deleteCustomer(customerId);
        customerDAO.deleteCustomer(customerId2);
    }

    @Test
    public void testSearchCustomerByName() {
        Customer customer = new Customer();
        customer.setFullName("John Doe");
        customer.setCustomerType("Individual");
        customer.setAddress("123 Street");
        customer.setEmail("john7@example.com");
        customer.setActive(true);
        int customerId = customerDAO.createCustomer(customer);

        Customer customer2 = new Customer();
        customer2.setFullName("Jane Smith");
        customer2.setCustomerType("Individual");
        customer2.setAddress("456 Street");
        customer2.setEmail("jane4@example.com");
        customer2.setActive(true);
        int customerId2 = customerDAO.createCustomer(customer2);

        List<Customer> customers = customerDAO.searchCustomersByName("Jane Smith");

        Assert.assertEquals(1, customers.size());

        customerDAO.deleteCustomer(customerId);
        customerDAO.deleteCustomer(customerId2);
    }

    @Test
    public void testSearchCustomerByType() {
        Customer customer = new Customer();
        customer.setFullName("John Doe");
        customer.setCustomerType("Individual");
        customer.setAddress("123 Street");
        customer.setEmail("john8@example.com");
        customer.setActive(true);
        int customerId = customerDAO.createCustomer(customer);

        Customer customer2 = new Customer();
        customer2.setFullName("Jane Smith");
        customer2.setCustomerType("Individual");
        customer2.setAddress("456 Street");
        customer2.setEmail("jane5@example.com");
        customer2.setActive(true);
        int customerId2 = customerDAO.createCustomer(customer2);

        Customer customer3 = new Customer();
        customer3.setFullName("Mike Doe");
        customer3.setCustomerType("Company");
        customer3.setAddress("234 Street");
        customer3.setEmail("mike@example.com");
        customer3.setActive(true);
        int customerId3 = customerDAO.createCustomer(customer3);

        Customer customer4 = new Customer();
        customer4.setFullName("Jess Smith");
        customer4.setCustomerType("Individual");
        customer4.setAddress("789 Street");
        customer4.setEmail("jess@example.com");
        customer4.setActive(true);
        int customerId4 = customerDAO.createCustomer(customer4);

        List<Customer> customers = customerDAO.searchCustomersByType("Company");

        Assert.assertEquals(1, customers.size());

        customerDAO.deleteCustomer(customerId);
        customerDAO.deleteCustomer(customerId2);
        customerDAO.deleteCustomer(customerId3);
        customerDAO.deleteCustomer(customerId4);
    }
}

