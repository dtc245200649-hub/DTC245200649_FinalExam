-- =====================================================================
-- KỊCH BẢN TẠO CƠ SỞ DỮ LIỆU & BẢNG CHO BÀI KIỂM TRA CUỐI MODULE
-- Hệ thống: Quản lý sản phẩm (Product Management System)
-- =====================================================================

-- 1. Tạo cơ sở dữ liệu nếu chưa tồn tại
CREATE DATABASE IF NOT EXISTS product_management
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

-- 2. Sử dụng cơ sở dữ liệu
USE product_management;

-- 3. Xóa bảng cũ nếu đã tồn tại để dễ dàng chạy lại script
DROP TABLE IF EXISTS products;

-- 4. Tạo bảng products theo đúng yêu cầu đề bài
CREATE TABLE products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    quantity INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 5. Thêm dữ liệu mẫu (ít nhất 5 sản phẩm công nghệ)
INSERT INTO products (name, price, quantity) VALUES
('Laptop Dell Inspiron 15 3520', 14500000.00, 15),
('Chuột không dây Logitech M331 Silent', 350000.00, 50),
('Bàn phím cơ DareU EK87 White Red Switch', 650000.00, 30),
('Tai nghe chống ồn Sony WH-1000XM5', 6890000.00, 10),
('Màn hình LG 27 inch 4K UHD 27UP600', 7490000.00, 20),
('Ổ cứng di động SSD Samsung T7 1TB', 2490000.00, 25);

-- Kiểm tra dữ liệu vừa thêm
SELECT * FROM products;
