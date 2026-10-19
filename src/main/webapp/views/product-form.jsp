<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>
        <c:choose>
            <c:when test="${formAction eq 'edit'}">Cập nhật sản phẩm</c:when>
            <c:otherwise>Thêm sản phẩm mới</c:otherwise>
        </c:choose>
        - Hệ Thống Quản Lý Sản Phẩm
    </title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
<div class="container">
    <div class="header-box">
        <h1>Hệ Thống Quản Lý Sản Phẩm</h1>
        <p>
            <c:choose>
                <c:when test="${formAction eq 'edit'}">Chỉnh sửa thông tin sản phẩm trong hệ thống</c:when>
                <c:otherwise>Nhập đầy đủ thông tin để thêm sản phẩm mới vào hệ thống</c:otherwise>
            </c:choose>
        </p>
    </div>

    <div class="form-container">
        <!-- Thông báo lỗi khi xác thực không đạt (nếu có) -->
        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger">
                <strong>✕ Lỗi nhập liệu:</strong> <c:out value="${errorMessage}" />
            </div>
        </c:if>

        <div class="card">
            <h2 class="action-bar-title" style="margin-bottom: 20px; border-bottom: 1px solid var(--neutral-200); padding-bottom: 12px;">
                <c:choose>
                    <c:when test="${formAction eq 'edit'}">
                        Cập nhật sản phẩm #<c:out value="${product.id}" />
                    </c:when>
                    <c:otherwise>
                        Thêm sản phẩm mới
                    </c:otherwise>
                </c:choose>
            </h2>

            <!-- Biểu mẫu gửi dữ liệu bằng phương thức POST -->
            <form action="${pageContext.request.contextPath}/products/<c:out value='${formAction eq \"edit\" ? \"edit\" : \"create\"}' />"
                  method="post"
                  id="productForm">

                <!-- Nếu là chế độ sửa, đính kèm ID ẩn -->
                <c:if test="${formAction eq 'edit'}">
                    <input type="hidden" name="id" value="${product.id}">
                </c:if>

                <!-- Trường 1: Tên sản phẩm -->
                <div class="form-group">
                    <label for="name">Tên sản phẩm <span class="required">*</span></label>
                    <input type="text"
                           id="name"
                           name="name"
                           class="form-control"
                           placeholder="Ví dụ: Laptop Dell Inspiron 15"
                           value="<c:out value='${product.name}' />"
                           required
                           autofocus>
                    <div class="form-help">Tên định danh của sản phẩm, không được để trống.</div>
                </div>

                <!-- Trường 2: Giá sản phẩm -->
                <div class="form-group">
                    <label for="price">Giá sản phẩm (VNĐ) <span class="required">*</span></label>
                    <input type="number"
                           id="price"
                           name="price"
                           class="form-control"
                           placeholder="Ví dụ: 15000000"
                           step="0.01"
                           min="0"
                           value="<c:out value='${not empty rawPrice ? rawPrice : product.price}' />"
                           required>
                    <div class="form-help">Giá trị bằng số, lớn hơn hoặc bằng 0 (không âm).</div>
                </div>

                <!-- Trường 3: Số lượng -->
                <div class="form-group">
                    <label for="quantity">Số lượng tồn kho <span class="required">*</span></label>
                    <input type="number"
                           id="quantity"
                           name="quantity"
                           class="form-control"
                           placeholder="Ví dụ: 25"
                           step="1"
                           min="0"
                           value="<c:out value='${not empty rawQuantity ? rawQuantity : product.quantity}' />"
                           required>
                    <div class="form-help">Số nguyên không âm biểu thị số lượng có sẵn trong kho.</div>
                </div>

                <!-- Các nút hành động -->
                <div class="form-actions">
                    <a href="${pageContext.request.contextPath}/products" class="btn btn-secondary">
                        Quay lại danh sách
                    </a>
                    <button type="submit" class="btn btn-primary" id="btn-submit">
                        <c:choose>
                            <c:when test="${formAction eq 'edit'}">Lưu thay đổi</c:when>
                            <c:otherwise>Lưu sản phẩm</c:otherwise>
                        </c:choose>
                    </button>
                </div>
            </form>
        </div>
    </div>

    <div class="footer">
        Product Management System &copy; JSP & Servlet Exam - Jakarta EE 10 / Tomcat 10.1
    </div>
</div>
</body>
</html>
