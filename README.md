# backend-developer-as-final-12165-adesh
Final Project Assignment - This repository contains the complete final project code and documentation.

# Resource Booking System

## Backend Developer Assignment

A secure RESTful Resource Booking System built using **Spring Boot**, **Java 17**, **Spring Security**, **JWT Authentication**, and **MySQL**.

The application allows users to view available resources and create reservations, while administrators can manage resources and reservations.

---

# Features

## Authentication & Authorization

* JWT-based Authentication
* BCrypt Password Encryption
* Spring Security
* Role-Based Access Control (RBAC)
* Stateless Authentication

### Roles

* **ADMIN**
* **USER**

---

# User Permissions

* Login
* View all available resources
* Create reservations
* View only their own reservations

---

# Admin Permissions

* Login
* Create Resources
* Update Resources
* Delete Resources
* View All Resources
* View All Reservations
* Update Reservation Status
* Delete Reservations

---

# Reservation Features

* Create Reservation
* Reservation Status

  * PENDING
  * CONFIRMED
  * CANCELLED
* Price stored as Decimal (BigDecimal)
* Reservation ownership using JWT
* Filtering by Status
* Filtering by Minimum Price
* Filtering by Maximum Price
* Pagination
* Sorting

---

# Technologies Used

* Java 17
* Spring Boot 3.x
* Spring Security
* Spring Data JPA
* Hibernate
* MySQL
* JWT (JJWT)
* Maven
* Lombok
* Swagger / OpenAPI

---

# Project Structure

```
src/main/java
└── com
    └── ResourceSystem
        ├── config
        ├── controller
        ├── dto
        ├── entity
        ├── repository
        ├── security
        ├── service
        ├── service/impl
        ├── exception
        ├── specification
        ├── util
        └── ResourceBookingApplication.java
```

---

# Database Setup

Create a MySQL database:

```sql
CREATE DATABASE resource_booking;
```

---

# Application Configuration

Update `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/resource_booking
spring.datasource.username=root
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

server.port=8080

jwt.secret=your_secret_key_here
jwt.expiration=86400000
```

---

# Default Test Users

## ADMIN

Email:

```
admin@gmail.com
```

Password:

```
admin123
```

Role:

```
ADMIN
```

---

## USER

Email:

```
user@gmail.com
```

Password:

```
user123
```

Role:

```
USER
```

---

# Running the Project

### Clone Repository

```bash
git clone <repository-url>
```

### Navigate to Project

```bash
cd resource-booking-system
```

### Build Project

```bash
mvn clean install
```

### Run Project

```bash
mvn spring-boot:run
```

Application starts at:

```
http://localhost:8080
```

---

# Authentication

## Login

```
POST /auth/login
```

### Request

```json
{
  "email": "admin@gmail.com",
  "password": "admin123"
}
```

### Response

```json
{
  "token": "JWT_TOKEN",
  "type": "Bearer",
  "email": "admin@gmail.com",
  "role": "ADMIN"
}
```

Use the JWT token in the Authorization header:

```
Authorization: Bearer <JWT_TOKEN>
```

---

# Resource APIs

## Get All Resources

```
GET /resources
```

Access:

* ADMIN
* USER

---

## Get Resource By Id

```
GET /resources/{id}
```

Access:

* ADMIN
* USER

---

## Create Resource

```
POST /resources
```

Access:

* ADMIN

---

## Update Resource

```
PUT /resources/{id}
```

Access:

* ADMIN

---

## Delete Resource

```
DELETE /resources/{id}
```

Access:

* ADMIN

---

# Reservation APIs

## Create Reservation

```
POST /reservations
```

Access:

* USER
* ADMIN

Example Request:

```json
{
  "resourceId": 1,
  "startTime": "2026-09-10T10:00:00",
  "endTime": "2026-09-10T12:00:00"
}
```

> **Note:** The logged-in user is obtained from the JWT token. The client must not send a `userId`.

---

## Get My Reservations

```
GET /reservations/my
```

Access:

* USER

---

## Get All Reservations

```
GET /reservations
```

Access:

* ADMIN

Supports filtering:

* status
* minPrice
* maxPrice

Supports pagination:

```
?page=0&size=10
```

Supports sorting:

```
?sort=price,asc
```

---

## Update Reservation Status

```
PUT /reservations/{id}/status
```

Example:

```json
{
  "status": "CONFIRMED"
}
```

Access:

* ADMIN

---

## Delete Reservation

```
DELETE /reservations/{id}
```

Access:

* ADMIN

---

# Swagger API Documentation

After starting the application, open:

```
http://localhost:8080/swagger-ui/index.html
```

OpenAPI JSON:

```
http://localhost:8080/v3/api-docs
```

---

# Error Handling

The application handles:

* Resource Not Found (404)
* Unauthorized Access (401)
* Forbidden Access (403)
* Bad Request (400)
* Validation Errors
* Internal Server Error (500)

Example Response:

```json
{
  "timestamp": "2026-09-07T10:20:30",
  "status": 404,
  "message": "Resource not found"
}
```

---

# Security

Implemented using:

* Spring Security
* JWT Authentication
* BCrypt Password Encoding
* Stateless Sessions

Authentication Flow:

```
Client
   │
POST /auth/login
   │
AuthenticationManager
   │
JWT Generated
   │
Client Stores Token
   │
Authorization: Bearer <TOKEN>
   │
JWT Filter
   │
Security Context
   │
Protected API
```

---

# Testing

The project includes tests for:

* Authentication
* Authorization
* Resource CRUD
* Reservation CRUD
* Security
* Service Layer

---

# Future Improvements

* Email Notifications
* Docker Support
* Redis Caching
* Calendar Integration
* Payment Gateway

---

# Author

**Adesh Valekar**

Backend Developer Assignment – Resource Booking System
