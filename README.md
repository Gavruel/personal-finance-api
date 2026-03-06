# Personal Finance API

A RESTful API built with **Java Spring Boot 4** for personal finance management. The system allows users to register, authenticate, manage transaction categories, and record financial transactions (income and expenses) with full validation and standardized error handling.

---

## Tech Stack

- **Java 21**
- **Spring Boot 4.0.1**
- **Spring Security** 
- **Spring Data JPA / Hibernate**
- **Argon2** 
- **Lombok**
- **Bean Validation (Jakarta)**
- **PostgreSQL**
- **SpringDoc OpenAPI 2.8.6** 
- **H2** 

---

## Project Structure

```
personalfinanceapi/
├── config/
│   ├── PasswordConfig.java
│   └── SecurityConfig.java
├── controller/
│   ├── auth/
│   │   └── AuthController.java
│   ├── UserController.java
│   ├── CategoryController.java
│   └── TransactionController.java
├── dto/
│   ├── user/
│   │   ├── UserRequestDTO.java
│   │   ├── UserResponseDTO.java
│   │   ├── UserUpdateDTO.java
│   │   └── CreateUserDTO.java
│   ├── category/
│   │   ├── CategoryRequestDTO.java
│   │   └── CategoryResponseDTO.java
│   └── transaction/
│       ├── TransactionRequestDTO.java
│       └── TransactionResponseDTO.java
├── exception/
│   ├── GlobalExceptionHandler.java
│   ├── ErrorResponseDTO.java
│   ├── ResourceNotFoundException.java
│   └── ResourceAlreadyExistsException.java
├── model/
│   ├── entities/
│   │   ├── User.java
│   │   ├── Category.java
│   │   └── Transaction.java
│   └── enums/
│       └── TransactionType.java     # INCOME | EXPENSE
├── repository/
│   ├── UserRepository.java
│   ├── CategoryRepository.java
│   └── TransactionRepository.java
├── security/
│   └── CustomUserDetailsService.java
├── service/
│   ├── UserService.java
│   ├── CategoryService.java
│   ├── TransactionService.java
│   └── PasswordService.java
└── PersonalFinanceApiApplication.java
```

---

## Getting Started

### Prerequisites

- Java 21+
- Maven
- PostgreSQL running locally

### Installation

1. **Clone the repository**

```bash
git clone https://github.com/your-username/personalfinanceapi.git
cd personalfinanceapi
```

2. **Configure your database** in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/personalfinance
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
server.port=8080
```

3. **Build and run**

```bash
./mvnw spring-boot:run
```

The API will be available at `http://localhost:8080`.

---

## Interactive Documentation (Swagger)

Once the application is running, access the full interactive API documentation at:

```
http://localhost:8080/swagger-ui/index.html
```

You can explore and test all endpoints directly in the browser — no external tools needed.

> The Swagger UI and `/v3/api-docs` are public routes and do not require authentication.

---

## Authentication

This API uses **HTTP Basic Authentication**. After registering, include your credentials (email + password) in every request to protected routes.

> **Note:** The `/auth/register` endpoint is **public** — all other endpoints require authentication.

---

## API Reference

### Auth

#### Register a new user

```http
POST /auth/register
```

| Field      | Type     | Constraints                         |
| :--------- | :------- | :---------------------------------- |
| `name`     | `string` | **Required**                        |
| `email`    | `string` | **Required**. Must be a valid email |
| `password` | `string` | **Required**. Minimum 6 characters  |

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

### Users

> All routes require **HTTP Basic Auth**.

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

#### Update a user

```http
PUT /api/users/{id}
```

| Parameter | Type     | Description                    |
| :-------- | :------- | :----------------------------- |
| `id`      | `string` | **Required**. UUID of the user |

| Field      | Type     | Constraints                 |
| :--------- | :------- | :-------------------------- |
| `name`     | `string` | Optional                    |
| `email`    | `string` | Optional                    |
| `password` | `string` | Optional. Min 6 characters  |

**Response** `200 OK`

#### Delete a user

```http
DELETE /api/users/{id}
```

| Parameter | Type     | Description                    |
| :-------- | :------- | :----------------------------- |
| `id`      | `string` | **Required**. UUID of the user |

**Response** `204 No Content`

---

### Categories

> All routes require **HTTP Basic Auth**.

#### Create a category

```http
POST /api/categories/users/{userId}/categories
```

| Parameter | Type     | Description                    |
| :-------- | :------- | :----------------------------- |
| `userId`  | `string` | **Required**. UUID of the user |

| Field  | Type              | Constraints                         |
| :----- | :---------------- | :---------------------------------- |
| `name` | `string`          | **Required**                        |
| `type` | `TransactionType` | **Required**. `INCOME` or `EXPENSE` |

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

#### Get all categories for a user

```http
GET /api/categories/users/{userId}/categories
```

| Parameter | Type     | Description                    |
| :-------- | :------- | :----------------------------- |
| `userId`  | `string` | **Required**. UUID of the user |

**Response** `200 OK`

---

### Transactions

> All routes require **HTTP Basic Auth**.

#### Create a transaction

```http
POST /api/transactions/users/{userId}
```

| Parameter | Type     | Description                    |
| :-------- | :------- | :----------------------------- |
| `userId`  | `string` | **Required**. UUID of the user |

| Field         | Type              | Constraints                                                      |
| :------------ | :---------------- | :--------------------------------------------------------------- |
| `description` | `string`          | **Required**                                                     |
| `amount`      | `number`          | **Required**. Must be greater than `0.01`                        |
| `date`        | `string`          | **Required**. Format: `YYYY-MM-DD`                               |
| `type`        | `TransactionType` | **Required**. `INCOME` or `EXPENSE`                              |
| `categoryId`  | `string`          | **Required**. UUID of an existing category belonging to the user |

**Example:**
```json
{
  "description": "Freelance project payment",
  "amount": 1500.00,
  "date": "2025-03-06",
  "type": "INCOME",
  "categoryId": "uuid"
}
```

**Response** `201 Created`:
```json
{
  "id": "uuid",
  "description": "Freelance project payment",
  "amount": 1500.00,
  "date": "2025-03-06",
  "type": "INCOME",
  "userId": "uuid",
  "categoryId": "uuid",
  "categoryName": "Freelance"
}
```

#### Get all transactions for a user

```http
GET /api/transactions/users/{userId}
```

| Parameter | Type     | Description                    |
| :-------- | :------- | :----------------------------- |
| `userId`  | `string` | **Required**. UUID of the user |

**Response** `200 OK`

---

## Error Handling

All errors return a standardized JSON response:

```json
{
  "status": 404,
  "error": "Not Found",
  "message": "User not found with id: ...",
  "path": "/api/users/...",
  "timestamp": "2025-03-06T10:00:00",
  "fieldErrors": null
}
```

For validation errors (`422 Unprocessable Entity`), the `fieldErrors` array is populated:

```json
{
  "status": 422,
  "error": "Validation Error",
  "message": "One or more fields are invalid",
  "path": "/auth/register",
  "timestamp": "2025-03-06T10:00:00",
  "fieldErrors": [
    { "field": "email", "message": "Invalid email address" },
    { "field": "password", "message": "Password must be at least 6 characters long" }
  ]
}
```

| Status | Meaning                       |
| :----- | :---------------------------- |
| `404`  | Resource not found            |
| `409`  | Conflict / duplicate resource |
| `422`  | Validation error              |
| `500`  | Unexpected internal error     |

---

## Data Models

### User

| Field      | Type     | Notes              |
| :--------- | :------- | :----------------- |
| `id`       | `UUID`   | Auto-generated     |
| `name`     | `string` | Required           |
| `email`    | `string` | Required, unique   |
| `password` | `string` | Hashed with Argon2 |

### Category

| Field  | Type              | Notes                          |
| :----- | :---------------- | :----------------------------- |
| `id`   | `UUID`            | Auto-generated                 |
| `name` | `string`          | Max 100 chars. Unique per user |
| `type` | `TransactionType` | `INCOME` or `EXPENSE`          |
| `user` | `User`            | Many-to-one relationship       |

### Transaction

| Field         | Type              | Notes                            |
| :------------ | :---------------- | :------------------------------- |
| `id`          | `UUID`            | Auto-generated                   |
| `description` | `string`          | Max 255 chars                    |
| `amount`      | `BigDecimal`      | Precision 15, scale 2. Min 0.01  |
| `date`        | `LocalDate`       | Required                         |
| `type`        | `TransactionType` | `INCOME` or `EXPENSE`            |
| `user`        | `User`            | Many-to-one relationship         |
| `category`    | `Category`        | Many-to-one. Must belong to user |

---

## Security Details

- Passwords are hashed using **Argon2** with the following parameters:
  - Salt length: 16 bytes
  - Hash length: 32 bytes
  - Parallelism: 2
  - Memory: 65536 KB
  - Iterations: 3
- CSRF is disabled (stateless REST API)
- Authentication is performed via **email + password** (HTTP Basic)
- Public routes: `/auth/register`, `/swagger-ui/**`, `/v3/api-docs/**`

---

## Roadmap

- [x] User registration and authentication
- [x] Category management (INCOME / EXPENSE)
- [x] Transaction management
- [x] Standardized error handling with `@ControllerAdvice`
- [x] Interactive API documentation with Swagger / OpenAPI
- [ ] Financial reports and summaries (balance, spending by category)
- [ ] JWT-based authentication
- [ ] Docker support

---

## Contributing

Contributions are welcome! Feel free to open an issue or submit a pull request.
