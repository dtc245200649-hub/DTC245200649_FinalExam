<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Hệ Thống Quản Lý Sản Phẩm - Danh sách</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
<div class="container">
    <!-- Tiêu đề trang -->
    <div class="header-box">
        <h1>Hệ Thống Quản Lý Sản Phẩm</h1>
        <p>Bài kiểm tra cuối module: Phát triển ứng dụng Web với JSP, Servlet & JDBC</p>
    </div>

    <!-- Thông báo thành công (nếu có) -->
    <c:if test="${not empty successMessage}">
        <div class="alert alert-success">
            <strong>✓ Thành công:</strong> <c:out value="${successMessage}" />
        </div>
    </c:if>

    <!-- Thông báo lỗi (nếu có) -->
    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger">
            <strong>✕ Thông báo:</strong> <c:out value="${errorMessage}" />
        </div>
    </c:if>

    <div class="card">
        <!-- Thanh công cụ & nút Thêm mới -->
        <div class="action-bar">
            <h2 class="action-bar-title">Danh sách sản phẩm</h2>
            <a href="${pageContext.request.contextPath}/products/create" class="btn btn-primary" id="btn-add-product">
                + Thêm sản phẩm mới
            </a>
        </div>

        <!-- Bảng hiển thị danh sách sản phẩm -->
        <div class="table-responsive">
            <table class="table">
                <thead>
                <tr>
                    <th class="text-center" style="width: 70px;">ID</th>
                    <th>Tên sản phẩm</th>
                    <th class="text-right" style="width: 170px;">Giá (VNĐ)</th>
                    <th class="text-center" style="width: 110px;">Số lượng</th>
                    <th class="text-center" style="width: 160px;">Thao tác</th>
                </tr>
                </thead>
                <tbody>
                <c:choose>
                    <c:when test="${not empty productList}">
                        <c:forEach var="p" items="${productList}">
                            <tr>
                                <td class="text-center font-bold">#<c:out value="${p.id}" /></td>
                                <td><c:out value="${p.name}" /></td>
                                <td class="text-right price-text">
                                    <fmt:formatNumber value="${p.price}" type="currency" currencySymbol="₫" maxFractionDigits="0" />
                                </td>
                                <td class="text-center"><c:out value="${p.quantity}" /></td>
                                <td class="text-center">
                                    <div class="action-buttons" style="justify-content: center;">
                                        <!-- Nút Sửa -->
                                        <a href="${pageContext.request.contextPath}/products/edit?id=${p.id}"
                                           class="btn btn-sm btn-warning"
                                           title="Sửa thông tin sản phẩm">
                                            Sửa
                                        </a>

                                        <!-- Nút Xóa (bắt buộc gửi bằng HTTP POST, có xác nhận JS) -->
                                        <form action="${pageContext.request.contextPath}/products/delete"
                                              method="post"
                                              class="inline-form"
                                              onsubmit="return confirmDelete('<c:out value="${p.name}" />');">
                                            <input type="hidden" name="id" value="${p.id}">
                                            <button type="submit"
                                                    class="btn btn-sm btn-danger"
                                                    title="Xóa sản phẩm">
                                                Xóa
                                            </button>
                                        </form>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td colspan="5">
                                <div class="empty-state">
                                    <span>📦</span>
                                    Chưa có sản phẩm nào trong hệ thống. Hãy bấm <strong>"Thêm sản phẩm mới"</strong> để bắt đầu!
                                </div>
                            </td>
                        </tr>
                    </c:otherwise>
                </c:choose>
                </tbody>
            </table>
        </div>
    </div>

    <div class="footer">
        Product Management System &copy; JSP & Servlet Exam - Jakarta EE 10 / Tomcat 10.1
    </div>
</div>

<script>
    /**
     * Xác nhận trước khi xóa sản phẩm
     * @param productName Tên sản phẩm cần xóa
     * @returns {boolean} true nếu người dùng đồng ý xóa, ngược lại false
     */
    function confirmDelete(productName) {
        return confirm("Bạn có chắc chắn muốn xóa sản phẩm \"" + productName + "\" không?\nThao tác này không thể hoàn tác!");
    }
</script>
</body>
</html>
