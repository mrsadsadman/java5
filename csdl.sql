DROP DATABASE ABCShopDB;
CREATE DATABASE ABCShopDB;
GO

USE ABCShopDB;
GO

-- Roles
CREATE TABLE Roles (
    id INT PRIMARY KEY IDENTITY(1,1),
    name NVARCHAR(50) NOT NULL,
    description NVARCHAR(255)
);

-- Accounts
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
SELECT DB_ID('ABCShopDB');
SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'accounts';
-- Categories
CREATE TABLE Categories (
    id INT PRIMARY KEY IDENTITY(1,1),
    name NVARCHAR(100) NOT NULL,
    description NVARCHAR(255),
    image NVARCHAR(255)
);

-- Products
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

-- Orders
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

-- OrderDetails
CREATE TABLE OrderDetails (
    id INT PRIMARY KEY IDENTITY(1,1),
    order_id INT FOREIGN KEY REFERENCES Orders(id),
    product_id INT FOREIGN KEY REFERENCES Products(id),
    price DECIMAL(18,2) NOT NULL,
    quantity INT NOT NULL,
    discount DECIMAL(5,2) DEFAULT 0,
    total DECIMAL(18,2) NOT NULL
);

-- Insert dữ liệu mẫu
INSERT INTO Roles (name, description) VALUES
('ADMIN', 'Quản trị hệ thống'),
('USER', 'Người dùng thông thường');

-- Insert accounts with plain text password '123'
INSERT INTO Accounts (username, password, fullname, email, phone, address, activated, role_id) VALUES
('admin', '123', 'Admin System', 'admin@abcshop.com', '0123456789', 'Hà Nội', 1, 1),
('user1', '123', 'Nguyễn Văn An', 'user1@email.com', '0987654321', 'TP.HCM', 1, 2),
('user2', '123', 'Trần Thị Bình', 'user2@email.com', '0912345678', 'Đà Nẵng', 1, 2),
('user3', '123', 'Lê Văn Cường', 'user3@email.com', '0934567890', 'Hải Phòng', 1, 2),
('user4', '123', 'Phạm Thị Dung', 'user4@email.com', '0945678901', 'Cần Thơ', 1, 2);

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
-- Add more products as needed...
