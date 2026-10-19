package com.example.productmanagement.servlet;

import com.example.productmanagement.dao.ProductDAO;
import com.example.productmanagement.model.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Servlet hiển thị danh sách sản phẩm.
 * Mapping URL: /products
 */
@WebServlet(name = "ProductListServlet", urlPatterns = {"/products"})
public class ProductListServlet extends HttpServlet {

    private ProductDAO productDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        productDAO = new ProductDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Thiết lập mã hóa tiếng Việt
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        try {
            List<Product> productList = productDAO.selectAllProducts();
            request.setAttribute("productList", productList);

            // Kiểm tra các thông báo được truyền từ redirect (Post/Redirect/Get)
            String message = request.getParameter("message");
            if (message != null) {
                switch (message) {
                    case "create_success":
                        request.setAttribute("successMessage", "Thêm sản phẩm mới thành công!");
                        break;
                    case "edit_success":
                        request.setAttribute("successMessage", "Cập nhật sản phẩm thành công!");
                        break;
                    case "delete_success":
                        request.setAttribute("successMessage", "Xóa sản phẩm thành công!");
                        break;
                    default:
                        break;
                }
            }

            String errorMessage = request.getParameter("error");
            if (errorMessage != null && !errorMessage.trim().isEmpty()) {
                request.setAttribute("errorMessage", errorMessage);
            }

            request.getRequestDispatcher("/views/product-list.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Không thể kết nối đến cơ sở dữ liệu: " + e.getMessage());
            request.getRequestDispatcher("/views/error.jsp").forward(request, response);
        }
    }
}
