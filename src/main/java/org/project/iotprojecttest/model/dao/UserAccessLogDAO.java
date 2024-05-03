package org.project.iotprojecttest.model.dao;

import org.project.iotprojecttest.DBConnector;
import org.project.iotprojecttest.model.objects.UserAccessLog;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserAccessLogDAO {

    public void createUserAccessLog(UserAccessLog userAccessLog) {
        String query = "INSERT INTO useraccesslogs (UserID, LoginDateTime, LogoutDateTime) VALUES (?, ?, ?)";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userAccessLog.getUserId());
            statement.setTimestamp(2, userAccessLog.getLoginDateTime() != null ?
                    new java.sql.Timestamp(userAccessLog.getLoginDateTime().getTime()) : null);
            statement.setTimestamp(3, userAccessLog.getLogoutDateTime() != null ?
                    new java.sql.Timestamp(userAccessLog.getLogoutDateTime().getTime()) : null);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public UserAccessLog getUserAccessLogById(int logId) {
        String query = "SELECT * FROM useraccesslogs WHERE LogID = ?";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, logId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                UserAccessLog userAccessLog = new UserAccessLog();
                userAccessLog.setLogId(resultSet.getInt("LogID"));
                userAccessLog.setUserId(resultSet.getInt("UserID"));
                userAccessLog.setLoginDateTime(resultSet.getTimestamp("LoginDateTime"));
                userAccessLog.setLogoutDateTime(resultSet.getTimestamp("LogoutDateTime"));
                return userAccessLog;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<UserAccessLog> getUserAccessLogsByUserId(int userId) {
        List<UserAccessLog> userAccessLogs = new ArrayList<>();
        String query = "SELECT * FROM useraccesslogs WHERE UserID = ?";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                UserAccessLog userAccessLog = new UserAccessLog();
                userAccessLog.setLogId(resultSet.getInt("LogID"));
                userAccessLog.setUserId(resultSet.getInt("UserID"));
                userAccessLog.setLoginDateTime(resultSet.getTimestamp("LoginDateTime"));
                userAccessLog.setLogoutDateTime(resultSet.getTimestamp("LogoutDateTime"));
                userAccessLogs.add(userAccessLog);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userAccessLogs;
    }

    public List<UserAccessLog> searchUserAccessLogsByDate(java.util.Date date) {
        List<UserAccessLog> userAccessLogs = new ArrayList<>();
        String query = "SELECT * FROM useraccesslogs WHERE DATE(LoginDateTime) = ? OR DATE(LogoutDateTime) = ?";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            statement.setDate(1, sqlDate);
            statement.setDate(2, sqlDate);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                UserAccessLog userAccessLog = new UserAccessLog();
                userAccessLog.setLogId(resultSet.getInt("LogID"));
                userAccessLog.setUserId(resultSet.getInt("UserID"));
                userAccessLog.setLoginDateTime(resultSet.getTimestamp("LoginDateTime"));
                userAccessLog.setLogoutDateTime(resultSet.getTimestamp("LogoutDateTime"));
                userAccessLogs.add(userAccessLog);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userAccessLogs;
    }

}