package com.example.productmanagement.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Lớp tiện ích quản lý kết nối JDBC đến MySQL Database.
 * Đọc cấu hình từ file db.properties trong classpath.
 */
public class DBConnection {

    private static String url;
    private static String username;
    private static String password;
    private static String driver;

    static {
        // Giá trị mặc định phòng trường hợp không đọc được file cấu hình
        url = "jdbc:mysql://localhost:3306/product_management?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Ho_Chi_Minh&characterEncoding=UTF-8";
        username = "root";
        password = "";
        driver = "com.mysql.cj.jdbc.Driver";

        // Đọc cấu hình từ file db.properties
        try (InputStream input = DBConnection.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input != null) {
                Properties prop = new Properties();
                prop.load(input);

                if (prop.getProperty("db.url") != null) {
                    url = prop.getProperty("db.url").trim();
                }
                if (prop.getProperty("db.username") != null) {
                    username = prop.getProperty("db.username").trim();
                }
                if (prop.getProperty("db.password") != null) {
                    password = prop.getProperty("db.password").trim();
                }
                if (prop.getProperty("db.driver") != null) {
                    driver = prop.getProperty("db.driver").trim();
                }
            } else {
                System.err.println("[DBConnection] Cảnh báo: Không tìm thấy file db.properties, sử dụng cấu hình mặc định.");
            }
        } catch (IOException ex) {
            System.err.println("[DBConnection] Lỗi đọc file db.properties: " + ex.getMessage());
        }

        // Ưu tiên đọc từ biến môi trường (Environment Variables) nếu có (hữu ích khi chạy Docker)
        if (System.getenv("DB_URL") != null && !System.getenv("DB_URL").trim().isEmpty()) {
            url = System.getenv("DB_URL").trim();
        }
        if (System.getenv("DB_USERNAME") != null && !System.getenv("DB_USERNAME").trim().isEmpty()) {
            username = System.getenv("DB_USERNAME").trim();
        }
        if (System.getenv("DB_PASSWORD") != null) {
            password = System.getenv("DB_PASSWORD").trim();
        }

        // Đăng ký JDBC Driver
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            System.err.println("[DBConnection] Lỗi: Không tìm thấy MySQL Driver: " + driver);
            e.printStackTrace();
        }
    }

    /**
     * Mở và trả về một kết nối mới tới cơ sở dữ liệu.
     * Người gọi phương thức này chịu trách nhiệm đóng kết nối (thường dùng try-with-resources).
     *
     * @return Connection đối tượng kết nối JDBC
     * @throws SQLException nếu có lỗi xảy ra trong quá trình kết nối
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}
