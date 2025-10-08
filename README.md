# Spring Boot Testcontainers Demo

A comprehensive Spring Boot application demonstrating the integration of Docker Compose support with PostgreSQL, Apache
Kafka, and Kafka UI for development and testing purposes.

## 📋 Table of Contents

- [Technologies](#technologies)
- [Prerequisites](#prerequisites)
- [Architecture](#architecture)
- [Getting Started](#getting-started)
- [Docker Services](#docker-services)
- [API Endpoints](#api-endpoints)
- [Testing with cURL](#testing-with-curl)
- [Project Structure](#project-structure)
- [Configuration](#configuration)

## 🛠 Technologies

- **Java 21** - Programming language
- **Spring Boot 3.5.6** - Application framework
    - Spring Data JPA
    - Spring Kafka
    - Spring Data Redis
    - Spring Boot Docker Compose Support
- **PostgreSQL 17.5 (Alpine 3.22)** - Primary database
- **Apache Kafka** - Message broker (KRaft mode)
- **Kafka UI** - Web-based Kafka management tool
- **Gradle 9.0** - Build tool
- **Testcontainers** - Integration testing framework
- **Lombok** - Code generation
- **Docker & Docker Compose** - Containerization

## 📦 Prerequisites

- **Java 21** or higher
- **Docker** and **Docker Compose**
- **Gradle** (or use the included Gradle wrapper)
- **Git**

## 🏗 Architecture

This application uses Docker Compose to orchestrate the following services:

```
┌─────────────────────────────────────────────────────────┐
│                 Spring Boot Application                  │
│                   (Port: 8181)                          │
└─────────────────────────────────────────────────────────┘
           │                 │                │
           │                 │                │
           ▼                 ▼                ▼
   ┌──────────────┐  ┌──────────────┐  ┌──────────────┐
   │  PostgreSQL  │  │    Kafka     │  │  Kafka UI    │
   │  (Port 5433) │  │  (Port 9092) │  │  (Port 8080) │
   └──────────────┘  └──────────────┘  └──────────────┘
```

## 🚀 Getting Started

### 1. Clone the Repository

```bash
git clone <repository-url>
cd springboot-testcontainers
```

### 2. Start Docker Services

```bash
docker-compose -f compose.yaml up -d
```

This will start:

- PostgreSQL 17.5 on port **5433**
- Apache Kafka on port **9092**
- Kafka UI on port **8080**

### 3. Verify Services are Running

```bash
docker ps
```

You should see three containers: `postgres`, `kafka`, and `kafka-ui`.

### 4. Run the Application

```bash
./gradlew bootRun
```

The application will start on **http://localhost:8181**

## 🐳 Docker Services

### PostgreSQL Database

- **Image**: `postgres:17.5-alpine3.22`
- **Port**: `5433:5432`
- **Username**: `yu71`
- **Password**: `53cret`
- **Database**: `testcontainers`
- **Connection String**: `jdbc:postgresql://localhost:5433/testcontainers`

#### Test Database Connection

```bash
docker exec postgres psql -U yu71 -d testcontainers -c "SELECT version();"
```

### Apache Kafka

- **Image**: `confluentinc/cp-kafka:latest`
- **Port**: `9092:9092`
- **Mode**: KRaft (no Zookeeper required)
- **Bootstrap Server**: `localhost:9092`

#### Test Kafka Connection

```bash
docker exec kafka kafka-topics --bootstrap-server localhost:9092 --list
```

### Kafka UI

- **Image**: `provectuslabs/kafka-ui:latest`
- **Port**: `8080:8080`
- **Access**: http://localhost:8080

Use Kafka UI to:

- View and manage Kafka topics
- Monitor consumer groups
- Send and receive messages
- View broker configuration

## 📡 API Endpoints

### Customer Management

| Method | Endpoint            | Description                         |
|--------|---------------------|-------------------------------------|
| GET    | `/customers`        | Get all customers from database     |
| GET    | `/customers-ext`    | Get customers from external service |
| GET    | `/customers/{name}` | Get customers by name               |
| POST   | `/customers`        | Create a new customer (async)       |

## 🧪 Testing with cURL

### 1. Create a New Customer

```bash
curl -X POST http://localhost:8181/customers \
  -H "Content-Type: application/json" \
  -d '{
    "id": 1,
    "name": "John Doe"
  }'
```

**Expected Response**: HTTP 200 (no body - async operation)

### 2. Create Multiple Customers

```bash
# Customer 2
curl -X POST http://localhost:8181/customers \
  -H "Content-Type: application/json" \
  -d '{"id": 2, "name": "Jane Smith"}'

# Customer 3
curl -X POST http://localhost:8181/customers \
  -H "Content-Type: application/json" \
  -d '{"id": 3, "name": "Bob Johnson"}'

# Customer 4
curl -X POST http://localhost:8181/customers \
  -H "Content-Type: application/json" \
  -d '{"id": 4, "name": "Alice Williams"}'
```

### 3. Get All Customers

```bash
curl http://localhost:8181/customers
```

**Expected Response**:

```json
[
  {"id": 1, "name": "John Doe"},
  {"id": 2, "name": "Jane Smith"},
  {"id": 3, "name": "Bob Johnson"},
  {"id": 4, "name": "Alice Williams"}
]
```

### 4. Get Customers by Name

```bash
curl http://localhost:8181/customers/John
```

**Expected Response**:

```json
[
  {"id": 1, "name": "John Doe"}
]
```

### 5. Get External Customers

```bash
curl http://localhost:8181/customers-ext
```

This endpoint fetches customers from an external service (configured via `EXTERNAL_CUSTOMER_SERVICE_HOST` and
`EXTERNAL_CUSTOMER_SERVICE_PORT`).

### 6. Pretty Print JSON Response

```bash
curl http://localhost:8181/customers | json_pp
# or use jq if installed
curl http://localhost:8181/customers | jq
```

## 📁 Project Structure

```
springboot-testcontainers/
├── src/
│   ├── main/
│   │   ├── java/id/my/hendisantika/testcontainers/
│   │   │   ├── controller/
│   │   │   │   └── CustomerHttpController.java
│   │   │   ├── dto/
│   │   │   │   └── CustomerDTO.java
│   │   │   ├── entity/
│   │   │   │   └── Customer.java
│   │   │   ├── repository/
│   │   │   ├── service/
│   │   │   └── SpringbootTestcontainersApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── schema.sql
│   │       └── data.sql
│   └── test/
├── compose.yaml
├── build.gradle
└── README.md
```

## ⚙️ Configuration

### Application Properties

Key configuration in `application.properties`:

```properties
# Server Configuration
server.port=8181
# Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5433/testcontainers
spring.datasource.username=yu71
spring.datasource.password=53cret
# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
# Kafka Configuration
spring.kafka.bootstrap-servers=localhost:9092
spring.kafka.consumer.group-id=tc
```

### Docker Compose Configuration

The `compose.yaml` file defines all service configurations:

- **PostgreSQL**: Custom credentials and port mapping
- **Kafka**: KRaft mode with proper listeners configuration
- **Kafka UI**: Connected to the Kafka cluster
- **Health Checks**: All services have health checks configured
- **Volumes**: Persistent storage for PostgreSQL data

## 🔧 Useful Commands

### Docker Compose

```bash
# Start all services
docker-compose -f compose.yaml up -d

# Stop all services
docker-compose -f compose.yaml down

# Stop and remove volumes
docker-compose -f compose.yaml down -v

# View logs
docker-compose -f compose.yaml logs -f

# View logs for specific service
docker-compose -f compose.yaml logs -f postgres
docker-compose -f compose.yaml logs -f kafka
docker-compose -f compose.yaml logs -f kafka-ui
```

### Gradle

```bash
# Build the project
./gradlew build

# Run tests
./gradlew test

# Run the application
./gradlew bootRun

# Clean build
./gradlew clean build
```

### PostgreSQL

```bash
# Connect to PostgreSQL
docker exec -it postgres psql -U yu71 -d testcontainers

# List all tables
docker exec postgres psql -U yu71 -d testcontainers -c "\dt"

# Query customers
docker exec postgres psql -U yu71 -d testcontainers -c "SELECT * FROM customer;"
```

### Kafka

```bash
# List topics
docker exec kafka kafka-topics --bootstrap-server localhost:9092 --list

# Create a topic
docker exec kafka kafka-topics --bootstrap-server localhost:9092 --create --topic test-topic --partitions 1 --replication-factor 1

# Describe a topic
docker exec kafka kafka-topics --bootstrap-server localhost:9092 --describe --topic test-topic

# Delete a topic
docker exec kafka kafka-topics --bootstrap-server localhost:9092 --delete --topic test-topic
```

## 🧪 Running Tests

```bash
# Run all tests
./gradlew test

# Run tests with coverage
./gradlew test jacocoTestReport

# Run integration tests only
./gradlew integrationTest
```

## 🐛 Troubleshooting

### Port Already in Use

If you encounter port conflicts:

```bash
# Check what's using port 5433
lsof -i :5433

# Check what's using port 9092
lsof -i :9092

# Check what's using port 8080
lsof -i :8080
```

### Database Connection Issues

1. Verify PostgreSQL container is running and healthy:

```bash
docker ps | grep postgres
```

2. Check PostgreSQL logs:

```bash
docker logs postgres
```

3. Test direct connection:

```bash
docker exec postgres psql -U yu71 -d testcontainers -c "SELECT 1;"
```

### Kafka Connection Issues

1. Verify Kafka container is healthy:

```bash
docker ps | grep kafka
```

2. Check Kafka logs:

```bash
docker logs kafka
```

3. Verify broker is accessible:

```bash
docker exec kafka kafka-broker-api-versions --bootstrap-server localhost:9092
```

## 📝 License

This project is created by Hendi Santika for demonstration purposes.

## 👤 Author

- **Name**: Hendi Santika
- **Email**: hendisantika@gmail.com
- **Telegram**: @hendisantika34

## 🤝 Contributing

Contributions, issues, and feature requests are welcome!

## ⭐ Show Your Support

Give a ⭐️ if this project helped you!
