<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    // Tự động điều hướng đến danh sách sản phẩm (/products)
    response.sendRedirect(request.getContextPath() + "/products");
%>
