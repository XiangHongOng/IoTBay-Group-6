package org.project.iotprojecttest.model.dao;

import org.project.iotprojecttest.DBConnector;
import org.project.iotprojecttest.model.objects.Staff;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StaffDAO {

    public void createStaff(Staff staff) {
        String query = "INSERT INTO staff (UserID, FullName, Position, Address) VALUES (?, ?, ?, ?)";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, staff.getUserId());
            statement.setString(2, staff.getFullName());
            statement.setString(3, staff.getPosition());
            statement.setString(4, staff.getAddress());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Staff getStaffById(int staffId) {
        String query = "SELECT * FROM staff WHERE StaffID = ?";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, staffId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Staff staff = new Staff();
                staff.setStaffId(resultSet.getInt("StaffID"));
                staff.setUserId(resultSet.getInt("UserID"));
                staff.setFullName(resultSet.getString("FullName"));
                staff.setPosition(resultSet.getString("Position"));
                staff.setAddress(resultSet.getString("Address"));
                return staff;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Staff> getAllStaff() {
        List<Staff> staffList = new ArrayList<>();
        String query = "SELECT * FROM staff";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Staff staff = new Staff();
                staff.setStaffId(resultSet.getInt("StaffID"));
                staff.setUserId(resultSet.getInt("UserID"));
                staff.setFullName(resultSet.getString("FullName"));
                staff.setPosition(resultSet.getString("Position"));
                staff.setAddress(resultSet.getString("Address"));
                staffList.add(staff);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return staffList;
    }

    public List<Staff> searchStaffByName(String name) {
        List<Staff> staffList = new ArrayList<>();
        String query = "SELECT * FROM staff WHERE FullName LIKE ?";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, "%" + name + "%");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Staff staff = new Staff();
                staff.setStaffId(resultSet.getInt("StaffID"));
                staff.setUserId(resultSet.getInt("UserID"));
                staff.setFullName(resultSet.getString("FullName"));
                staff.setPosition(resultSet.getString("Position"));
                staff.setAddress(resultSet.getString("Address"));
                staffList.add(staff);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return staffList;
    }

    public Staff getStaffByUserId(int userId) {
        String query = "SELECT * FROM Staff WHERE UserID = ?";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Staff staff = new Staff();
                staff.setStaffId(resultSet.getInt("StaffID"));
                staff.setUserId(resultSet.getInt("UserID"));
                staff.setFullName(resultSet.getString("FullName"));
                staff.setPosition(resultSet.getString("Position"));
                staff.setAddress(resultSet.getString("Address"));
                return staff;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isUserStaff(int userId) {
        String query = "SELECT COUNT(*) FROM Staff WHERE UserID = ?";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void updateStaff(Staff staff) {
        String query = "UPDATE staff SET FullName = ?, Position = ?, Address = ? WHERE StaffID = ?";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, staff.getFullName());
            statement.setString(2, staff.getPosition());
            statement.setString(3, staff.getAddress());
            statement.setInt(4, staff.getStaffId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteStaff(int staffId) {
        String query = "DELETE FROM staff WHERE StaffID = ?";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, staffId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}