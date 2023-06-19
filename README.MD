# MS Test QuickStart - Order Microservice

## Testing Categories for Microservices

|     | Testing Category             | Tools                          |
|-----|------------------------------|--------------------------------|
| 1   | Unit Testing                 | Junit, Spock, TestNG           |
| 2   | Component Testing            | Cucumber, RestAssured, Mockito |
| 3   | Contract Testing             | Cucumber, RestAssured, Mockito |
| 4   | Integration Testing          | WireMock                       |
| 5   | Integration Contract Testing | Mockito, WireMock, Pact        |
| 6   | UI Testing / E2E Testing     | Selenium + Cucumber            |

![Microservices Testing Categories](https://raw.githubusercontent.com/arafkarsh/ms-test-quickstart/master/diagrams/Microservices-Testing-Strategies-2.jpg)

## Spring Framework Versions

- SpringBoot 2 (2.7.2)

### 1. Junit (5.7.2) - 105 Test Cases

| 1   | Testing Framework | Version | Test Cases |
|-----|-------------------|---------|------------|
| 1.1 | JUnit 5           | 5.7.2   | 42         |
| 1.2 | SpringBootTest 2  | 2.7.2   | 01         |
| 1.3 | RestAssured 5     | 5.3.0   | 29         |
| 1.4 | Mockito 3         | 3.11.2  | 18         |
| 1.5 | WireMock 2        | 2.29.1  | 07         |
| 1.6 | Cucumber 6        | 6.10.4  | 03         |
| 1.7 | Selenium 4        | 4.0.0   | 02         |
| 1.8 | Pact 4            | 4.0.10  | 03         |

### 2. Spring Spock (2.4.0) - 45 Test Cases

| 2   | Testing Framework | Version | Test Cases |
|-----|-------------------|---------|------------|
| 2.1 | Spock 2           | 2.4.0   | 21         |
| 2.2 | SpringBootTest 2  | 2.7.2   | 02         |
| 2.3 | RestAssured 5     | 5.3.0   | 08         |
| 2.4 | Mockito 3         | 3.11.2  | 02         |
| 2.5 | WireMock 2        | 2.29.1  | 07         |
| 2.6 | Cucumber 6        | 6.10.4  | 03         |
| 2.7 | Selenium 4        | 4.0.0   | 02         |
| 2.8 | Pact 4            | 4.0.10  | 00         |


### 3. TestNG (7.4.0) - 35 Test Cases

| 3   | Testing Framework | Version | Test Cases |
|-----|-------------------|---------|------------|
| 3.1 | TestNG 7          | 7.4.0   | 18         |
| 3.2 | SpringBootTest 2  | 2.7.2   | 02         |
| 3.3 | RestAssured 5     | 5.3.0   | 05         |
| 3.4 | Mockito 3         | 3.11.2  | 02         |
| 3.5 | WireMock 2        | 2.29.1  | 03         |
| 3.6 | Cucumber 6        | 6.10.4  | 03         |
| 3.7 | Selenium 4        | 4.0.0   | 02         |
| 3.8 | Pact              | NA      | No Support |


### 4. Support Libraries

| 4   | Testing Framework | Version | Test Cases |
|-----|-------------------|---------|------------|
| 4.1 | Hamcrest 2        | 2.2.0   | -          |
| 4.2 | AssertJ 3         | 3.20.2  | -          |

### 5. Other Utilities

| 5   | Other Libraries | Version     |
|-----|-----------------|-------------|
| 5.1 | Apache Commons  | 3.10.0      | 
| 5.2 | Google GSON     | 2.8.7       |
| 5.3 | Google Guava    | 30.1.1-jre  |
| 5.4 | Faster XML      | 2.13.0-rc1  |


## 6. Other Tools 
- Open API (1.5.10)

Order Microservice is part of an eCommerce Application. The objective of selecting this Microservice is to demonstrate various testing frameworks. As Order is a complex service with internal (Ex. Inventory, Warhouse, Shipping etc) and external (Payment) service integration and it has all the requirements to use various testing frameworks. The objective is to demonstrate various testing frameworks. 

Order Service REST Endpoints are documented using Open API v3. 
You can access the API's using http://localhost:9080/swagger-ui.html

![Order Service API based on Open API v3](https://raw.githubusercontent.com/arafkarsh/ms-test-quickstart/master/diagrams/Order-OpenAPI-Order.jpg)

![Order Service API based on Open API v3](https://raw.githubusercontent.com/arafkarsh/ms-test-quickstart/master/diagrams/Order-OpenAPI-Core.jpg)

Other Services

1. Payment External Service
2. Shipping Service
3. Inventory Service
4. Warehouse Service

Currently Order Service has a Mock Implementation of these services.

Now let us focus on Microservices Testing Strategies.

![Microservice Testing Strategies](https://raw.githubusercontent.com/arafkarsh/ms-test-quickstart/master/diagrams/Microservices-Testing-Strategies-1.jpg)

The following Test Categories helps you to automate the testing for Microservices based development resulting in ZERO End-2-End Testing.
All the tests will be fully automated as part of your CI/CD pipeline.

## Dependencies

- Spring Boot 2 (2.7.2)
- Maven SureFire Plugin 2 (3.0.0-M7)

## Testing Frameworks

![Microservices Testing Frameworks](https://raw.githubusercontent.com/arafkarsh/ms-test-quickstart/master/diagrams/Microservices-Testing-Tools.jpg)


Copyright (c) 2021-23, Apache 2.0 License, Author: Araf Karsh Hamid

