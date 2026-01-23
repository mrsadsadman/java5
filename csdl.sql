-- Tạo database
CREATE DATABASE ABCShopDB;
GO

USE ABCShopDB;
GO

-- Bảng Roles
CREATE TABLE Roles (
    id INT PRIMARY KEY IDENTITY(1,1),
    name NVARCHAR(50) NOT NULL,
    description NVARCHAR(255)
);
GO

-- Bảng Accounts
CREATE TABLE Accounts (
    id INT PRIMARY KEY IDENTITY(1,1),
    username NVARCHAR(50) UNIQUE NOT NULL,
    password NVARCHAR(255) NOT NULL,
    fullname NVARCHAR(100) NOT NULL,
    email NVARCHAR(100) UNIQUE NOT NULL,
    phone NVARCHAR(20),
    address NVARCHAR(255),
    photo NVARCHAR(255),
    activated BIT DEFAULT 0,
    token NVARCHAR(255),
    role_id INT FOREIGN KEY REFERENCES Roles(id),
    created_date DATETIME DEFAULT GETDATE()
);
GO

-- Bảng Categories
CREATE TABLE Categories (
    id INT PRIMARY KEY IDENTITY(1,1),
    name NVARCHAR(100) NOT NULL,
    description NVARCHAR(255),
    image NVARCHAR(255)
);
GO

-- Bảng Products
CREATE TABLE Products (
    id INT PRIMARY KEY IDENTITY(1,1),
    name NVARCHAR(200) NOT NULL,
    price DECIMAL(18,2) NOT NULL,
    discount DECIMAL(5,2) DEFAULT 0,
    quantity INT DEFAULT 0,
    description NVARCHAR(MAX),
    image NVARCHAR(255),
    category_id INT FOREIGN KEY REFERENCES Categories(id),
    created_date DATETIME DEFAULT GETDATE(),
    available BIT DEFAULT 1
);
GO

-- Bảng Orders
CREATE TABLE Orders (
    id INT PRIMARY KEY IDENTITY(1,1),
    account_id INT FOREIGN KEY REFERENCES Accounts(id),
    order_date DATETIME DEFAULT GETDATE(),
    address NVARCHAR(255) NOT NULL,
    phone NVARCHAR(20) NOT NULL,
    total_amount DECIMAL(18,2) NOT NULL,
    status NVARCHAR(50) DEFAULT 'Pending',
    notes NVARCHAR(500)
);
GO

-- Bảng OrderDetails
CREATE TABLE OrderDetails (
    id INT PRIMARY KEY IDENTITY(1,1),
    order_id INT FOREIGN KEY REFERENCES Orders(id),
    product_id INT FOREIGN KEY REFERENCES Products(id),
    price DECIMAL(18,2) NOT NULL,
    quantity INT NOT NULL,
    discount DECIMAL(5,2) DEFAULT 0,
    total DECIMAL(18,2) NOT NULL
);
GO

-- Insert dữ liệu mẫu
INSERT INTO Roles (name, description) VALUES
('ADMIN', 'Quản trị hệ thống'),
('USER', 'Người dùng thông thường');
GO

-- Insert admin account (password: admin123)
INSERT INTO Accounts (username, password, fullname, email, phone, address, activated, role_id) VALUES
('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iK5KZD8a', 
 'Admin System', 'admin@abcshop.com', '0123456789', 'Hà Nội', 1, 1);
GO

-- Insert user accounts
INSERT INTO Accounts (username, password, fullname, email, phone, address, activated, role_id) VALUES
('user1', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iK5KZD8a', 
 'Nguyễn Văn An', 'user1@email.com', '0987654321', 'TP.HCM', 1, 2),
('user2', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iK5KZD8a',
 'Trần Thị Bình', 'user2@email.com', '0912345678', 'Đà Nẵng', 1, 2),
('user3', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iK5KZD8a',
 'Lê Văn Cường', 'user3@email.com', '0934567890', 'Hải Phòng', 1, 2),
('user4', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iK5KZD8a',
 'Phạm Thị Dung', 'user4@email.com', '0945678901', 'Cần Thơ', 1, 2);
GO

-- Insert categories
INSERT INTO Categories (name, description) VALUES
('Điện thoại', 'Các loại điện thoại thông minh'),
('Laptop', 'Máy tính xách tay các loại'),
('Tablet', 'Máy tính bảng'),
('Phụ kiện', 'Phụ kiện điện tử'),
('Tai nghe', 'Tai nghe không dây và có dây'),
('Loa', 'Loa bluetooth và loa vi tính'),
('Đồng hồ', 'Đồng hồ thông minh'),
('Máy ảnh', 'Máy ảnh kỹ thuật số'),
('TV', 'Tivi thông minh'),
('Gaming', 'Thiết bị chơi game');
GO

-- Insert products (200 sản phẩm mẫu - chỉ liệt kê 10)
INSERT INTO Products (name, price, discount, quantity, description, category_id, image) VALUES
('iPhone 15 Pro Max', 29990000, 5, 50, 'iPhone 15 Pro Max 256GB', 1, 'iphone15.jpg'),
('Samsung Galaxy S24 Ultra', 24990000, 7, 40, 'Samsung Galaxy S24 Ultra 512GB', 1, 'samsung24.jpg'),
('MacBook Pro M3', 45990000, 3, 30, 'MacBook Pro 14 inch M3', 2, 'macbook.jpg'),
('Dell XPS 13', 32990000, 8, 25, 'Dell XPS 13 2024', 2, 'dellxps.jpg'),
('iPad Pro M2', 22990000, 4, 35, 'iPad Pro 12.9 inch M2', 3, 'ipadpro.jpg'),
('Samsung Galaxy Tab S9', 18990000, 6, 45, 'Samsung Galaxy Tab S9 Ultra', 3, 'tabs9.jpg'),
('AirPods Pro 2', 6990000, 10, 100, 'Tai nghe AirPods Pro 2', 5, 'airpods.jpg'),
('Sony WH-1000XM5', 7990000, 5, 60, 'Tai nghe chống ồn Sony', 5, 'sony.jpg'),
('Apple Watch Ultra 2', 19990000, 2, 40, 'Apple Watch Ultra 2', 7, 'applewatch.jpg'),
('PlayStation 5', 11990000, 15, 50, 'Máy chơi game PS5', 10, 'ps5.jpg');
-- Thêm 190 sản phẩm khác...
GO

-- Tạo indexes
CREATE INDEX idx_products_category ON Products(category_id);
CREATE INDEX idx_orders_account ON Orders(account_id);
CREATE INDEX idx_orderdetails_order ON OrderDetails(order_id);
GO