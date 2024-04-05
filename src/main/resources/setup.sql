-- Create Database (Schema)
CREATE DATABASE IF NOT EXISTS iotbay;
USE iotbay;

-- Create 'users' Table
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL,
    isAdmin BOOLEAN NOT NULL DEFAULT FALSE
    );

-- Create 'products' Table
CREATE TABLE IF NOT EXISTS products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(45) NOT NULL,
    desc LONGTEXT,
    price DECIMAL(10, 2) NOT NULL
    );
