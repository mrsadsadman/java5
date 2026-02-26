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
    status NVARCHAR(50) DEFAULT N'Chờ xử lý',
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

-- Insert Roles
INSERT INTO Roles (name, description) VALUES
(N'ADMIN', N'Quản trị hệ thống'),
(N'USER', N'Người dùng thông thường');

-- Insert Accounts
INSERT INTO Accounts (username, password, fullname, email, phone, address, activated, role_id) VALUES
('admin', '123', N'Admin Hệ Thống', 'admin@abcshop.com', '0123456789', N'Hà Nội', 1, 1),
('user1', '123', N'Nguyễn Văn An', 'user1@email.com', '0987654321', N'TP.HCM', 1, 2),
('user2', '123', N'Trần Thị Bình', 'user2@email.com', '0912345678', N'Đà Nẵng', 1, 2),
('user3', '123', N'Lê Văn Cường', 'user3@email.com', '0934567890', N'Hải Phòng', 1, 2),
('user4', '123', N'Phạm Thị Dung', 'user4@email.com', '0945678901', N'Cần Thơ', 1, 2);

-- Insert Categories
INSERT INTO Categories (name, description) VALUES
(N'Điện thoại', N'Các loại điện thoại thông minh'),
(N'Laptop', N'Máy tính xách tay các loại'),
(N'Tablet', N'Máy tính bảng'),
(N'Phụ kiện', N'Phụ kiện điện tử'),
(N'Tai nghe', N'Tai nghe không dây và có dây'),
(N'Loa', N'Loa bluetooth và loa vi tính'),
(N'Đồng hồ', N'Đồng hồ thông minh'),
(N'Máy ảnh', N'Máy ảnh kỹ thuật số'),
(N'TV', N'Tivi thông minh'),
(N'Gaming', N'Thiết bị chơi game');

-- Insert Products
INSERT INTO Products (name, price, discount, quantity, description, category_id, image) VALUES
(N'iPhone 15 Pro Max', 29990000, 5, 50, N'iPhone 15 Pro Max 256GB chính hãng', 1, 'iphone15.jpg'),
(N'Samsung Galaxy S24 Ultra', 24990000, 7, 40, N'Samsung Galaxy S24 Ultra 512GB', 1, 'samsung24.jpg'),
(N'MacBook Pro M3', 45990000, 3, 30, N'MacBook Pro 14 inch M3 mới nhất', 2, 'macbook.jpg'),
(N'Dell XPS 13', 32990000, 8, 25, N'Dell XPS 13 2024 cao cấp', 2, 'dellxps.jpg'),
(N'iPad Pro M2', 22990000, 4, 35, N'iPad Pro 12.9 inch M2 mạnh mẽ', 3, 'ipadpro.jpg'),
(N'Samsung Galaxy Tab S9', 18990000, 6, 45, N'Samsung Galaxy Tab S9 Ultra', 3, 'tabs9.jpg'),
(N'AirPods Pro 2', 6990000, 10, 100, N'Tai nghe AirPods Pro 2 chống ồn', 5, 'airpods.jpg'),
(N'Sony WH-1000XM5', 7990000, 5, 60, N'Tai nghe chống ồn Sony cao cấp', 5, 'sony.jpg'),
(N'Apple Watch Ultra 2', 19990000, 2, 40, N'Apple Watch Ultra 2 chính hãng', 7, 'applewatch.jpg'),
(N'PlayStation 5', 11990000, 15, 50, N'Máy chơi game PS5 bản mới', 10, 'ps5.jpg');


UPDATE Products SET image = 'https://images.unsplash.com/photo-1695048133142-1a20484d2569' 
WHERE name = N'iPhone 15 Pro Max';

UPDATE Products SET image = 'https://images.unsplash.com/photo-1706280949837-e2c3e4e5b9f0'
WHERE name = N'Samsung Galaxy S24 Ultra';

UPDATE Products SET image = 'https://images.unsplash.com/photo-1517336714731-489689fd1ca8'
WHERE name = N'MacBook Pro M3';

UPDATE Products SET image = 'https://images.unsplash.com/photo-1587614382346-4ec70e388b28'
WHERE name = N'Dell XPS 13';

UPDATE Products SET image = 'https://images.unsplash.com/photo-1585790050230-5dd28404ccb9'
WHERE name = N'iPad Pro M2';

UPDATE Products SET image = 'https://images.unsplash.com/photo-1603898037225-0f9c7a53a7b6'
WHERE name = N'Samsung Galaxy Tab S9';

UPDATE Products SET image = 'https://images.unsplash.com/photo-1600294037681-c80b4cb5b434'
WHERE name = N'AirPods Pro 2';

UPDATE Products SET image = 'https://images.unsplash.com/photo-1583394838336-acd977736f90'
WHERE name = N'Sony WH-1000XM5';

UPDATE Products SET image = 'https://images.unsplash.com/photo-1603791440384-56cd371ee9a7'
WHERE name = N'Apple Watch Ultra 2';

UPDATE Products SET image = 'https://images.unsplash.com/photo-1606813907291-d86efa9b94db'
WHERE name = N'PlayStation 5';
