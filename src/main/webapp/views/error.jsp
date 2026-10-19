<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thông Báo Lỗi - Hệ Thống Quản Lý Sản Phẩm</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
<div class="container">
    <div class="header-box">
        <h1>Hệ Thống Quản Lý Sản Phẩm</h1>
        <p>Đã xảy ra sự cố trong quá trình xử lý yêu cầu</p>
    </div>

    <div class="form-container">
        <div class="card" style="text-align: center; padding: 36px 24px;">
            <div style="font-size: 3rem; margin-bottom: 12px;">⚠️</div>
            <h2 style="color: var(--danger-text); margin-bottom: 16px; font-size: 1.4rem;">
                Xảy ra lỗi hệ thống
            </h2>

            <div class="alert alert-danger" style="text-align: left; display: block;">
                <strong>Chi tiết lỗi:</strong><br>
                <c:choose>
                    <c:when test="${not empty errorMessage}">
                        <c:out value="${errorMessage}" />
                    </c:when>
                    <c:otherwise>
                        Đã xảy ra lỗi không xác định. Vui lòng kiểm tra lại log máy chủ Tomcat hoặc kết nối MySQL.
                    </c:otherwise>
                </c:choose>
            </div>

            <div style="background-color: var(--neutral-100); padding: 14px; border-radius: 6px; text-align: left; font-size: 0.85rem; color: var(--neutral-700); margin-bottom: 24px;">
                <strong>Gợi ý khắc phục:</strong>
                <ul style="margin-left: 20px; margin-top: 6px; line-height: 1.5;">
                    <li>Kiểm tra xem MySQL Server (XAMPP / MySQL Service) đã được bật chưa (cổng 3306).</li>
                    <li>Kiểm tra file cấu hình <code>src/main/resources/db.properties</code> đã điền đúng tài khoản/mật khẩu MySQL chưa.</li>
                    <li>Đảm bảo đã chạy file <code>database/product_management.sql</code> để tạo cơ sở dữ liệu và bảng.</li>
                </ul>
            </div>

            <a href="${pageContext.request.contextPath}/products" class="btn btn-primary">
                Quay lại Trang Chủ
            </a>
        </div>
    </div>

    <div class="footer">
        Product Management System &copy; JSP & Servlet Exam - Jakarta EE 10 / Tomcat 10.1
    </div>
</div>
</body>
</html>
