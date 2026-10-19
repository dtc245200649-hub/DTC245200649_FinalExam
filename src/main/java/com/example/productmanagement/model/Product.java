package com.example.productmanagement.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Model biểu diễn thực thể Sản phẩm (Product).
 * Tương ứng với bảng 'products' trong cơ sở dữ liệu MySQL.
 */
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private BigDecimal price;
    private int quantity;

    // Constructor mặc định (no-argument constructor)
    public Product() {
    }

    // Constructor dùng khi thêm mới sản phẩm (chưa có ID do MySQL tự sinh AUTO_INCREMENT)
    public Product(String name, BigDecimal price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    // Constructor đầy đủ khi đọc từ Database hoặc khi sửa sản phẩm
    public Product(int id, String name, BigDecimal price, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
