package com.example.productmanagement.servlet;

import com.example.productmanagement.dao.ProductDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

/**
 * Servlet xử lý xóa sản phẩm.
 * Mapping URL: /products/delete
 * Chú ý: Bắt buộc thực hiện qua phương thức HTTP POST để đảm bảo an toàn (không dùng GET).
 */
@WebServlet(name = "ProductDeleteServlet", urlPatterns = {"/products/delete"})
public class ProductDeleteServlet extends HttpServlet {

    private ProductDAO productDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        productDAO = new ProductDAO();
    }

    /**
     * Chặn truy cập xóa bằng phương thức GET để tuân thủ chuẩn an toàn REST / Web.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String errorMsg = URLEncoder.encode("Thao tác xóa sản phẩm chỉ chấp nhận phương thức POST!", StandardCharsets.UTF_8);
        response.sendRedirect(request.getContextPath() + "/products?error=" + errorMsg);
    }

    /**
     * Tiếp nhận yêu cầu xóa sản phẩm qua phương thức POST
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String idStr = request.getParameter("id");
        if (idStr == null || idStr.trim().isEmpty()) {
            String errorMsg = URLEncoder.encode("ID sản phẩm cần xóa không hợp lệ!", StandardCharsets.UTF_8);
            response.sendRedirect(request.getContextPath() + "/products?error=" + errorMsg);
            return;
        }

        try {
            int id = Integer.parseInt(idStr.trim());
            boolean deleted = productDAO.deleteProduct(id);

            if (deleted) {
                // Post/Redirect/Get
                response.sendRedirect(request.getContextPath() + "/products?message=delete_success");
            } else {
                String errorMsg = URLEncoder.encode("Không tìm thấy sản phẩm có ID: " + id + " để xóa!", StandardCharsets.UTF_8);
                response.sendRedirect(request.getContextPath() + "/products?error=" + errorMsg);
            }

        } catch (NumberFormatException e) {
            String errorMsg = URLEncoder.encode("ID sản phẩm phải là số nguyên!", StandardCharsets.UTF_8);
            response.sendRedirect(request.getContextPath() + "/products?error=" + errorMsg);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Lỗi cơ sở dữ liệu khi xóa sản phẩm: " + e.getMessage());
            request.getRequestDispatcher("/views/error.jsp").forward(request, response);
        }
    }
}
