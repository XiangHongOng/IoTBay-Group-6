package unit.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.project.iotprojecttest.model.dao.UserDAO;
import org.project.iotprojecttest.model.objects.Customer;
import org.project.iotprojecttest.model.objects.User;
import java.util.ArrayList;
import java.util.List;

public class UserAccountTest
{
    private UserDAO userDAO;

    @Before
    public void setup() {
        userDAO = new UserDAO();
    }

    @Test
    public void testCreateUser() {
        User user = new User();
        user.setEmail("jim@example.com");
        user.setPassword("password");
        user.setPhone("1234567890");

        UserDAO userDAO = new UserDAO();
        int userId = userDAO.createUser(user);

        Assert.assertNotEquals(0, userId);

        User createdUser = userDAO.getUserById(userId);

        userDAO.deleteUser(userId);

    }

    @Test
    public void testGetUserById() {
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUserById(1);

        Assert.assertNotNull(user);
        Assert.assertEquals(1, user.getUserId());
    }

    @Test
    public void testGetNumberOfUsers(){
        UserDAO userDAO = new UserDAO();
        List<User> users = userDAO.getAllUsers();
        Assert.assertEquals(25, users.size());
    }

    @Test
    public void testVerifyUserExists(){
        UserDAO userDAO = new UserDAO();
        User user = new User();

        user.setEmail("jerry@example.com");
        user.setPassword("password");
        user.setPhone("1234567890");

        int userId = userDAO.createUser(user);

        Assert.assertTrue(userDAO.verifyUser(user.getEmail(), user.getPassword()));

        userDAO.deleteUser(userId);
    }

    @Test
    public void testVerifyUserNotExists(){
        Assert.assertFalse(userDAO.verifyUser("notanemail@example.com", "badPassword"));
    }

    @Test
    public void testEmailDoesNotExist(){
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUserByEmail("abcdefg@gmail.com");
        Assert.assertNull(user);
    }

    @Test
    public void testEmailDoesExist(){
        UserDAO userDAO = new UserDAO();
        User user = new User();

        user.setEmail("jerry@example.com");
        user.setPassword("password");
        user.setPhone("1234567890");

        int userId = userDAO.createUser(user);

        User userFound = userDAO.getUserByEmail("jerry@example.com");

        Assert.assertNotNull(userFound);

        userDAO.deleteUser(userId);
    }

    @Test
    public void testUpdateUser() {
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUserById(1);

        user.setEmail("jane@example.com");
        user.setPassword("newpassword");
        user.setPhone("9876543210");

        boolean updated = userDAO.updateUser(user);

        Assert.assertTrue(updated);
    }

    @Test
    public void testDeleteUser() {
        UserDAO userDAO = new UserDAO();
        User user = new User();
        user.setEmail("jeremy@example.com");
        user.setPassword("password");
        user.setPhone("1234567890");

        int userId = userDAO.createUser(user);

        userDAO.deleteUser(userId);

        User deletedUser = userDAO.getUserById(userId);
        Assert.assertNull(deletedUser);
    }

}
