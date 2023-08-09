# Employee Management Application

Welcome to the Employee Management Application, a REST API-based solution for efficiently handling employee records within your company. This application exposes endpoints that allow you to manage employees, including creating, retrieving, updating, and deleting records. It also integrates with a message broker to ensure event notifications for relevant actions.

## Table of Contents

- Requirements
- Technologies Used
- Installation and Setup

## Requirements

The application meets the following criteria:

1. **Create Employee:** Create new employee profiles with automatic UUID generation, email, full name, birthday, and hobbies.

2. **Get All Employees:** Retrieve a list of all employees in JSON array format.

3. **Get Employee by Uuid:** Fetch a specific employee's details in JSON object format.

4. **Update Employee:** Modify employee information.

5. **Delete Employee:** Remove an employee record.

6. **Event Notification:** Emit events to a message broker Kafka upon employee creation, update, or deletion.

## Technologies Used

- Programming Language: Java 17
- Web Framework: Spring Boot 3 
- Database: MySQL
- Message Broker: Kafka
- API Documentation: Swagger
- Build tools: gradle 7 
- Docker

## Installation and Setup

1. Clone the repository:
   ```sh
   git clone https://github.com/Samira1462/spring-challenge/tree/main/employee-api
   

2. Install dependencies:
install docker base on O.S. 

3. Build and run the application:
   ```sh 
   docker-compose down && docker-compose build --no-cache && docker-compose up

4. Go to the root of the application where build.gradle and Run execute the below command
   
   ```sh  
   ./gradlew bootRun
5. Access the Swagger API documentation:
   http://localhost:8080/swagger-ui/index.html#/employee-controller/update

