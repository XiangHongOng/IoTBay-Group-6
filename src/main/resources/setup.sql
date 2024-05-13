-- Create Database (Schema)
CREATE DATABASE IF NOT EXISTS iotbay;
USE iotbay;

-- Create 'users' Table
CREATE TABLE Users (
                       UserID INT PRIMARY KEY AUTO_INCREMENT,
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
                           FullName VARCHAR(50) NOT NULL,
                           CustomerType VARCHAR(20) NOT NULL,
                           Address VARCHAR(100),
                           Email VARCHAR(50),
                           IsActive bool NOT NULL DEFAULT true,
                           FOREIGN KEY (UserID) REFERENCES Users(UserID) ON DELETE SET NULL
);

CREATE TABLE Staff (
                       StaffID INT PRIMARY KEY AUTO_INCREMENT,
                       UserID INT UNIQUE,
                       FullName VARCHAR(50) NOT NULL,
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
INSERT INTO Users (Email, Password, Phone) VALUES
                                               ('john.doe@example.com', 'password123', '1234567890'),
                                               ('jane.smith@example.com', 'password456', '9876543210'),
                                               ('michael.johnson@example.com', 'password789', '4561237890'),
                                               ('emily.davis@example.com', 'password012', '7890123456'),
                                               ('robert.wilson@example.com', 'password345', '2345678901'),
                                               ('alice.brown@example.com', 'password678', '6789012345'),
                                               ('bob.thompson@example.com', 'password901', '9012345678'),
                                               ('charlie.anderson@example.com', 'password234', '2345678901'),
                                               ('david.taylor@example.com', 'password567', '5678901234'),
                                               ('eve.miller@example.com', 'password890', '8901234567'),
                                               ('frank.jackson@example.com', 'password123', '1234567890'),
                                               ('grace.hughes@example.com', 'password456', '9876543210'),
                                               ('henry.parker@example.com', 'password789', '4561237890'),
                                               ('isabelle.reed@example.com', 'password012', '7890123456'),
                                               ('jack.turner@example.com', 'password345', '2345678901'),
                                               ('katie.ward@example.com', 'password678', '6789012345'),
                                               ('liam.young@example.com', 'password901', '9012345678'),
                                               ('mia.cook@example.com', 'password234', '2345678901'),
                                               ('noah.baker@example.com', 'password567', '5678901234'),
                                               ('olivia.morris@example.com', 'password890', '8901234567'),
                                               ('cam@cam.com', '123', '123');
;

-- Insert data into Customers table
INSERT INTO Customers (UserID, FullName, CustomerType, Address, Email, IsActive) VALUES
                                                                                     (1, 'John Doe', 'Individual', '123 Main St, City, Country', null, true),
                                                                                     (2, 'Jane Smith', 'Individual', '456 Oak Ave, Town, Country', null, true),
                                                                                     (3, 'Michael Johnson', 'Individual', '789 Elm St, Village, Country', null, true),
                                                                                     (4, 'Emily Davis', 'Individual', '321 Pine Rd, City, Country', 'robert.wilson@example.com', true),
                                                                                     (5, 'Robert Wilson', 'Individual', '654 Maple Ln, Town, Country', null, false),
                                                                                     (6, 'Alice Brown', 'Individual', '987 Cedar Blvd, City, Country', null, true),
                                                                                     (7, 'Bob Thompson', 'Individual', '159 Birch Way, Town, Country', null, true),
                                                                                     (8, 'Charlie Anderson', 'Individual', '753 Willow Dr, Village, Country', null, true),
                                                                                     (9, 'David Taylor', 'Individual', '246 Spruce Ave, City, Country', null, true),
                                                                                     (10, 'Eve Miller', 'Individual', '840 Ash St, Town, Country', null, true),
                                                                                     (11, 'Frank Jackson', 'Company', '369 Oak St, City, Country', 'frank.jackson@example.com', true),
                                                                                     (12, 'Grace Hughes', 'Company', '258 Maple Ave, Town, Country', 'grace.hughes@example.com', true),
                                                                                     (13, 'Henry Parker', 'Individual', '147 Pine Rd, Village, Country', null, true),
                                                                                     (14, 'Isabelle Reed', 'Individual', '963 Cedar Ln, City, Country', null, true),
                                                                                     (15, 'Jack Turner', 'Company', '852 Birch Blvd, Town, Country', 'jack.turner@example.com', true),
                                                                                     (16, 'Katie Ward', 'Individual', '741 Willow Way, Village, Country', null, true),
                                                                                     (17, 'Liam Young', 'Individual', '630 Spruce Dr, City, Country', null, true),
                                                                                     (18, 'Mia Cook', 'Company', '529 Ash Ave, Town, Country', 'mia.cook@example.com', true),
                                                                                     (19, 'Noah Baker', 'Individual', '418 Oak Rd, Village, Country', null, true),
                                                                                     (20, 'Olivia Morris', 'Individual', '307 Maple Ln, City, Country', null, true);

-- Insert data into Staff table
INSERT INTO Staff (UserID, FullName, Position, Address) VALUES
                                                            (1, 'Patricia Roberts', 'Manager', '123 staff St, City, Country'),
                                                            (2, 'Queen Phillips', 'Sales Representative', '456 staff Ave, Town, Country'),
                                                            (3, 'Ryan Simmons', 'Customer Support', '789 staff Rd, Village, Country'),
                                                            (4, 'Sophie Henderson', 'Shipping Coordinator', '321 staff Ln, City, Country'),
                                                            (5, 'Thomas Gonzalez', 'Inventory Specialist', '654 staff Blvd, Town, Country'),
                                                            (6, 'Ursula Fisher', 'Manager', '987 staff Way, City, Country'),
                                                            (7, 'Victor Barnes', 'Sales Representative', '159 staff Dr, Town, Country'),
                                                            (8, 'Wendy Hart', 'Customer Support', '753 staff Ave, Village, Country'),
                                                            (9, 'Xavier Sims', 'Shipping Coordinator', '246 staff Rd, City, Country'),
                                                            (10, 'Yvonne Hawkins', 'Inventory Specialist', '840 staff Ln, Town, Country'),
                                                            (11, 'Zachary Cunningham', 'Manager', '369 staff St, City, Country'),
                                                            (12, 'Aria Nichols', 'Sales Representative', '258 staff Ave, Town, Country'),
                                                            (13, 'Benjamin Fowler', 'Customer Support', '147 staff Rd, Village, Country'),
                                                            (14, 'Charlotte Bowman', 'Shipping Coordinator', '963 staff Ln, City, Country'),
                                                            (15, 'Daniel Vaughn', 'Inventory Specialist', '852 staff Blvd, Town, Country'),
                                                            (16, 'Ella Schneider', 'Manager', '741 staff Way, Village, Country'),
                                                            (17, 'Finn Marsh', 'Sales Representative', '630 staff Dr, City, Country'),
                                                            (18, 'Grace Harvey', 'Customer Support', '529 staff Ave, Town, Country'),
                                                            (19, 'Harrison Reeves', 'Shipping Coordinator', '418 staff Rd, Village, Country'),
                                                            (20, 'Ivy Larson', 'Inventory Specialist', '307 staff Ln, City, Country'),
                                                            (21, 'Cam', 'Test', 'no dox');

-- Insert data into Suppliers table
INSERT INTO Suppliers (ContactName, Company, Email, Address) VALUES
                                                                 ('Alice Smith', 'Tech Solutions Inc.', 'alice@techsolutions.com', '123 Industrial Way, City, Country'),
                                                                 ('Bob Johnson', 'Gadget Suppliers', 'bob@gadgetsuppliers.com', '456 Factory Rd, Town, Country'),
                                                                 ('Charlie Davis', 'Electronic Emporium', 'charlie@electronicemporium.com', '789 Commerce St, Village, Country'),
                                                                 ('David Wilson', 'Wireless Wonders', 'david@wirelesswonders.com', '321 Technology Ln, City, Country'),
                                                                 ('Eve Taylor', 'Smart Devices Corp.', 'eve@smartdevicescorp.com', '654 Innovation Blvd, Town, Country'),
                                                                 ('Frank Jackson', 'Techno Traders', 'frank@technotraders.com', '987 Market St, City, Country'),
                                                                 ('Grace Hughes', 'Gadgetron Inc.', 'grace@gadgetron.com', '159 Silicon Ave, Town, Country'),
                                                                 ('Henry Parker', 'ElectroniX Ltd.', 'henry@electronix.com', '753 Circuit Rd, Village, Country'),
                                                                 ('Isabelle Reed', 'Wired Up Co.', 'isabelle@wiredup.com', '246 Transistor Ln, City, Country'),
                                                                 ('Jack Turner', 'Tech Innovators', 'jack@techinnovators.com', '840 Microchip Blvd, Town, Country'),
                                                                 ('Katie Ward', 'Gadget Galaxy', 'katie@gadgetgalaxy.com', '369 Processor St, City, Country'),
                                                                 ('Liam Young', 'E-Tech Enterprises', 'liam@etechenterprises.com', '258 Capacitor Ave, Town, Country'),
                                                                 ('Mia Cook', 'Wired World', 'mia@wiredworld.com', '147 Diode Rd, Village, Country'),
                                                                 ('Noah Baker', 'Tech Titans', 'noah@techtitans.com', '963 Resistor Ln, City, Country'),
                                                                 ('Olivia Morris', 'Gadgets Galore', 'olivia@gadgetsgalore.com', '852 Inductor Blvd, Town, Country'),
                                                                 ('Patricia Roberts', 'Circuit City', 'patricia@circuitcity.com', '741 Transistor Way, Village, Country'),
                                                                 ('Quinn Phillips', 'Tech Depot', 'quinn@techdepot.com', '630 Capacitor Dr, City, Country'),
                                                                 ('Ryan Simmons', 'Electronic Express', 'ryan@electronicexpress.com', '529 Diode Ave, Town, Country'),
                                                                 ('Sophie Henderson', 'Gadget Hub', 'sophie@gadgethub.com', '418 Resistor Rd, Village, Country'),
                                                                 ('Thomas Gonzalez', 'Tech Emporium', 'thomas@techemporium.com', '307 Inductor Ln, City, Country');

-- Insert data into Products table
INSERT INTO Products (ProductName, ProductType, UnitPrice, Quantity) VALUES
                                                                         ('Smart Watch', 'Wearable', 199.99, 50),
                                                                         ('Wireless Headphones', 'Audio', 99.99, 75),
                                                                         ('Smart Home Hub', 'Home Automation', 149.99, 30),
                                                                         ('Virtual Reality Headset', 'Gaming', 299.99, 40),
                                                                         ('Fitness Tracker', 'Wearable', 79.99, 60),
                                                                         ('Bluetooth Speaker', 'Audio', 59.99, 100),
                                                                         ('Smart Thermostat', 'Home Automation', 129.99, 25),
                                                                         ('Wireless Keyboard', 'Computer Accessories', 49.99, 80),
                                                                         ('Smart Security Camera', 'Home Security', 99.99, 35),
                                                                         ('Noise-Cancelling Headphones', 'Audio', 199.99, 50),
                                                                         ('Fitness Smartwatch', 'Wearable', 149.99, 45),
                                                                         ('Wireless Charging Pad', 'Phone Accessories', 29.99, 90),
                                                                         ('Smart Door Lock', 'Home Security', 179.99, 20),
                                                                         ('Bluetooth Earbuds', 'Audio', 89.99, 70),
                                                                         ('Smart Smoke Detector', 'Home Safety', 69.99, 55),
                                                                         ('Wireless Gaming Mouse', 'Gaming', 79.99, 65),
                                                                         ('Smart Plug', 'Home Automation', 24.99, 110),
                                                                         ('Fitness Tracker with Heart Rate Monitor', 'Wearable', 109.99, 40),
                                                                         ('Wireless Surround Sound System', 'Audio', 399.99, 15),
                                                                         ('Smart Doorbell', 'Home Security', 149.99, 30);

-- Insert data into Orders table
INSERT INTO Orders (CustomerID, OrderDate) VALUES
                                               (1, '2024-05-01'),
                                               (2, '2024-05-02'),
                                               (6, '2024-05-03'),
                                               (3, '2024-05-04'),
                                               (4, '2024-05-05'),
                                               (7, '2024-05-06'),
                                               (8, '2024-05-07'),
                                               (10, '2024-05-08'),
                                               (11, '2024-05-09'),
                                               (12, '2024-05-10'),
                                               (15, '2024-05-11'),
                                               (16, '2024-05-12'),
                                               (18, '2024-05-13'),
                                               (19, '2024-05-14'),
                                               (20, '2024-05-15'),
                                               (5, '2024-05-16'),
                                               (9, '2024-05-17'),
                                               (13, '2024-05-18'),
                                               (14, '2024-05-19'),
                                               (17, '2024-05-20');

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
                                                                     (5, 5, 1),
                                                                     (6, 6, 4),
                                                                     (7, 7, 1),
                                                                     (8, 8, 3),
                                                                     (9, 9, 2),
                                                                     (10, 10, 1),
                                                                     (11, 11, 2),
                                                                     (12, 12, 5),
                                                                     (13, 13, 1),
                                                                     (14, 14, 3),
                                                                     (15, 15, 2),
                                                                     (16, 16, 4);

-- Insert data into Payments table
INSERT INTO Payments (PaymentMethod, CreditCardDetails, Amount, PaymentDate, OrderID) VALUES
                                                                                          ('Credit Card', '1234567890123456', 349.98, '2024-05-01', 1),
                                                                                          ('Debit Card', '9876543210987654', 299.97, '2024-05-02', 2),
                                                                                          ('PayPal', NULL, 379.98, '2024-05-03', 3),
                                                                                          ('Credit Card', '5678901234567890', 449.97, '2024-05-04', 4),
                                                                                          ('Debit Card', '1098765432109876', 279.98, '2024-05-05', 5),
                                                                                          ('PayPal', NULL, 199.99, '2024-05-06', 6),
                                                                                          ('Credit Card', '4567890123456789', 129.99, '2024-05-07', 7),
                                                                                          ('Debit Card', '8765432109876543', 99.99, '2024-05-08', 8),
                                                                                          ('PayPal', NULL, 599.97, '2024-05-09', 9),
                                                                                          ('Credit Card', '2345678901234567', 249.98, '2024-05-10', 10),
                                                                                          ('Debit Card', '7654321098765432', 179.99, '2024-05-11', 11),
                                                                                          ('PayPal', NULL, 149.99, '2024-05-12', 12),
                                                                                          ('Credit Card', '9012345678901234', 399.98, '2024-05-13', 13),
                                                                                          ('Debit Card', '3456789012345678', 89.99, '2024-05-14', 14),
                                                                                          ('PayPal', NULL, 69.99, '2024-05-15', 15),
                                                                                          ('Credit Card', '6789012345678901', 799.96, '2024-05-16', 16),
                                                                                          ('Debit Card', '0123456789012345', 199.99, '2024-05-17', 17),
                                                                                          ('PayPal', NULL, 109.99, '2024-05-18', 18),
                                                                                          ('Credit Card', '4567890123456789', 299.97, '2024-05-19', 19),
                                                                                          ('Debit Card', '8765432109876543', 449.98, '2024-05-20', 20);
-- Insert data into Shipments table
INSERT INTO Shipments (ShipmentMethod, ShipmentDate, ShipmentAddress, OrderID) VALUES
                                                                                   ('Standard Shipping', '2024-05-03', '123 Main St, City, Country', 1),
                                                                                   ('Express Shipping', '2024-05-04', '456 Oak Ave, Town, Country', 2),
                                                                                   ('Standard Shipping', '2024-05-05', '789 Elm St, Village, Country', 3),
                                                                                   ('Express Shipping', '2024-05-06', '321 Pine Rd, City, Country', 4),
                                                                                   ('Standard Shipping', '2024-05-07', '654 Maple Ln, Town, Country', 5),
                                                                                   ('Express Shipping', '2024-05-08', '987 Cedar Blvd, City, Country', 6),
                                                                                   ('Standard Shipping', '2024-05-09', '159 Birch Way, Town, Country', 7),
                                                                                   ('Express Shipping', '2024-05-10', '753 Willow Dr, Village, Country', 8),
                                                                                   ('Standard Shipping', '2024-05-11', '246 Spruce Ave, City, Country', 9),
                                                                                   ('Express Shipping', '2024-05-12', '840 Ash St, Town, Country', 10),
                                                                                   ('Standard Shipping', '2024-05-13', '369 Oak St, City, Country', 11),
                                                                                   ('Express Shipping', '2024-05-14', '258 Maple Ave, Town, Country', 12),
                                                                                   ('Standard Shipping', '2024-05-15', '147 Pine Rd, Village, Country', 13),
                                                                                   ('Express Shipping', '2024-05-16', '963 Cedar Ln, City, Country', 14),
                                                                                   ('Standard Shipping', '2024-05-17', '852 Birch Blvd, Town, Country', 15),
                                                                                   ('Express Shipping', '2024-05-18', '741 Willow Way, Village, Country', 16),
                                                                                   ('Standard Shipping', '2024-05-19', '630 Spruce Dr, City, Country', 17),
                                                                                   ('Express Shipping', '2024-05-20', '529 Ash Ave, Town, Country', 18),
                                                                                   ('Standard Shipping', '2024-05-21', '418 Oak Rd, Village, Country', 19),
                                                                                   ('Express Shipping', '2024-05-22', '307 Maple Ln, City, Country', 20);
-- Insert data into UserAccessLogs table
INSERT INTO UserAccessLogs (UserID, LoginDateTime, LogoutDateTime) VALUES
                                                                       (1, '2024-05-01 10:00:00', '2024-05-01 12:30:00'),
                                                                       (2, '2024-05-02 08:30:00', '2024-05-02 17:45:00'),
                                                                       (3, '2024-05-03 09:15:00', '2024-05-03 11:20:00'),
                                                                       (4, '2024-05-04 11:20:00', '2024-05-04 14:50:00'),
                                                                       (5, '2024-05-05 13:00:00', '2024-05-05 16:10:00'),
                                                                       (6, '2024-05-06 14:30:00', '2024-05-06 18:45:00'),
                                                                       (7, '2024-05-07 16:00:00', '2024-05-07 19:20:00'),
                                                                       (8, '2024-05-08 17:30:00', '2024-05-08 20:15:00'),
                                                                       (9, '2024-05-09 19:00:00', '2024-05-09 22:00:00'),
                                                                       (10, '2024-05-10 20:30:00', '2024-05-10 23:45:00'),
                                                                       (11, '2024-05-11 22:00:00', '2024-05-12 01:30:00'),
                                                                       (12, '2024-05-12 23:30:00', '2024-05-13 03:15:00'),
                                                                       (13, '2024-05-13 01:00:00', '2024-05-13 04:45:00'),
                                                                       (14, '2024-05-14 02:30:00', '2024-05-14 06:00:00'),
                                                                       (15, '2024-05-15 04:00:00', '2024-05-15 07:30:00'),
                                                                       (16, '2024-05-16 05:30:00', '2024-05-16 08:45:00'),
                                                                       (17, '2024-05-17 07:00:00', '2024-05-17 10:15:00'),
                                                                       (18, '2024-05-18 08:30:00', '2024-05-18 11:30:00'),
                                                                       (19, '2024-05-19 10:00:00', '2024-05-19 13:00:00'),
                                                                       (20, '2024-05-20 11:30:00', '2024-05-20 14:45:00');