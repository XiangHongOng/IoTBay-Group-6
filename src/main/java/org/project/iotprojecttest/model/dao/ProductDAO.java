package org.project.iotprojecttest.model.dao;

import org.project.iotprojecttest.DBConnector;
import org.project.iotprojecttest.model.objects.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    public int createProduct(Product product) {
        String query = "INSERT INTO products (ProductName, ProductType, UnitPrice, Quantity) VALUES (?, ?, ?, ?)";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, product.getProductName());
            statement.setString(2, product.getProductType());
            statement.setDouble(3, product.getUnitPrice());
            statement.setInt(4, product.getQuantity());
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

    public Product getProductById(int productId) {
        String query = "SELECT * FROM products WHERE ProductID = ?";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, productId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Product product = new Product();
                product.setProductId(resultSet.getInt("ProductID"));
                product.setProductName(resultSet.getString("ProductName"));
                product.setProductType(resultSet.getString("ProductType"));
                product.setUnitPrice(resultSet.getDouble("UnitPrice"));
                product.setQuantity(resultSet.getInt("Quantity"));
                return product;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM products";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                product.setProductId(resultSet.getInt("ProductID"));
                product.setProductName(resultSet.getString("ProductName"));
                product.setProductType(resultSet.getString("ProductType"));
                product.setUnitPrice(resultSet.getDouble("UnitPrice"));
                product.setQuantity(resultSet.getInt("Quantity"));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public void updateProduct(Product product) {
        String query = "UPDATE products SET ProductName = ?, ProductType = ?, UnitPrice = ?, Quantity = ? WHERE ProductID = ?";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, product.getProductName());
            statement.setString(2, product.getProductType());
            statement.setDouble(3, product.getUnitPrice());
            statement.setInt(4, product.getQuantity());
            statement.setInt(5, product.getProductId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteProduct(int productId) {
        String query = "DELETE FROM products WHERE ProductID = ?";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, productId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Product> searchProductsByName(String name) {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM products WHERE ProductName LIKE ?";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, "%" + name + "%");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                product.setProductId(resultSet.getInt("ProductID"));
                product.setProductName(resultSet.getString("ProductName"));
                product.setProductType(resultSet.getString("ProductType"));
                product.setUnitPrice(resultSet.getDouble("UnitPrice"));
                product.setQuantity(resultSet.getInt("Quantity"));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public List<Product> searchProductsByType(String type) {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM products WHERE ProductType = ?";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, type);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                product.setProductId(resultSet.getInt("ProductID"));
                product.setProductName(resultSet.getString("ProductName"));
                product.setProductType(resultSet.getString("ProductType"));
                product.setUnitPrice(resultSet.getDouble("UnitPrice"));
                product.setQuantity(resultSet.getInt("Quantity"));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public void updateProductStock(int productId, int quantity) {
        String query = "UPDATE products SET Quantity = Quantity - ? WHERE ProductID = ?";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, quantity);
            statement.setInt(2, productId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void restoreProductStock(int productId, int quantity) {
        String query = "UPDATE products SET Quantity = Quantity + ? WHERE ProductID = ?";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, quantity);
            statement.setInt(2, productId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}