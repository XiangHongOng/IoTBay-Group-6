package org.project.iotprojecttest.model.dao;

import org.project.iotprojecttest.DBConnector;
import org.project.iotprojecttest.model.objects.Payment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAO {

    public int createPayment(Payment payment) {
        String query = "INSERT INTO payments (PaymentMethod, CreditCardDetails, Amount, PaymentDate, Status, OrderID) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, payment.getPaymentMethod());
            statement.setString(2, payment.getCreditCardDetails());
            statement.setDouble(3, payment.getAmount());
            statement.setDate(4, new java.sql.Date(payment.getPaymentDate().getTime()));
            statement.setString(5, payment.getStatus());
            statement.setInt(6, payment.getOrderId());
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public List<Payment> getAllPayments() {
        List<Payment> payments = new ArrayList<>();
        String query = "SELECT * FROM payments";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Payment payment = new Payment();
                payment.setPaymentId(resultSet.getInt("PaymentID"));
                payment.setPaymentMethod(resultSet.getString("PaymentMethod"));
                payment.setCreditCardDetails(resultSet.getString("CreditCardDetails"));
                payment.setAmount(resultSet.getDouble("Amount"));
                payment.setPaymentDate(resultSet.getDate("PaymentDate"));
                payment.setStatus(resultSet.getString("Status"));
                payment.setOrderId(resultSet.getInt("OrderID"));
                payments.add(payment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return payments;
    }

    public Payment getPaymentById(int paymentId) {
        String query = "SELECT * FROM payments WHERE PaymentID = ?";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, paymentId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Payment payment = new Payment();
                payment.setPaymentId(resultSet.getInt("PaymentID"));
                payment.setPaymentMethod(resultSet.getString("PaymentMethod"));
                payment.setCreditCardDetails(resultSet.getString("CreditCardDetails"));
                payment.setAmount(resultSet.getDouble("Amount"));
                payment.setPaymentDate(resultSet.getDate("PaymentDate"));
                payment.setStatus(resultSet.getString("Status"));
                payment.setOrderId(resultSet.getInt("OrderID"));
                return payment;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Payment> getPaymentsByOrderId(int orderId) {
        List<Payment> payments = new ArrayList<>();
        String query = "SELECT * FROM payments WHERE OrderID = ?";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, orderId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Payment payment = new Payment();
                payment.setPaymentId(resultSet.getInt("PaymentID"));
                payment.setPaymentMethod(resultSet.getString("PaymentMethod"));
                payment.setCreditCardDetails(resultSet.getString("CreditCardDetails"));
                payment.setAmount(resultSet.getDouble("Amount"));
                payment.setPaymentDate(resultSet.getDate("PaymentDate"));
                payment.setStatus(resultSet.getString("Status"));
                payment.setOrderId(resultSet.getInt("OrderID"));
                payments.add(payment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return payments;
    }

    public double getTotalPaidAmountByOrderId(int orderId) {
        double totalPaid = 0.0;
        String query = "SELECT SUM(Amount) AS TotalPaid FROM payments WHERE OrderID = ? AND Status = 'Paid'";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, orderId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                totalPaid = resultSet.getDouble("TotalPaid");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalPaid;
    }

    public boolean updatePayment(Payment payment) {
        String query = "UPDATE payments SET PaymentMethod = ?, CreditCardDetails = ?, Amount = ?, PaymentDate = ?, Status = ?, OrderID = ? WHERE PaymentID = ?";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, payment.getPaymentMethod());
            statement.setString(2, payment.getCreditCardDetails());
            statement.setDouble(3, payment.getAmount());
            statement.setDate(4, new java.sql.Date(payment.getPaymentDate().getTime()));
            statement.setString(5, payment.getStatus());
            statement.setInt(6, payment.getOrderId());
            statement.setInt(7, payment.getPaymentId());
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletePayment(int paymentId) {
        String query = "DELETE FROM payments WHERE PaymentID = ?";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, paymentId);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Payment> searchPaymentsByDate(Date date) {
        List<Payment> payments = new ArrayList<>();
        String query = "SELECT * FROM payments WHERE PaymentDate = ?";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDate(1, new java.sql.Date(date.getTime()));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Payment payment = new Payment();
                payment.setPaymentId(resultSet.getInt("PaymentID"));
                payment.setPaymentMethod(resultSet.getString("PaymentMethod"));
                payment.setCreditCardDetails(resultSet.getString("CreditCardDetails"));
                payment.setAmount(resultSet.getDouble("Amount"));
                payment.setPaymentDate(resultSet.getDate("PaymentDate"));
                payment.setStatus(resultSet.getString("Status"));
                payment.setOrderId(resultSet.getInt("OrderID"));
                payments.add(payment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return payments;
    }

    public List<Payment> searchPaymentsByPaymentIdAndCustomerId(int paymentId, int customerId) {
        List<Payment> payments = new ArrayList<>();
        String query = "SELECT * FROM payments WHERE PaymentID = ? AND OrderID IN (SELECT OrderID FROM orders WHERE CustomerID = ?)";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, paymentId);
            statement.setInt(2, customerId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Payment payment = new Payment();
                payment.setPaymentId(resultSet.getInt("PaymentID"));
                payment.setPaymentMethod(resultSet.getString("PaymentMethod"));
                payment.setCreditCardDetails(resultSet.getString("CreditCardDetails"));
                payment.setAmount(resultSet.getDouble("Amount"));
                payment.setPaymentDate(resultSet.getDate("PaymentDate"));
                payment.setStatus(resultSet.getString("Status"));
                payment.setOrderId(resultSet.getInt("OrderID"));
                payments.add(payment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return payments;
    }

    public List<Payment> getPaymentsByCustomerId(int customerId) {
        List<Payment> payments = new ArrayList<>();
        String query = "SELECT * FROM payments WHERE OrderID IN (SELECT OrderID FROM orders WHERE CustomerID = ?)";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, customerId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Payment payment = new Payment();
                payment.setPaymentId(resultSet.getInt("PaymentID"));
                payment.setPaymentMethod(resultSet.getString("PaymentMethod"));
                payment.setCreditCardDetails(resultSet.getString("CreditCardDetails"));
                payment.setAmount(resultSet.getDouble("Amount"));
                payment.setPaymentDate(resultSet.getDate("PaymentDate"));
                payment.setStatus(resultSet.getString("Status"));
                payment.setOrderId(resultSet.getInt("OrderID"));
                payments.add(payment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return payments;
    }

    public Payment getPaymentByIdAndCustomerId(int paymentId, int customerId) {
        String query = "SELECT * FROM payments WHERE PaymentID = ? AND OrderID IN (SELECT OrderID FROM orders WHERE CustomerID = ?)";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, paymentId);
            statement.setInt(2, customerId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Payment payment = new Payment();
                payment.setPaymentId(resultSet.getInt("PaymentID"));
                payment.setPaymentMethod(resultSet.getString("PaymentMethod"));
                payment.setCreditCardDetails(resultSet.getString("CreditCardDetails"));
                payment.setAmount(resultSet.getDouble("Amount"));
                payment.setPaymentDate(resultSet.getDate("PaymentDate"));
                payment.setStatus(resultSet.getString("Status"));
                payment.setOrderId(resultSet.getInt("OrderID"));
                return payment;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Payment> searchPaymentsByDateAndCustomerId(java.sql.Date paymentDate, int customerId) {
        List<Payment> payments = new ArrayList<>();
        String query = "SELECT * FROM payments WHERE PaymentDate = ? AND OrderID IN (SELECT OrderID FROM orders WHERE CustomerID = ?)";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDate(1, paymentDate);
            statement.setInt(2, customerId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Payment payment = new Payment();
                payment.setPaymentId(resultSet.getInt("PaymentID"));
                payment.setPaymentMethod(resultSet.getString("PaymentMethod"));
                payment.setCreditCardDetails(resultSet.getString("CreditCardDetails"));
                payment.setAmount(resultSet.getDouble("Amount"));
                payment.setPaymentDate(resultSet.getDate("PaymentDate"));
                payment.setStatus(resultSet.getString("Status"));
                payment.setOrderId(resultSet.getInt("OrderID"));
                payments.add(payment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return payments;
    }

    public Payment getPaymentByOrderId(int orderId) {
        String query = "SELECT * FROM payments WHERE OrderID = ?";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, orderId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Payment payment = new Payment();
                payment.setPaymentId(resultSet.getInt("PaymentID"));
                payment.setPaymentMethod(resultSet.getString("PaymentMethod"));
                payment.setCreditCardDetails(resultSet.getString("CreditCardDetails"));
                payment.setAmount(resultSet.getDouble("Amount"));
                payment.setPaymentDate(resultSet.getDate("PaymentDate"));
                payment.setOrderId(resultSet.getInt("OrderID"));
                payment.setStatus(resultSet.getString("Status"));
                return payment;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Payment getUnpaidPaymentByOrderId(int orderId) {
        String query = "SELECT * FROM payments WHERE OrderID = ? AND Status != 'Paid'";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, orderId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Payment payment = new Payment();
                payment.setPaymentId(resultSet.getInt("PaymentID"));
                payment.setPaymentMethod(resultSet.getString("PaymentMethod"));
                payment.setCreditCardDetails(resultSet.getString("CreditCardDetails"));
                payment.setAmount(resultSet.getDouble("Amount"));
                payment.setPaymentDate(resultSet.getDate("PaymentDate"));
                payment.setOrderId(resultSet.getInt("OrderID"));
                payment.setStatus(resultSet.getString("Status"));
                return payment;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}