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
import java.sql.SQLException;

/**
 * Servlet xử lý thêm mới sản phẩm.
 * Mapping URL: /products/create
 */
@WebServlet(name = "ProductCreateServlet", urlPatterns = {"/products/create"})
public class ProductCreateServlet extends HttpServlet {

    private ProductDAO productDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        productDAO = new ProductDAO();
    }

    /**
     * Hiển thị biểu mẫu thêm sản phẩm mới
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        request.setAttribute("formAction", "create");
        request.getRequestDispatcher("/views/product-form.jsp").forward(request, response);
    }

    /**
     * Tiếp nhận dữ liệu biểu mẫu và lưu sản phẩm mới
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String name = request.getParameter("name");
        String priceStr = request.getParameter("price");
        String quantityStr = request.getParameter("quantity");

        String errorMessage = null;
        BigDecimal price = null;
        Integer quantity = null;

        // 1. Kiểm tra tính hợp lệ của Tên sản phẩm
        if (name == null || name.trim().isEmpty()) {
            errorMessage = "Tên sản phẩm không được để trống!";
        }

        // 2. Kiểm tra tính hợp lệ của Giá sản phẩm
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

        // 3. Kiểm tra tính hợp lệ của Số lượng
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

        // Nếu có lỗi xác thực dữ liệu: giữ lại dữ liệu người dùng đã nhập và quay lại form
        if (errorMessage != null) {
            request.setAttribute("errorMessage", errorMessage);
            request.setAttribute("formAction", "create");
            // Đóng gói tạm dữ liệu để hiển thị lại trên form
            Product tempProduct = new Product();
            tempProduct.setName(name);
            tempProduct.setPrice(price);
            tempProduct.setQuantity(quantity != null ? quantity : 0);
            request.setAttribute("product", tempProduct);
            request.setAttribute("rawPrice", priceStr);
            request.setAttribute("rawQuantity", quantityStr);

            request.getRequestDispatcher("/views/product-form.jsp").forward(request, response);
            return;
        }

        // Lưu sản phẩm vào cơ sở dữ liệu
        try {
            Product newProduct = new Product(name.trim(), price, quantity);
            boolean success = productDAO.insertProduct(newProduct);

            if (success) {
                // Áp dụng mô hình Post/Redirect/Get để tránh duplicate submission khi F5
                response.sendRedirect(request.getContextPath() + "/products?message=create_success");
            } else {
                request.setAttribute("errorMessage", "Không thể thêm sản phẩm. Vui lòng thử lại!");
                request.setAttribute("formAction", "create");
                request.getRequestDispatcher("/views/product-form.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Lỗi cơ sở dữ liệu khi thêm sản phẩm: " + e.getMessage());
            request.getRequestDispatcher("/views/error.jsp").forward(request, response);
        }
    }
}
