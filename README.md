# Identity Service - Session 13 Exercise 01

## Giới thiệu
Dự án **Identity Service** được xây dựng bằng **Spring Boot 3 (Java 21, Gradle)**, đóng vai trò quản lý danh tính (Identity) và bảo mật mật khẩu người dùng với PostgreSQL và BCrypt.

## Yêu cầu kỹ thuật đã thực hiện
- **Ngôn ngữ & Công cụ**: Java 21, Gradle.
- **Thư viện (Dependencies)**:
  - `org.springframework.boot:spring-boot-starter-data-jpa`
  - `org.springframework.boot:spring-boot-starter-security`
  - `org.springframework.boot:spring-boot-starter-web`
  - `org.postgresql:postgresql`
  - Lombok
- **Database**: PostgreSQL (Database `identity_db`).
- **Thực thi**:
  - Entity `User` với các trường `id` (UUID), `username`, `password`, `role`.
  - Cấu hình `SecurityFilterChain`: tắt CSRF, `permitAll()` cho endpoint đăng ký `/api/auth/register` (hoặc `/api/auth/**`).
  - Cấu hình `BCryptPasswordEncoder` bean.
  - Viết API `POST /api/auth/register`: Nhận thông tin đăng ký và băm mật khẩu bằng BCrypt trước khi lưu vào Database.

## Cấu trúc thư mục
```
identity-service/
├── gradle/wrapper/
│   ├── gradle-wrapper.jar
│   └── gradle-wrapper.properties
├── src/
│   ├── main/
│   │   ├── java/com/rikkei/microservice/identity/
│   │   │   ├── config/
│   │   │   │   └── SecurityConfig.java
│   │   │   ├── controller/
│   │   │   │   └── AuthController.java
│   │   │   ├── dto/
│   │   │   │   ├── RegisterRequest.java
│   │   │   │   └── UserResponse.java
│   │   │   ├── entity/
│   │   │   │   └── User.java
│   │   │   ├── repository/
│   │   │   │   └── UserRepository.java
│   │   │   ├── service/
│   │   │   │   └── AuthService.java
│   │   │   └── IdentityServiceApplication.java
│   │   └── resources/
│   │       └── application.yml
│   └── test/
│       └── java/com/rikkei/microservice/identity/
│           └── IdentityServiceApplicationTests.java
├── .gitignore
├── build.gradle
├── gradlew
├── gradlew.bat
├── README.md
└── settings.gradle
```

## Hướng dẫn chạy và kiểm thử

### 1. Cấu hình cơ sở dữ liệu
Đảm bảo PostgreSQL đang chạy trên cổng 5432 và đã tạo database `identity_db`:
```sql
CREATE DATABASE identity_db;
```

### 2. Build & Test
Chạy lệnh test với Gradle Wrapper:
```bash
./gradlew test
```

### 3. Khởi chạy ứng dụng
```bash
./gradlew bootRun
```

### 4. Đăng ký tài khoản (API Register)
- **Endpoint**: `POST http://localhost:8080/api/auth/register`
- **Headers**: `Content-Type: application/json`
- **Body**:
```json
{
  "username": "john_doe",
  "password": "mySecurePassword123",
  "role": "ROLE_USER"
}
```
- **Response**:
```json
{
  "id": "e44d34eb-5ff6-4ad2-a9b3-1cb8dd2cce61",
  "username": "john_doe",
  "role": "ROLE_USER"
}
```
Mật khẩu trong database sẽ được mã hóa bằng chuẩn BCrypt (`$2a$10$...`).
