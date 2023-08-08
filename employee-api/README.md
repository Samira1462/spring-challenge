# Getting Started
## Employee API
Your goal is to implement an application that handles the employees of a company. The
application must expose a REST API, so that other services can communicate with it
easily.

## Steps
The application must be able to:
1. Create an employee with the following properties
   ○ Uuid (generated automatically)
   ○ E-mail
   ○ Full name (first and last name)
   ○ Birthday (format YYYY-MM-DD)
   ○ List of hobbies (for example, "soccer", "music", etc)
2. Get a list of all employees (response in JSON Array format)
3. Get a specific employee by uuid (response in JSON Object format)
4. Update an employee
5. Delete an employee
6. Whenever an employee is created, updated or deleted, an event related to this
   action must be pushed in some message broker (i.e, Kafka, RabbitMq, etc).
## Restriction
The email field is unique, i.e. 2 employees cannot have the same email

## Requirements
* Java 17
* Spring boot 3
* gradle 7


docker-compose down && docker-compose build --no-cache && docker-compose up

