# 🏦 Bank Management Microservices System

A production-style microservice-based bank management system built with Java, Spring Boot, and Spring Cloud.

## 🏗️ Architecture

API Gateway (Port 8080)
│
┌─────┼──────────────────────┐
│     │                      │
User  Account              Transaction
Service Service             Service
(8081) (8082)               (8083)
│
Eureka Server
(Port 8761)


## ⚙️ Tech Stack

- **Java 21**
- **Spring Boot 3.4.1**
- **Spring Cloud 2024.0.0**
- **Spring Cloud Netflix Eureka** — Service Discovery
- **Spring Cloud Gateway** — API Gateway
- **Spring Cloud OpenFeign** — Inter-service Communication
- **JWT (jjwt)** — Authentication
- **Spring Data JPA** — ORM
- **H2 Database** — In-memory Database
- **Maven** — Build Tool

## 🧩 Microservices

| Service | Port | Responsibility |
|---|---|---|
| Eureka Server | 8761 | Service registry and discovery |
| API Gateway | 8080 | Single entry point, routing |
| User Service | 8081 | User registration, login, JWT |
| Account Service | 8082 | Account creation, balance management |
| Transaction Service | 8083 | Deposit, withdrawal, transfer |

## 🚀 How to Run

### Prerequisites
- Java 21
- Maven

### Start in this order:

**1. Eureka Server**
```bash
cd eureka-server/Bank_management
mvn spring-boot:run
```

**2. API Gateway**
```bash
cd api-gateway/api-gateway
mvn spring-boot:run
```

**3. User Service**
```bash
cd user-service/user-service
mvn spring-boot:run
```

**4. Account Service**
```bash
cd account-service/account-service
mvn spring-boot:run
```

**5. Transaction Service**
```bash
cd transaction-service/transaction-service
mvn spring-boot:run
```

## 📡 API Endpoints

### User Service
| Method | URL | Description |
|---|---|---|
| POST | `/api/users/register` | Register new user |
| POST | `/api/users/login` | Login and get JWT token |
| GET | `/api/users/{username}` | Get user details |

### Account Service
| Method | URL | Description |
|---|---|---|
| POST | `/api/accounts/create` | Create bank account |
| GET | `/api/accounts/{accountNumber}` | Get account details |
| GET | `/api/accounts/user/{userId}` | Get all accounts of user |
| POST | `/api/accounts/deposit` | Deposit money |
| POST | `/api/accounts/withdraw` | Withdraw money |
| PUT | `/api/accounts/close/{accountNumber}` | Close account |

### Transaction Service
| Method | URL | Description |
|---|---|---|
| POST | `/api/transactions/deposit` | Deposit money |
| POST | `/api/transactions/withdraw` | Withdraw money |
| POST | `/api/transactions/transfer` | Transfer between accounts |
| GET | `/api/transactions/history/{accountNumber}` | Transaction history |
| GET | `/api/transactions/all` | All transactions |

## 🔍 Monitoring

- **Eureka Dashboard** → http://localhost:8761
- **H2 Console (User)** → http://localhost:8081/h2-console
- **H2 Console (Account)** → http://localhost:8082/h2-console
- **H2 Console (Transaction)** → http://localhost:8083/h2-console

## 🔮 Future Improvements

- Password encryption using BCrypt
- MySQL for production database
- Docker containerization
- JWT validation filter in API Gateway
- Circuit breaker using Resilience4j