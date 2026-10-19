package com.example.productmanagement.dao;

import com.example.productmanagement.model.Product;
import com.example.productmanagement.util.DBConnection;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Lớp Data Access Object (DAO) thực hiện các thao tác CRUD với bảng products trong MySQL.
 * Tất cả thao tác đều sử dụng PreparedStatement và try-with-resources để bảo vệ và giải phóng tài nguyên.
 */
public class ProductDAO {

    private static final String SELECT_ALL_PRODUCTS = "SELECT id, name, price, quantity FROM products ORDER BY id DESC";
    private static final String SELECT_PRODUCT_BY_ID = "SELECT id, name, price, quantity FROM products WHERE id = ?";
    private static final String INSERT_PRODUCT_SQL = "INSERT INTO products (name, price, quantity) VALUES (?, ?, ?)";
    private static final String UPDATE_PRODUCT_SQL = "UPDATE products SET name = ?, price = ?, quantity = ? WHERE id = ?";
    private static final String DELETE_PRODUCT_SQL = "DELETE FROM products WHERE id = ?";

    /**
     * Lấy toàn bộ danh sách sản phẩm từ database.
     *
     * @return List<Product> danh sách sản phẩm
     * @throws SQLException nếu có lỗi truy vấn cơ sở dữ liệu
     */
    public List<Product> selectAllProducts() throws SQLException {
        List<Product> products = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_PRODUCTS);
             ResultSet rs = preparedStatement.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                BigDecimal price = rs.getBigDecimal("price");
                int quantity = rs.getInt("quantity");

                products.add(new Product(id, name, price, quantity));
            }
        }
        return products;
    }

    /**
     * Lấy thông tin 1 sản phẩm theo ID.
     *
     * @param id ID của sản phẩm cần lấy
     * @return Product nếu tìm thấy, null nếu không tồn tại
     * @throws SQLException nếu có lỗi truy vấn cơ sở dữ liệu
     */
    public Product selectProductById(int id) throws SQLException {
        Product product = null;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PRODUCT_BY_ID)) {

            preparedStatement.setInt(1, id);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    String name = rs.getString("name");
                    BigDecimal price = rs.getBigDecimal("price");
                    int quantity = rs.getInt("quantity");

                    product = new Product(id, name, price, quantity);
                }
            }
        }
        return product;
    }

    /**
     * Thêm mới một sản phẩm vào database.
     *
     * @param product đối tượng Product cần thêm (chưa có id)
     * @return true nếu thêm thành công (số dòng ảnh hưởng > 0)
     * @throws SQLException nếu có lỗi truy vấn cơ sở dữ liệu
     */
    public boolean insertProduct(Product product) throws SQLException {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PRODUCT_SQL)) {

            preparedStatement.setString(1, product.getName());
            preparedStatement.setBigDecimal(2, product.getPrice());
            preparedStatement.setInt(3, product.getQuantity());

            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        }
    }

    /**
     * Cập nhật thông tin một sản phẩm theo ID.
     *
     * @param product đối tượng Product cần cập nhật (đã có id)
     * @return true nếu cập nhật thành công (số dòng ảnh hưởng > 0)
     * @throws SQLException nếu có lỗi truy vấn cơ sở dữ liệu
     */
    public boolean updateProduct(Product product) throws SQLException {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PRODUCT_SQL)) {

            preparedStatement.setString(1, product.getName());
            preparedStatement.setBigDecimal(2, product.getPrice());
            preparedStatement.setInt(3, product.getQuantity());
            preparedStatement.setInt(4, product.getId());

            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        }
    }

    /**
     * Xóa sản phẩm khỏi database theo ID.
     *
     * @param id ID sản phẩm cần xóa
     * @return true nếu xóa thành công (số dòng ảnh hưởng > 0)
     * @throws SQLException nếu có lỗi truy vấn cơ sở dữ liệu
     */
    public boolean deleteProduct(int id) throws SQLException {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PRODUCT_SQL)) {

            preparedStatement.setInt(1, id);

            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        }
    }
}
