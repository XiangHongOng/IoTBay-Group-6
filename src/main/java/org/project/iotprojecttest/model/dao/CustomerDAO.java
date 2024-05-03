package org.project.iotprojecttest.model.dao;

import org.project.iotprojecttest.DBConnector;
import org.project.iotprojecttest.model.objects.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

public class CustomerDAO {

    public int createCustomer(Customer customer) {
        String query = "INSERT INTO Customers (UserID, CustomerType, Address, Email, IsActive) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            if (customer.getUserId() != 0) {
                statement.setInt(1, customer.getUserId());
            } else {
                statement.setNull(1, Types.INTEGER);
            }
            statement.setString(2, customer.getCustomerType());
            statement.setString(3, customer.getAddress());
            statement.setString(4, customer.getEmail());
            statement.setBoolean(5, customer.getActive());
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


    public Customer getCustomerByUserId(int userId) {
        String query = "SELECT * FROM Customers WHERE UserID = ?";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Customer customer = new Customer();
                customer.setCustomerId(resultSet.getInt("CustomerID"));
                customer.setUserId(resultSet.getInt("UserID"));
                customer.setCustomerType(resultSet.getString("CustomerType"));
                customer.setAddress(resultSet.getString("Address"));
                customer.setEmail(resultSet.getString("Email"));
                customer.setActive(resultSet.getBoolean("IsActive"));
                return customer;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Customer getCustomerByCustomerId(int customerId) {
        String query = "SELECT * FROM Customers WHERE CustomerID = ?";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, customerId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Customer customer = new Customer();
                customer.setCustomerId(resultSet.getInt("CustomerID"));
                customer.setUserId(resultSet.getInt("UserID"));
                customer.setCustomerType(resultSet.getString("CustomerType"));
                customer.setAddress(resultSet.getString("Address"));
                customer.setEmail(resultSet.getString("Email"));
                customer.setActive(resultSet.getBoolean("IsActive"));
                return customer;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*public Customer getCustomerByEmail(String email) {
        String query = "SELECT c.* FROM Customers c INNER JOIN Users u ON c.UserID = u.UserID WHERE u.Email = ?";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Customer customer = new Customer();
                customer.setCustomerId(resultSet.getInt("CustomerID"));
                customer.setUserId(resultSet.getInt("UserID"));
                customer.setCustomerType(resultSet.getString("CustomerType"));
                customer.setAddress(resultSet.getString("Address"));
                customer.setEmail(email);
                return customer;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
     */

    public Customer getCustomerByEmail(String email) {
        String query = "SELECT * FROM Customers WHERE Email = ?";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Customer customer = new Customer();
                customer.setCustomerId(resultSet.getInt("CustomerID"));
                customer.setUserId(resultSet.getInt("UserID"));
                customer.setCustomerType(resultSet.getString("CustomerType"));
                customer.setAddress(resultSet.getString("Address"));
                customer.setEmail(resultSet.getString("Email"));
                customer.setActive(resultSet.getBoolean("IsActive"));
                return customer;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isCustomerActive(int customerId) {
        String query = "SELECT IsActive FROM Customers WHERE CustomerID = ?";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, customerId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getBoolean("IsActive");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void updateCustomer(Customer customer) {
        String query = "UPDATE Customers SET UserID = ?, CustomerType = ?, Address = ?, Email = ?, IsActive = ? WHERE CustomerID = ?";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            if (customer.getUserId() != 0) {
                statement.setInt(1, customer.getUserId());
            } else {
                statement.setNull(1, Types.INTEGER);
            }
            statement.setString(2, customer.getCustomerType());
            statement.setString(3, customer.getAddress());
            statement.setString(4, customer.getEmail());
            statement.setBoolean(5, customer.getActive());
            statement.setInt(6, customer.getCustomerId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}