package org.project.iotprojecttest.model.dao;

import org.project.iotprojecttest.DBConnector;
import org.project.iotprojecttest.model.objects.OrderLineItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderLineItemDAO {

    public void createOrderLineItem(OrderLineItem orderLineItem) {
        String query = "INSERT INTO orderlineitems (OrderID, ProductID, OrderedQuantity) VALUES (?, ?, ?)";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, orderLineItem.getOrderId());
            statement.setInt(2, orderLineItem.getProductId());
            statement.setInt(3, orderLineItem.getOrderedQuantity());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public OrderLineItem getOrderLineItemById(int orderLineId) {
        String query = "SELECT * FROM orderlineitems WHERE OrderLineID = ?";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, orderLineId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                OrderLineItem orderLineItem = new OrderLineItem();
                orderLineItem.setOrderLineId(resultSet.getInt("OrderLineID"));
                orderLineItem.setOrderId(resultSet.getInt("OrderID"));
                orderLineItem.setProductId(resultSet.getInt("ProductID"));
                orderLineItem.setOrderedQuantity(resultSet.getInt("OrderedQuantity"));
                return orderLineItem;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<OrderLineItem> getOrderLineItemsByOrderId(int orderId) {
        List<OrderLineItem> orderLineItems = new ArrayList<>();
        String query = "SELECT * FROM orderlineitems WHERE OrderID = ?";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, orderId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                OrderLineItem orderLineItem = new OrderLineItem();
                orderLineItem.setOrderLineId(resultSet.getInt("OrderLineID"));
                orderLineItem.setOrderId(resultSet.getInt("OrderID"));
                orderLineItem.setProductId(resultSet.getInt("ProductID"));
                orderLineItem.setOrderedQuantity(resultSet.getInt("OrderedQuantity"));
                orderLineItems.add(orderLineItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderLineItems;
    }

    public void updateOrderLineItem(OrderLineItem orderLineItem) {
        String query = "UPDATE orderlineitems SET OrderID = ?, ProductID = ?, OrderedQuantity = ? WHERE OrderLineID = ?";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, orderLineItem.getOrderId());
            statement.setInt(2, orderLineItem.getProductId());
            statement.setInt(3, orderLineItem.getOrderedQuantity());
            statement.setInt(4, orderLineItem.getOrderLineId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteOrderLineItem(int orderLineId) {
        String query = "DELETE FROM orderlineitems WHERE OrderLineID = ?";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, orderLineId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}