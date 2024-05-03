-- Create Database (Schema)
CREATE DATABASE IF NOT EXISTS iotbay;
USE iotbay;

-- Create 'users' Table
CREATE TABLE Users (
                       UserID INT PRIMARY KEY AUTO_INCREMENT,
                       FullName VARCHAR(50) NOT NULL,
                       Email VARCHAR(50) NOT NULL UNIQUE,
                       Password VARCHAR(20) NOT NULL,
                       Phone VARCHAR(15) NOT NULL
);

CREATE TABLE UserAccessLogs (
                                LogID INT PRIMARY KEY AUTO_INCREMENT,
                                UserID INT,
                                LoginDateTime DATETIME,
                                LogoutDateTime DATETIME,
                                FOREIGN KEY (UserID) REFERENCES Users(UserID) ON DELETE SET NULL
);

CREATE TABLE Customers (
                           CustomerID INT PRIMARY KEY AUTO_INCREMENT,
                           UserID INT UNIQUE,
                           CustomerType VARCHAR(20) NOT NULL,
                           Address VARCHAR(100),
                           Email VARCHAR(50),
                           IsActive bool NOT NULL DEFAULT true,
                           FOREIGN KEY (UserID) REFERENCES Users(UserID) ON DELETE SET NULL
);

CREATE TABLE Staff (
                       StaffID INT PRIMARY KEY AUTO_INCREMENT,
                       UserID INT UNIQUE,
                       Position VARCHAR(20) NOT NULL,
                       Address VARCHAR(100) NOT NULL,
                       FOREIGN KEY (UserID) REFERENCES Users(UserID) ON DELETE SET NULL
);

CREATE TABLE Suppliers (
                           SupplierID INT PRIMARY KEY AUTO_INCREMENT,
                           ContactName VARCHAR(50) NOT NULL,
                           Company VARCHAR(50) NOT NULL,
                           Email VARCHAR(50) NOT NULL UNIQUE,
                           Address VARCHAR(100) NOT NULL
);

CREATE TABLE Products (
                          ProductID INT PRIMARY KEY AUTO_INCREMENT,
                          ProductName VARCHAR(50) NOT NULL,
                          ProductType VARCHAR(20) NOT NULL,
                          UnitPrice DECIMAL(10, 2) NOT NULL,
                          Quantity INT NOT NULL
);

CREATE TABLE Orders (
                        OrderID INT PRIMARY KEY AUTO_INCREMENT,
                        CustomerID INT,
                        OrderDate DATE NOT NULL,
                        Status ENUM('Saved', 'Submitted', 'Paid', 'Cancelled') NOT NULL DEFAULT 'Saved',
                        FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID) ON DELETE SET NULL
);

CREATE TABLE OrderLineItems (
                                OrderLineID INT PRIMARY KEY AUTO_INCREMENT,
                                OrderID INT,
                                ProductID INT,
                                OrderedQuantity INT NOT NULL,
                                FOREIGN KEY (OrderID) REFERENCES Orders(OrderID) ON DELETE SET NULL,
                                FOREIGN KEY (ProductID) REFERENCES Products(ProductID) ON DELETE SET NULL
);

CREATE TABLE Payments (
                          PaymentID INT PRIMARY KEY AUTO_INCREMENT,
                          PaymentMethod VARCHAR(20) NOT NULL,
                          CreditCardDetails VARCHAR(50),
                          Amount DECIMAL(10, 2) NOT NULL,
                          PaymentDate DATE NOT NULL,
                          Status ENUM('Paid', 'Unpaid') NOT NULL DEFAULT 'Unpaid',
                          OrderID INT,
                          FOREIGN KEY (OrderID) REFERENCES Orders(OrderID) ON DELETE SET NULL
);

CREATE TABLE Shipments (
                           ShipmentID INT PRIMARY KEY AUTO_INCREMENT,
                           ShipmentMethod VARCHAR(20) NOT NULL,
                           ShipmentDate DATE NOT NULL,
                           ShipmentAddress VARCHAR(100) NOT NULL,
                           OrderID INT,
                           FOREIGN KEY (OrderID) REFERENCES Orders(OrderID) ON DELETE SET NULL
);

-- Insert data into Users table
INSERT INTO Users (FullName, Email, Password, Phone) VALUES
                                                         ('John Doe', 'john.doe@example.com', 'password123', '1234567890'),
                                                         ('Jane Smith', 'jane.smith@example.com', 'password456', '9876543210'),
                                                         ('Michael Johnson', 'michael.johnson@example.com', 'password789', '4561237890'),
                                                         ('Emily Davis', 'emily.davis@example.com', 'password012', '7890123456'),
                                                         ('Robert Wilson', 'robert.wilson@example.com', 'password345', '2345678901'),
                                                         ('Alice Brown', 'alice.brown@example.com', 'password678', '6789012345'), -- Add new user
                                                         ('Bob Thompson', 'bob.thompson@example.com', 'password901', '9012345678'), -- Add new user
                                                         ('Charlie Anderson', 'charlie.anderson@example.com', 'password234', '2345678901'), -- Add new user
                                                         ('David Taylor', 'david.taylor@example.com', 'password567', '5678901234'), -- Add new user
                                                         ('Eve Miller', 'eve.miller@example.com', 'password890', '8901234567'), -- Add new user
                                                         ('Cam', 'cam@cam.com', '123', '123');

-- Insert data into UserAccessLogs table
INSERT INTO UserAccessLogs (UserID, LoginDateTime, LogoutDateTime) VALUES
                                                                       (1, '2023-05-01 10:00:00', '2023-05-01 12:30:00'),
                                                                       (2, '2023-05-02 08:30:00', '2023-05-02 17:45:00'),
                                                                       (3, '2023-05-03 09:15:00', NULL),
                                                                       (4, '2023-05-04 11:20:00', '2023-05-04 14:50:00'),
                                                                       (5, '2023-05-05 13:00:00', '2023-05-05 16:10:00');

-- Insert data into Customers table
INSERT INTO Customers (UserID, CustomerType, Address, Email, IsActive) VALUES
                                                                           (1, 'Individual', '123 Main St, City, Country', null, true),
                                                                           (2, 'Individual', '456 Oak Ave, Town, Country', null, true),
                                                                           (3, 'Individual', '789 Elm St, Village, Country', null, true),
                                                                           (4, 'Individual', '321 Pine Rd, City, Country', 'robert.wilson@example.com', true),
                                                                           (5, 'Individual', '654 Maple Ln, Town, Country', null, false),
                                                                           (11, 'Individual', '', null, true);

-- Insert data into Staff table
INSERT INTO Staff (UserID, Position, Address) VALUES
                                                  (6, 'Manager', '987 Cedar Blvd, City, Country'),
                                                  (7, 'Sales Representative', '159 Birch Way, Town, Country'),
                                                  (8, 'Customer Support', '753 Willow Dr, Village, Country'),
                                                  (9, 'Shipping Coordinator', '246 Spruce Ave, City, Country'),
                                                  (10, 'Inventory Specialist', '840 Ash St, Town, Country'),
                                                  (11, 'Owner', 'No dox plz');

-- Insert data into Suppliers table
INSERT INTO Suppliers (ContactName, Company, Email, Address) VALUES
                                                                 ('Alice Smith', 'Tech Solutions Inc.', 'alice@techsolutions.com', '123 Industrial Way, City, Country'),
                                                                 ('Bob Johnson', 'Gadget Suppliers', 'bob@gadgetsuppliers.com', '456 Factory Rd, Town, Country'),
                                                                 ('Charlie Davis', 'Electronic Emporium', 'charlie@electronicemporium.com', '789 Commerce St, Village, Country'),
                                                                 ('David Wilson', 'Wireless Wonders', 'david@wirelesswonders.com', '321 Technology Ln, City, Country'),
                                                                 ('Eve Taylor', 'Smart Devices Corp.', 'eve@smartdevicescorp.com', '654 Innovation Blvd, Town, Country');

-- Insert data into Products table
INSERT INTO Products (ProductName, ProductType, UnitPrice, Quantity) VALUES
                                                                         ('Smart Watch', 'Wearable', 199.99, 50),
                                                                         ('Wireless Headphones', 'Audio', 99.99, 75),
                                                                         ('Smart Home Hub', 'Home Automation', 149.99, 30),
                                                                         ('Virtual Reality Headset', 'Gaming', 299.99, 40),
                                                                         ('Fitness Tracker', 'Wearable', 79.99, 60);

-- Insert data into Orders table
INSERT INTO Orders (CustomerID, OrderDate) VALUES
                                               (1, '2023-05-01'),
                                               (2, '2023-05-02'),
                                               (NULL, '2023-05-03'),
                                               (3, '2023-05-04'),
                                               (4, '2023-05-05');

-- Insert data into OrderLineItems table
INSERT INTO OrderLineItems (OrderID, ProductID, OrderedQuantity) VALUES
                                                                     (1, 1, 2),
                                                                     (1, 3, 1),
                                                                     (2, 2, 3),
                                                                     (3, 4, 1),
                                                                     (3, 5, 2),
                                                                     (4, 1, 1),
                                                                     (4, 3, 2),
                                                                     (5, 2, 2),
                                                                     (5, 5, 1);

-- Insert data into Payments table
INSERT INTO Payments (PaymentMethod, CreditCardDetails, Amount, PaymentDate, OrderID) VALUES
                                                                                          ('Credit Card', '1234567890123456', 349.98, '2023-05-01', 1),
                                                                                          ('Debit Card', '9876543210987654', 299.97, '2023-05-02', 2),
                                                                                          ('PayPal', NULL, 379.98, '2023-05-03', 3),
                                                                                          ('Credit Card', '5678901234567890', 449.97, '2023-05-04', 4),
                                                                                          ('Debit Card', '1098765432109876', 279.98, '2023-05-05', 5);

-- Insert data into Shipments table
INSERT INTO Shipments (ShipmentMethod, ShipmentDate, ShipmentAddress, OrderID) VALUES
                                                                                   ('Standard Shipping', '2023-05-03', '123 Main St, City, Country', 1),
                                                                                   ('Express Shipping', '2023-05-04', '456 Oak Ave, Town, Country', 2),
                                                                                   ('Standard Shipping', '2023-05-05', '789 Elm St, Village, Country', 3),
                                                                                   ('Express Shipping', '2023-05-06', '321 Pine Rd, City, Country', 4),
                                                                                   ('Standard Shipping', '2023-05-07', '654 Maple Ln, Town, Country', 5);
