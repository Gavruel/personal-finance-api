#  Personal Finance API

A RESTful API built with **Java Spring Boot** for personal finance management. The system allows users to register, authenticate, and manage custom transaction categories (income and expenses), laying the foundation for a complete financial control application.

---

##  Tech Stack

- **Java 17+**
- **Spring Boot**
- **Spring Security** (HTTP Basic Auth)
- **Spring Data JPA**
- **Hibernate**
- **Argon2** (Password Hashing)
- **Lombok**
- **Bean Validation (Jakarta)**
- **PostgreSQL** 

---

##  Project Structure

```
personalfinanceapi/
├── config/
│   ├── PasswordConfig.java
│   └── SecurityConfig.java
├── controller/
│   ├── AuthController.java
│   ├── UserController.java
│   └── CategoryController.java
├── dto/
│   ├── user/
│   │   ├── CreateUserDTO.java
│   │   ├── UserRequestDTO.java
│   │   ├── UserResponseDTO.java
│   │   └── UserUpdateDTO.java
│   └── category/
│       ├── CategoryRequestDTO.java
│       └── CategoryResponseDTO.java
├── model/
│   ├── entities/
│   │   ├── User.java
│   │   └── Category.java
│   └── enums/
│       └── TransactionType.java  # INCOME | EXPENSE
├── repository/
│   ├── UserRepository.java
│   └── CategoryRepository.java
├── security/
│   └── CustomUserDetailsService.java
├── service/
│   ├── UserService.java
│   ├── CategoryService.java
│   └── PasswordService.java
└── PersonalFinanceApiApplication.java
```

---

##  Getting Started

### Prerequisites

- Java 17+
- Maven
- A running database (PostgreSQL recommended)

### Installation

1. **Clone the repository**

```bash
git clone https://github.com/Gavruel/personalfinanceapi.git
cd personalfinanceapi
```

2. **Configure your database** in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/personalfinance
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

3. **Build and run**

```bash
./mvnw spring-boot:run
```

The API will be available at `http://localhost:8080`.

---

##  Authentication

This API uses **HTTP Basic Authentication**. After registering, include your credentials in every request to protected routes.

> **Note:** The `/auth/register` endpoint is **public** — all other endpoints require authentication.

---

##  API Reference

###  Auth

#### Register a new user

```http
POST /auth/register
```

**Request Body:**

| Field      | Type     | Constraints                           |
| :--------- | :------- | :------------------------------------ |
| `name`     | `string` | **Required**                          |
| `email`    | `string` | **Required**. Must be a valid email   |
| `password` | `string` | **Required**. Minimum 6 characters   |

**Example:**
```json
{
  "name": "Gabriel Santos",
  "email": "gabriel@email.com",
  "password": "securepass123"
}
```

**Response** `201 Created`:
```json
{
  "id": "uuid",
  "name": "Gabriel Santos",
  "email": "gabriel@email.com"
}
```

---

###  Users

> All user routes require **HTTP Basic Auth**.

#### Get all users

```http
GET /api/users
```

**Response** `200 OK`:
```json
[
  {
    "id": "uuid",
    "name": "Gabriel Santos",
    "email": "gabriel@email.com"
  }
]
```

---

#### Update a user

```http
PUT /api/users/{id}
```

| Parameter | Type     | Description                         |
| :-------- | :------- | :---------------------------------- |
| `id`      | `string` | **Required**. UUID of the user      |

**Request Body:**

| Field      | Type     | Constraints                           |
| :--------- | :------- | :------------------------------------ |
| `name`     | `string` | Optional                              |
| `email`    | `string` | Optional                              |
| `password` | `string` | Optional. Minimum 6 characters       |

**Response** `200 OK`:
```json
{
  "id": "uuid",
  "name": "Gabriel Updated",
  "email": "newemail@email.com"
}
```

---

#### Delete a user

```http
DELETE /api/users/{id}
```

| Parameter | Type     | Description                         |
| :-------- | :------- | :---------------------------------- |
| `id`      | `string` | **Required**. UUID of the user      |

**Response** `204 No Content`

---

###  Categories

> All category routes require **HTTP Basic Auth**.

#### Create a category for a user

```http
POST /api/categories/users/{userId}/categories
```

| Parameter | Type     | Description                           |
| :-------- | :------- | :------------------------------------ |
| `userId`  | `string` | **Required**. UUID of the user        |

**Request Body:**

| Field  | Type              | Constraints                            |
| :----- | :---------------- | :------------------------------------- |
| `name` | `string`          | **Required**                           |
| `type` | `TransactionType` | **Required**. `INCOME` or `EXPENSE`    |

**Example:**
```json
{
  "name": "Freelance",
  "type": "INCOME"
}
```

**Response** `201 Created`:
```json
{
  "id": "uuid",
  "name": "Freelance",
  "type": "INCOME",
  "userId": "uuid"
}
```

---

#### Get all categories for a user

```http
GET /api/categories/users/{userId}/categories
```

| Parameter | Type     | Description                           |
| :-------- | :------- | :------------------------------------ |
| `userId`  | `string` | **Required**. UUID of the user        |

**Response** `200 OK`:
```json
[
  {
    "id": "uuid",
    "name": "Freelance",
    "type": "INCOME",
    "userId": "uuid"
  },
  {
    "id": "uuid",
    "name": "Groceries",
    "type": "EXPENSE",
    "userId": "uuid"
  }
]
```

---

##  Data Models

### User

| Field      | Type     | Notes                         |
| :--------- | :------- | :---------------------------- |
| `id`       | `UUID`   | Auto-generated                |
| `name`     | `string` | Required                      |
| `email`    | `string` | Required, unique              |
| `password` | `string` | Hashed with Argon2            |

### Category

| Field  | Type              | Notes                                        |
| :----- | :---------------- | :------------------------------------------- |
| `id`   | `UUID`            | Auto-generated                               |
| `name` | `string`          | Max 100 chars. Unique per user               |
| `type` | `TransactionType` | `INCOME` or `EXPENSE`                        |
| `user` | `User`            | Many-to-one relationship                     |

---

##  Security Details

- Passwords are hashed using **Argon2** with the following parameters:
  - Salt length: 16 bytes
  - Hash length: 32 bytes
  - Parallelism: 2
  - Memory: 65536 KB
  - Iterations: 3
- CSRF is disabled (stateless REST API)
- Authentication is performed via **email + password** (HTTP Basic)

---

##  Roadmap

- [ ] Transaction management (CRUD)
- [ ] Financial reports and summaries
- [ ] JWT-based authentication
- [ ] Swagger / OpenAPI documentation
- [ ] Docker support

---

##  Contributing

Contributions are welcome! Feel free to open an issue or submit a pull request.

---

##  License

This project is open source and available under the [MIT License](LICENSE).
