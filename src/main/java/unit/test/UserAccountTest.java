package unit.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.project.iotprojecttest.model.dao.UserDAO;
import org.project.iotprojecttest.model.objects.User;

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
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setPhone("1234567890");

        int userId = userDAO.createUser(user);

        Assert.assertTrue(userId > 0);
    }

}
