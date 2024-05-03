package org.project.iotprojecttest.model.dao;

import org.project.iotprojecttest.DBConnector;
import org.project.iotprojecttest.model.objects.Shipment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ShipmentDAO {

    public void createShipment(Shipment shipment) {
        String query = "INSERT INTO shipments (ShipmentMethod, ShipmentDate, ShipmentAddress, OrderID) VALUES (?, ?, ?, ?)";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, shipment.getShipmentMethod());
            statement.setDate(2, new java.sql.Date(shipment.getShipmentDate().getTime()));
            statement.setString(3, shipment.getShipmentAddress());
            statement.setInt(4, shipment.getOrderId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Shipment getShipmentById(int shipmentId) {
        String query = "SELECT * FROM shipments WHERE ShipmentID = ?";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, shipmentId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Shipment shipment = new Shipment();
                shipment.setShipmentId(resultSet.getInt("ShipmentID"));
                shipment.setShipmentMethod(resultSet.getString("ShipmentMethod"));
                shipment.setShipmentDate(resultSet.getDate("ShipmentDate"));
                shipment.setShipmentAddress(resultSet.getString("ShipmentAddress"));
                shipment.setOrderId(resultSet.getInt("OrderID"));
                return shipment;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Shipment> getShipmentsByOrderId(int orderId) {
        List<Shipment> shipments = new ArrayList<>();
        String query = "SELECT * FROM shipments WHERE OrderID = ?";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, orderId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Shipment shipment = new Shipment();
                shipment.setShipmentId(resultSet.getInt("ShipmentID"));
                shipment.setShipmentMethod(resultSet.getString("ShipmentMethod"));
                shipment.setShipmentDate(resultSet.getDate("ShipmentDate"));
                shipment.setShipmentAddress(resultSet.getString("ShipmentAddress"));
                shipment.setOrderId(resultSet.getInt("OrderID"));
                shipments.add(shipment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shipments;
    }

    public void updateShipment(Shipment shipment) {
        String query = "UPDATE shipments SET ShipmentMethod = ?, ShipmentDate = ?, ShipmentAddress = ?, OrderID = ? WHERE ShipmentID = ?";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, shipment.getShipmentMethod());
            statement.setDate(2, new java.sql.Date(shipment.getShipmentDate().getTime()));
            statement.setString(3, shipment.getShipmentAddress());
            statement.setInt(4, shipment.getOrderId());
            statement.setInt(5, shipment.getShipmentId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteShipment(int shipmentId) {
        String query = "DELETE FROM shipments WHERE ShipmentID = ?";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, shipmentId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Shipment> searchShipmentsByDate(Date date) {
        List<Shipment> shipments = new ArrayList<>();
        String query = "SELECT * FROM shipments WHERE ShipmentDate = ?";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDate(1, new java.sql.Date(date.getTime()));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Shipment shipment = new Shipment();
                shipment.setShipmentId(resultSet.getInt("ShipmentID"));
                shipment.setShipmentMethod(resultSet.getString("ShipmentMethod"));
                shipment.setShipmentDate(resultSet.getDate("ShipmentDate"));
                shipment.setShipmentAddress(resultSet.getString("ShipmentAddress"));
                shipment.setOrderId(resultSet.getInt("OrderID"));
                shipments.add(shipment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shipments;
    }

}