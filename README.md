# Coffee Shop Application

This repository contains a Coffee Shop application built using hexagonal architecture principles with Spring Boot and Maven.

## Architecture

The application is structured using hexagonal architecture, also known as ports and adapters architecture. Here's a breakdown of the architectural components:

### Core Domain (Application)

The `coffeeshop-application` module contains the core domain logic of the Coffee Shop application. It consists of:

- **Use Cases**: Represented by `OrderingCoffee` and `PreparingCoffee` interfaces, defining business operations.
- **Entities and Value Objects**: Domain objects such as `Order`, `LineItem`, and enums like `Drink`, `Milk`, `Size`.
- **Business Logic**: Services like `CoffeeShop` and `CoffeeMachine` implementing use cases.

### Primary Adapters (Inbound)

The primary adapters facilitate interaction with external systems or users:

- **REST API**: Implemented with Spring `@RestController` in `OrderController`, handles incoming HTTP requests.
- **Mapping**: Converts DTOs (Data Transfer Objects) to domain entities and vice versa (`OrderRequest`, `OrderResponse`).

### Secondary Adapters (Outbound)

The secondary adapters provide implementations for secondary ports such as persistence:

- **JPA Persistence**: `OrdersJpaAdapter` stores orders in a database using JPA repositories (`OrderJpaRepository`, `OrderEntity`).

### Configuration

- **Spring Boot Configuration**: Spring Boot setup (`@SpringBootApplication`, `@Configuration`, etc.) in `coffeeshop-infrastructure`.
- **Transactional Support**: Managed using AOP (`@Aspect` and `@TransactionalUseCaseAspect`) to ensure transactions for use cases.

## Test Cases

### Unit Tests

Unit tests validate individual components in isolation:

- **Domain Logic**: Tests for business logic in `CoffeeShop`, `CoffeeMachine`, `Order`, `LineItem`, etc.
- **Use Cases**: Tests for `OrderingCoffee`, `PreparingCoffee`, ensuring correct use case execution.

### Integration Tests

Integration tests verify interactions between components:

- **Primary Adapters**: Tests REST endpoints (`OrderControllerTest`) with mocked MVC environment (`@WebMvcTest`).
- **Secondary Adapters**: Tests persistence (`OrdersJpaAdapterTest`) using an embedded database (`@DataJpaTest`).

### End-to-End Tests

End-to-end tests validate the application's entire flow:

- **CoffeeShopApplicationTests**: Tests complete order processing flow from placing an order to taking it, using `MockMvc` and mocked `CoffeeMachine`.

### Running Tests

To run all tests, execute:

```bash
mvn test
```



## Key Topics Covered

### 1. Hexagonal Architecture (Ports and Adapters)

Explore how Hexagonal Architecture is implemented:

- **Core Domain**: Structuring domain logic (`Order`, `CoffeeMachine`).
- **Primary Adapters**: Implementing REST API endpoints (`OrderController`).
- **Secondary Adapters**: Integrating JPA for persistence (`OrdersJpaAdapter`).

### 2. Testing Strategies

Learn comprehensive testing techniques:

- **Unit Tests**: Validate business logic and use cases in isolation.
- **Integration Tests**: Test primary and secondary adapters with mocks and embedded databases.
- **End-to-End Tests**: Validate complete application flows using `MockMvc`.

### 3. Transactional Behavior

Implement transaction management using AOP:

- **Transactional Use Cases**: Ensure data integrity for business operations._
