package com.example.productmanagement.servlet;

import com.example.productmanagement.dao.ProductDAO;
import com.example.productmanagement.model.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

/**
 * Servlet xử lý cập nhật (sửa) sản phẩm.
 * Mapping URL: /products/edit
 */
@WebServlet(name = "ProductEditServlet", urlPatterns = {"/products/edit"})
public class ProductEditServlet extends HttpServlet {

    private ProductDAO productDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        productDAO = new ProductDAO();
    }

    /**
     * Hiển thị biểu mẫu sửa sản phẩm với thông tin hiện có
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String idStr = request.getParameter("id");
        if (idStr == null || idStr.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/products?error=" +
                    URLEncoder.encode("ID sản phẩm không hợp lệ!", StandardCharsets.UTF_8));
            return;
        }

        try {
            int id = Integer.parseInt(idStr.trim());
            Product product = productDAO.selectProductById(id);

            if (product == null) {
                response.sendRedirect(request.getContextPath() + "/products?error=" +
                        URLEncoder.encode("Sản phẩm có ID " + id + " không tồn tại!", StandardCharsets.UTF_8));
                return;
            }

            request.setAttribute("product", product);
            request.setAttribute("formAction", "edit");
            request.getRequestDispatcher("/views/product-form.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/products?error=" +
                    URLEncoder.encode("ID sản phẩm phải là số nguyên!", StandardCharsets.UTF_8));
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Lỗi cơ sở dữ liệu khi tải thông tin sản phẩm: " + e.getMessage());
            request.getRequestDispatcher("/views/error.jsp").forward(request, response);
        }
    }

    /**
     * Tiếp nhận dữ liệu cập nhật và lưu thay đổi vào database
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String idStr = request.getParameter("id");
        String name = request.getParameter("name");
        String priceStr = request.getParameter("price");
        String quantityStr = request.getParameter("quantity");

        String errorMessage = null;
        int id = -1;
        BigDecimal price = null;
        Integer quantity = null;

        // 1. Kiểm tra ID
        try {
            id = Integer.parseInt(idStr != null ? idStr.trim() : "");
        } catch (NumberFormatException e) {
            errorMessage = "ID sản phẩm không hợp lệ!";
        }

        // 2. Kiểm tra Tên sản phẩm
        if (errorMessage == null) {
            if (name == null || name.trim().isEmpty()) {
                errorMessage = "Tên sản phẩm không được để trống!";
            }
        }

        // 3. Kiểm tra Giá
        if (errorMessage == null) {
            if (priceStr == null || priceStr.trim().isEmpty()) {
                errorMessage = "Giá sản phẩm không được để trống!";
            } else {
                try {
                    price = new BigDecimal(priceStr.trim());
                    if (price.compareTo(BigDecimal.ZERO) < 0) {
                        errorMessage = "Giá sản phẩm phải là số không âm (>= 0)!";
                    }
                } catch (NumberFormatException e) {
                    errorMessage = "Giá sản phẩm không hợp lệ! Vui lòng nhập số.";
                }
            }
        }

        // 4. Kiểm tra Số lượng
        if (errorMessage == null) {
            if (quantityStr == null || quantityStr.trim().isEmpty()) {
                errorMessage = "Số lượng không được để trống!";
            } else {
                try {
                    quantity = Integer.parseInt(quantityStr.trim());
                    if (quantity < 0) {
                        errorMessage = "Số lượng sản phẩm phải là số nguyên không âm (>= 0)!";
                    }
                } catch (NumberFormatException e) {
                    errorMessage = "Số lượng không hợp lệ! Vui lòng nhập số nguyên.";
                }
            }
        }

        // Nếu có lỗi dữ liệu: gửi lại form với thông báo lỗi
        if (errorMessage != null) {
            request.setAttribute("errorMessage", errorMessage);
            request.setAttribute("formAction", "edit");
            Product tempProduct = new Product(id, name, price, quantity != null ? quantity : 0);
            request.setAttribute("product", tempProduct);
            request.setAttribute("rawPrice", priceStr);
            request.setAttribute("rawQuantity", quantityStr);
            request.getRequestDispatcher("/views/product-form.jsp").forward(request, response);
            return;
        }

        // Cập nhật vào cơ sở dữ liệu
        try {
            Product product = new Product(id, name.trim(), price, quantity);
            boolean updated = productDAO.updateProduct(product);

            if (updated) {
                // Post/Redirect/Get
                response.sendRedirect(request.getContextPath() + "/products?message=edit_success");
            } else {
                request.setAttribute("errorMessage", "Không thể cập nhật. Có thể sản phẩm không còn tồn tại!");
                request.setAttribute("formAction", "edit");
                request.setAttribute("product", product);
                request.getRequestDispatcher("/views/product-form.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Lỗi cơ sở dữ liệu khi cập nhật sản phẩm: " + e.getMessage());
            request.getRequestDispatcher("/views/error.jsp").forward(request, response);
        }
    }
}
