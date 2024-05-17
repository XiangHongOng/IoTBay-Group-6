package org.project.iotprojecttest.model.dao;

import org.project.iotprojecttest.DBConnector;
import org.project.iotprojecttest.model.objects.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderDAO {

    public int createOrder(Order order) {
        String query = "INSERT INTO orders (CustomerID, OrderDate) VALUES (?, ?)";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            if (order.getCustomerId() != 0) {
                statement.setInt(1, order.getCustomerId());
            } else {
                statement.setNull(1, Types.INTEGER);
            }
            statement.setDate(2, new java.sql.Date(order.getOrderDate().getTime()));
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

    public Order getOrderById(int orderId) {
        String query = "SELECT * FROM orders WHERE OrderID = ?";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, orderId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Order order = new Order();
                order.setOrderId(resultSet.getInt("OrderID"));
                order.setCustomerId(resultSet.getInt("CustomerID"));
                order.setOrderDate(resultSet.getDate("OrderDate"));
                order.setStatus(resultSet.getString("Status"));
                return order;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM orders";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Order order = new Order();
                order.setOrderId(resultSet.getInt("OrderID"));
                order.setCustomerId(resultSet.getInt("CustomerID"));
                order.setOrderDate(resultSet.getDate("OrderDate"));
                order.setStatus(resultSet.getString("Status"));
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public boolean updateOrder(Order order) {
        String query = "UPDATE orders SET CustomerID = ?, OrderDate = ?, Status = ? WHERE OrderID = ?";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            if (order.getCustomerId() != 0) {
                statement.setInt(1, order.getCustomerId());
            } else {
                statement.setNull(1, Types.INTEGER);
            }
            statement.setDate(2, new java.sql.Date(order.getOrderDate().getTime()));
            statement.setString(3, order.getStatus());
            statement.setInt(4, order.getOrderId());
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void updateOrderStatus(int orderId, String status) {
        String query = "UPDATE orders SET Status = ? WHERE OrderID = ?";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, status);
            statement.setInt(2, orderId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteOrder(int orderId) {
        String query = "DELETE FROM orders WHERE OrderID = ?";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, orderId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Order> searchOrdersByCustomerId(int customerId) {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM orders WHERE CustomerID = ?";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, customerId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Order order = new Order();
                order.setOrderId(resultSet.getInt("OrderID"));
                order.setCustomerId(resultSet.getInt("CustomerID"));
                order.setOrderDate(resultSet.getDate("OrderDate"));
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public List<Order> searchOrdersByDate(Date date) {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM orders WHERE OrderDate = ?";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDate(1, new java.sql.Date(date.getTime()));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Order order = new Order();
                order.setOrderId(resultSet.getInt("OrderID"));
                order.setCustomerId(resultSet.getInt("CustomerID"));
                order.setOrderDate(resultSet.getDate("OrderDate"));
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public List<Order> getOrdersByCustomerId(int customerId) {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM orders WHERE CustomerID = ?";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, customerId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Order order = new Order();
                order.setOrderId(resultSet.getInt("OrderID"));
                order.setCustomerId(resultSet.getInt("CustomerID"));
                order.setOrderDate(resultSet.getDate("OrderDate"));
                order.setStatus(resultSet.getString("Status"));
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public List<Order> getOrdersByCustomerEmail(String customerEmail) {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT o.* FROM orders o JOIN customers c ON o.CustomerID = c.CustomerID WHERE c.Email = ?";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, customerEmail);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Order order = new Order();
                order.setOrderId(resultSet.getInt("OrderID"));
                order.setCustomerId(resultSet.getInt("CustomerID"));
                order.setOrderDate(resultSet.getDate("OrderDate"));
                order.setStatus(resultSet.getString("Status"));
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }


    public Order getOrderByOrderNumberAndDate(String orderNumber, String orderDate) {
        String query = "SELECT * FROM orders WHERE OrderID = ? AND OrderDate = ?";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, orderNumber);
            statement.setString(2, orderDate);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Order order = new Order();
                order.setOrderId(resultSet.getInt("OrderID"));
                order.setCustomerId(resultSet.getInt("CustomerID"));
                order.setOrderDate(resultSet.getDate("OrderDate"));
                order.setStatus(resultSet.getString("Status"));
                return order;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Order getOrderByAnonymousCustomerEmailAndOrderId(String anonymousCustomerEmail, String orderNumber, String orderDate) {
        String query = "SELECT * FROM orders WHERE AnonymousCustomerEmail = ? AND OrderID = ? AND OrderDate = ?";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, anonymousCustomerEmail);
            statement.setString(2, orderNumber);
            statement.setString(3, orderDate);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Order order = new Order();
                order.setOrderId(resultSet.getInt("OrderID"));
                order.setCustomerId(resultSet.getInt("CustomerID"));
                order.setOrderDate(resultSet.getDate("OrderDate"));
                order.setStatus(resultSet.getString("Status"));
                return order;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Order getOrderByIdAndCustomerId(int orderId, int customerId) {
        String query = "SELECT * FROM orders WHERE OrderID = ? AND CustomerID = ?";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, orderId);
            statement.setInt(2, customerId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Order order = new Order();
                order.setOrderId(resultSet.getInt("OrderID"));
                order.setCustomerId(resultSet.getInt("CustomerID"));
                order.setOrderDate(resultSet.getDate("OrderDate"));
                order.setStatus(resultSet.getString("Status"));
                return order;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Order> searchOrdersByDateAndCustomerId(java.sql.Date orderDate, int customerId) {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM orders WHERE OrderDate = ? AND CustomerID = ?";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDate(1, orderDate);
            statement.setInt(2, customerId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Order order = new Order();
                order.setOrderId(resultSet.getInt("OrderID"));
                order.setCustomerId(resultSet.getInt("CustomerID"));
                order.setOrderDate(resultSet.getDate("OrderDate"));
                order.setStatus(resultSet.getString("Status"));
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public List<Order> getUnpaidOrdersByCustomerId(int customerId) {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM orders WHERE CustomerID = ? AND Status = 'Submitted'";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, customerId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Order order = new Order();
                order.setOrderId(resultSet.getInt("OrderID"));
                order.setOrderDate(resultSet.getDate("OrderDate"));
                order.setCustomerId(resultSet.getInt("CustomerID"));
                order.setStatus(resultSet.getString("Status"));
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public List<Order> getUnpaidOrdersByCustomerIdAndDate(int customerId, java.sql.Date orderDate) {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM orders WHERE CustomerID = ? AND OrderDate = ? AND Status = 'Submitted'";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, customerId);
            statement.setDate(2, orderDate);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Order order = new Order();
                order.setOrderId(resultSet.getInt("OrderID"));
                order.setCustomerId(resultSet.getInt("CustomerID"));
                order.setOrderDate(resultSet.getDate("OrderDate"));
                order.setStatus(resultSet.getString("Status"));
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public double calculateOrderTotalAmount(int orderId) {
        double totalAmount = 0.0;
        String query = "SELECT SUM(p.UnitPrice * oli.OrderedQuantity) AS TotalAmount " +
                "FROM OrderLineItems oli " +
                "JOIN Products p ON oli.ProductID = p.ProductID " +
                "WHERE oli.OrderID = ?";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, orderId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                totalAmount = resultSet.getDouble("TotalAmount");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        totalAmount = Math.round(totalAmount * 100.0) / 100.0;
        return totalAmount;
    }

}