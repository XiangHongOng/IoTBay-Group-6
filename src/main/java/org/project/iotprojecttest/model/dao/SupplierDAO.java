package org.project.iotprojecttest.model.dao;

import org.project.iotprojecttest.DBConnector;
import org.project.iotprojecttest.model.objects.Supplier;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAO {

    public void createSupplier(Supplier supplier) {
        String query = "INSERT INTO suppliers (ContactName, Company, Email, Address) VALUES (?, ?, ?, ?)";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, supplier.getContactName());
            statement.setString(2, supplier.getCompany());
            statement.setString(3, supplier.getEmail());
            statement.setString(4, supplier.getAddress());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Supplier getSupplierById(int supplierId) {
        String query = "SELECT * FROM suppliers WHERE SupplierID = ?";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, supplierId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Supplier supplier = new Supplier();
                supplier.setSupplierId(resultSet.getInt("SupplierID"));
                supplier.setContactName(resultSet.getString("ContactName"));
                supplier.setCompany(resultSet.getString("Company"));
                supplier.setEmail(resultSet.getString("Email"));
                supplier.setAddress(resultSet.getString("Address"));
                return supplier;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Supplier> getAllSuppliers() {
        List<Supplier> suppliers = new ArrayList<>();
        String query = "SELECT * FROM suppliers";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Supplier supplier = new Supplier();
                supplier.setSupplierId(resultSet.getInt("SupplierID"));
                supplier.setContactName(resultSet.getString("ContactName"));
                supplier.setCompany(resultSet.getString("Company"));
                supplier.setEmail(resultSet.getString("Email"));
                supplier.setAddress(resultSet.getString("Address"));
                suppliers.add(supplier);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return suppliers;
    }

    public void updateSupplier(Supplier supplier) {
        String query = "UPDATE suppliers SET ContactName = ?, Company = ?, Email = ?, Address = ? WHERE SupplierID = ?";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, supplier.getContactName());
            statement.setString(2, supplier.getCompany());
            statement.setString(3, supplier.getEmail());
            statement.setString(4, supplier.getAddress());
            statement.setInt(5, supplier.getSupplierId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteSupplier(int supplierId) {
        String query = "DELETE FROM suppliers WHERE SupplierID = ?";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, supplierId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Supplier> searchSuppliersByContactName(String contactName) {
        List<Supplier> suppliers = new ArrayList<>();
        String query = "SELECT * FROM suppliers WHERE ContactName LIKE ?";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, "%" + contactName + "%");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Supplier supplier = new Supplier();
                supplier.setSupplierId(resultSet.getInt("SupplierID"));
                supplier.setContactName(resultSet.getString("ContactName"));
                supplier.setCompany(resultSet.getString("Company"));
                supplier.setEmail(resultSet.getString("Email"));
                supplier.setAddress(resultSet.getString("Address"));
                suppliers.add(supplier);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return suppliers;
    }

    public List<Supplier> searchSuppliersByCompany(String company) {
        List<Supplier> suppliers = new ArrayList<>();
        String query = "SELECT * FROM suppliers WHERE Company LIKE ?";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, "%" + company + "%");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Supplier supplier = new Supplier();
                supplier.setSupplierId(resultSet.getInt("SupplierID"));
                supplier.setContactName(resultSet.getString("ContactName"));
                supplier.setCompany(resultSet.getString("Company"));
                supplier.setEmail(resultSet.getString("Email"));
                supplier.setAddress(resultSet.getString("Address"));
                suppliers.add(supplier);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return suppliers;
    }

}