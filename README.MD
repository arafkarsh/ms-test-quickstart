# MS Test QuickStart - Order Microservice

## Framework Versions

- SpringBootTest 2 (2.7.2)
- JUnit 5 (5.7.2)
- Hamcrest 2 (2.2)
- Mockito 3 (3.11.2)
- WireMock 2 ((2.29.1)
- Cucumber 6 (6.10.4)
- Selenium 4 (4.0.0)
- Pact 4 (4.0.10)
- AssertJ 3 (3.20.2)

## Other Tools 
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

## Testing Categories for Microservices

- Unit Testing (JUnit)
- Component Testing (Cucumber + JUnit)
- Behavior &  Contract Testing (Mockito + JUnit)
- Integration & Contract Testing (PACT & WireMock + JUnit)
- UI Testing - User Flow (Selenium with Cucumber + JUnit)

![Microservices Testing Categories](https://raw.githubusercontent.com/arafkarsh/ms-test-quickstart/master/diagrams/Microservices-Testing-Strategies-2.jpg)

## Dependencies

- Spring Boot 2 (2.7.2)
- Maven SureFire Plugin 2 (3.0.0-M7)

## Testing Frameworks

![Microservices Testing Frameworks](https://raw.githubusercontent.com/arafkarsh/ms-test-quickstart/master/diagrams/Microservices-Testing-Tools.jpg)

- SpringBootTest 2 (2.7.2)
- JUnit 5 (5.7.2)
- Hamcrest 2 (2.2)
- Mockito 3 (3.11.2)
- WireMock 2 ((2.29.1)
- Cucumber 6 (6.10.4)
- Selenium 4 (4.0.0)
- Pact 4 (4.0.10)
- AssertJ 3 (3.20.2)

## Other Utility Libraries

- Apache Commons (3.10)
- Google GSON (2.8.7)
- Google Guava (30.1.1-jre)
- Faster XML (2.13.0-rc1)


Copyright (c) 2021-23, Apache 2.0 License, Author: Araf Karsh Hamid

