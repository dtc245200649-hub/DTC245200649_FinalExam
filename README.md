# HỆ THỐNG QUẢN LÝ SẢN PHẨM (PRODUCT MANAGEMENT SYSTEM)
**Bài kiểm tra cuối module: Phát triển ứng dụng Web với JSP & Servlet**

---

## 1. Giới thiệu dự án

Dự án là ứng dụng Web Java hoàn chỉnh theo mô hình MVC (Model - View - Controller), thực hiện các chức năng quản lý danh mục sản phẩm (thêm, hiển thị, sửa, xóa - CRUD) kết nối cơ sở dữ liệu MySQL qua JDBC.

### Công nghệ sử dụng:
- **Ngôn ngữ**: Java 17 (tương thích Java 17+)
- **Máy chủ ứng dụng (Web Server)**: Apache Tomcat 10.1+
- **Servlet API**: Jakarta Servlet API 6.0 (`jakarta.servlet.*`)
- **Hiển thị giao diện**: JSP (JavaServer Pages), JSTL 3.0, HTML5, Vanilla CSS
- **Cơ sở dữ liệu**: MySQL 8.x
- **Kết nối cơ sở dữ liệu**: MySQL Connector/J 8.3 qua JDBC
- **Quản lý dự án & Build**: Apache Maven

---

## 2. Cấu trúc thư mục dự án

```text
product-management/
├── pom.xml                                     # Cấu hình Maven dependencies & build WAR
├── README.md                                   # Hướng dẫn chi tiết cài đặt và kiểm thử
├── database/
│   └── product_management.sql                  # Script tạo Database, bảng products và dữ liệu mẫu
└── src/
    └── main/
        ├── java/
        │   └── com/
        │       └── example/
        │           └── productmanagement/
        │               ├── model/
        │               │   └── Product.java            # Lớp Model đại diện cho sản phẩm
        │               ├── dao/
        │               │   └── ProductDAO.java         # Data Access Object thực hiện các câu lệnh SQL
        │               ├── util/
        │               │   └── DBConnection.java       # Quản lý kết nối JDBC MySQL an toàn
        │               └── servlet/
        │                   ├── ProductListServlet.java   # Controller hiển thị danh sách (/products)
        │                   ├── ProductCreateServlet.java # Controller thêm sản phẩm (/products/create)
        │                   ├── ProductEditServlet.java   # Controller sửa sản phẩm (/products/edit)
        │                   └── ProductDeleteServlet.java # Controller xóa sản phẩm (/products/delete)
        ├── resources/
        │   ├── db.properties                           # File cấu hình kết nối DB
        │   └── db.properties.example                   # File mẫu cấu hình DB
        └── webapp/
            ├── index.jsp                               # Tự động chuyển hướng về /products
            ├── WEB-INF/
            │   └── web.xml                             # Cấu hình ứng dụng Web (Servlet 6.0)
            ├── views/
            │   ├── product-list.jsp                    # Giao diện bảng danh sách sản phẩm
            │   ├── product-form.jsp                    # Giao diện Form thêm và sửa sản phẩm
            │   └── error.jsp                           # Giao diện thông báo lỗi thân thiện
            └── assets/
                └── css/
                    └── style.css                       # Giao diện CSS hiện đại, tương thích di động
```

---

## 3. Hướng dẫn từng bước cài đặt và chạy ứng dụng

### Bước 1: Kiểm tra Java và Maven
Mở Command Prompt (cmd) hoặc PowerShell trên máy tính:
```bash
java -version
mvn -v
```
> Yêu cầu: Java phiên bản từ 17 trở lên và Maven từ 3.8 trở lên.

### Bước 2: Cài đặt và khởi động MySQL Server
- Bạn có thể sử dụng **MySQL Server độc lập** hoặc **XAMPP / Laragon / MySQL Workbench**.
- Khởi động MySQL Server trên cổng mặc định `3306`.

### Bước 3: Chạy script tạo cơ sở dữ liệu
1. Mở công cụ quản lý cơ sở dữ liệu (MySQL Workbench, phpMyAdmin, DBeaver, hoặc MySQL CLI).
2. Mở file: `database/product_management.sql`.
3. Thực thi toàn bộ script. Script sẽ tự động:
   - Tạo cơ sở dữ liệu `product_management`.
   - Tạo bảng `products` gồm các cột `id, name, price, quantity, created_at`.
   - Thêm 6 bản ghi sản phẩm mẫu.

### Bước 4: Cấu hình thông tin kết nối MySQL
Mở file `src/main/resources/db.properties` và chỉnh sửa mật khẩu MySQL của máy bạn:
```properties
db.url=jdbc:mysql://localhost:3306/product_management?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Ho_Chi_Minh&characterEncoding=UTF-8
db.username=root
db.password=YOUR_PASSWORD_HERE
db.driver=com.mysql.cj.jdbc.Driver
```
*(Nếu dùng XAMPP thì thông thường `db.username=root` và `db.password=` để trống).*

### Bước 5: Build dự án với Maven
Mở terminal tại thư mục chứa file `pom.xml` (`product-management/`) và chạy lệnh:
```bash
mvn clean package
```
Sau khi quá trình build hoàn tất thành công, bạn sẽ thấy thông báo `BUILD SUCCESS`.

### Bước 6: Xác định file WAR
File nén triển khai nằm tại thư mục `target/`:
```text
target/product-management.war
```

### Bước 7: Triển khai lên Apache Tomcat 10.1
1. **Cách 1: Triển khai trực tiếp (Khuyên dùng)**
   - Sao chép file `target/product-management.war` vào thư mục `webapps/` của thư mục cài đặt Apache Tomcat 10.1:
     ```text
     [ĐƯỜNG_DẪN_TOMCAT]/webapps/product-management.war
     ```
   - Khởi động Tomcat bằng cách chạy:
     - Trên Windows: Chạy file `bin/startup.bat`.
     - Trên Linux/macOS: Chạy `bin/startup.sh`.
   - Tomcat sẽ tự động giải nén file `.war` thành thư mục `product-management`.

2. **Cách 2: Chạy trực tiếp trên IDE (IntelliJ IDEA / Eclipse)**
   - Mở dự án `product-management` bằng IntelliJ hoặc Eclipse dạng Maven Project.
   - Thêm cấu hình chạy **Tomcat Server (Local)** trỏ đến thư mục cài Tomcat 10.1.
   - Thêm Artifact `product-management:war` hoặc `product-management:war exploded` vào mục Deployment với Application Context là `/product-management`.
   - Nhấn **Run / Debug**.

### Bước 8: Truy cập ứng dụng trên trình duyệt
Mở trình duyệt web (Chrome, Edge, Firefox) và truy cập đường dẫn:
```text
http://localhost:8080/product-management/
```
hoặc trực tiếp:
```text
http://localhost:8080/product-management/products
```

---

## 4. Bảng kiểm thử thủ công (Testing Checklist)

| STT | Kịch bản kiểm thử | Thao tác thực hiện | Kết quả mong đợi |
| :---: | :--- | :--- | :--- |
| **1** | **Hiển thị danh sách** | Truy cập `http://localhost:8080/product-management/` | Tự động chuyển đến `/products`, hiển thị bảng đầy đủ các sản phẩm mẫu có trong database (ID, Tên, Giá VNĐ, Số lượng) cùng các nút Thao tác. |
| **2** | **Thêm sản phẩm hợp lệ** | Bấm "+ Thêm sản phẩm mới", nhập: Tên: `Bàn phím cơ AKKO`, Giá: `1250000`, Số lượng: `10` -> Bấm "Lưu sản phẩm". | Quay lại trang danh sách, hiển thị thông báo màu xanh "Thêm sản phẩm mới thành công!" và sản phẩm mới xuất hiện trên bảng. |
| **3** | **Thêm tên sản phẩm trống** | Bấm "+ Thêm sản phẩm mới", để trống tên, nhập giá `500000`, số lượng `5` -> Bấm Lưu. | Hiển thị cảnh báo màu đỏ: "Tên sản phẩm không được để trống!", dữ liệu giá và số lượng được giữ nguyên trên form. |
| **4** | **Nhập giá âm hoặc sai định dạng** | Nhập Giá: `-1000` hoặc chữ `abc`. | Trình duyệt và Server bắt lỗi: "Giá sản phẩm phải là số không âm (>= 0)!". Không lưu vào DB. |
| **5** | **Nhập số lượng âm hoặc số lẻ** | Nhập Số lượng: `-5` hoặc `3.5`. | Báo lỗi: "Số lượng sản phẩm phải là số nguyên không âm (>= 0)!". |
| **6** | **Sửa sản phẩm thành công** | Bấm nút "Sửa" ở sản phẩm ID 1. Sửa giá và số lượng -> Bấm "Lưu thay đổi". | Chuyển về danh sách, thông báo "Cập nhật sản phẩm thành công!", dữ liệu trên bảng được cập nhật chính xác. |
| **7** | **Sửa sản phẩm ID không tồn tại** | Gõ trực tiếp trên thanh URL: `/products/edit?id=99999`. | Chuyển hướng về danh sách sản phẩm kèm thông báo lỗi: "Sản phẩm có ID 99999 không tồn tại!". |
| **8** | **Xóa sản phẩm có xác nhận** | Bấm nút "Xóa" tại một sản phẩm. Khi hộp thoại xác nhận hiện lên -> Bấm **OK**. | Yêu cầu được gửi qua HTTP POST, sản phẩm bị xóa khỏi database, hiển thị thông báo "Xóa sản phẩm thành công!". |
| **9** | **Hủy thao tác xóa** | Bấm nút "Xóa" tại một sản phẩm -> Khi hộp thoại hiện lên, bấm **Cancel / Hủy**. | Không có request nào gửi đi, sản phẩm vẫn được giữ nguyên. |
| **10** | **Kiểm tra an toàn HTTP POST cho Xóa** | Thử gõ trực tiếp trên trình duyệt URL: `/products/delete?id=1` (phương thức GET). | Hệ thống chặn thao tác GET và chuyển về trang danh sách thông báo: "Thao tác xóa sản phẩm chỉ chấp nhận phương thức POST!". |
| **11** | **Xử lý khi Database ngắt kết nối** | Tắt dịch vụ MySQL rồi tải lại trang `/products`. | Chuyển đến trang `views/error.jsp` với thông báo lỗi thân thiện và hướng dẫn kiểm tra kết nối, không để lộ crash trace ra ngoài. |

---

## 5. Hướng dẫn xử lý các lỗi thường gặp

1. **Lỗi `Access denied for user 'root'@'localhost'`**:
   - **Nguyên nhân**: Mật khẩu MySQL trong `db.properties` không khớp với mật khẩu máy của bạn.
   - **Khắc phục**: Mở `src/main/resources/db.properties`, sửa lại dòng `db.password=...` cho đúng với mật khẩu của bạn rồi đóng gói lại (`mvn clean package`).

2. **Lỗi `Communications link failure`**:
   - **Nguyên nhân**: MySQL Server chưa được bật hoặc đang chạy ở cổng khác 3306.
   - **Khắc phục**: Mở XAMPP Control Panel hoặc Windows Services và khởi động MySQL Service.

3. **Lỗi 404 Not Found**:
   - **Nguyên nhân**: Tên context path không đúng hoặc chưa deploy thành công file WAR.
   - **Khắc phục**: Đảm bảo truy cập đúng `http://localhost:8080/product-management/`. Kiểm tra thư mục `webapps` trong Tomcat để xem file `.war` đã giải nén chưa.

4. **Lỗi `ClassNotFoundException: jakarta.servlet.*`**:
   - **Nguyên nhân**: Đang chạy trên Tomcat 9 trở xuống (Tomcat 9 sử dụng namespace cũ `javax.servlet`).
   - **Khắc phục**: Dự án sử dụng chuẩn Jakarta EE 10, bắt buộc chạy trên **Apache Tomcat 10.1+**.

5. **Lỗi Tiếng Việt bị lỗi font (ký tự lạ `???`)**:
   - **Nguyên nhân**: Kết nối JDBC hoặc request chưa cấu hình UTF-8.
   - **Khắc phục**: Dự án đã tích hợp sẵn `characterEncoding=UTF-8` trong JDBC URL và `request.setCharacterEncoding("UTF-8")` trong tất cả các Servlet.

---

## 6. Đóng gói nộp bài

Toàn bộ mã nguồn, cấu hình Maven, file SQL và tài liệu được nén thành file ZIP theo cấu trúc chuẩn:
```text
[MA_SO_SINH_VIEN]_FinalExam.zip
```
*(Trong đó `[MA_SO_SINH_VIEN]` được thay thế bằng Mã số sinh viên của bạn khi nộp bài).*
