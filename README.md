# Spring Boot Project README

## Project Introduction

This project is a back-end application built with Spring Boot, designed to support a front-end React application for a car club. The application provides RESTful APIs for handling user registrations, managing car collections, and performing administrative tasks. Users can register, view their car collections, and interact with various features, while administrators can manage users, moderate content, and access system metrics through an admin panel.

Please follow the steps below to install and run the project.

## Environment Requirements

- **Java**: This project requires **Java 17**.
- **Maven**: Use **Maven 3.6.x** or above for managing dependencies.
- **MySQL**: The project requires **MySQL 8** with a database named `rcca`.

## Installation Steps

### 1. Clone the Project

First, clone the project code to your local machine using Git:

```sh
$ git clone https://github.com/WeihaoYYY/RCCA2.git
$ cd <project-directory>
```

### 2. Configure Database

Ensure you have a running MySQL server and create a new database named `rcca`. Update the `application.properties` file with your database connection details:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/rcca
spring.datasource.username=<your_username>
spring.datasource.password=<your_password>
```

The project also includes an `insert.sql` file with sample data. Make sure to import this data into your MySQL database to get started.

### 3. Install Dependencies

Run the following command to install the required dependencies and build the project:

```sh
$ mvn clean install
```
This command will compile the code, run the tests, and package the application.

## Run the Project

### 1. Running the Application Locally

To run the application locally, you need to set the active profile to `local`. You can do this in your Spring Boot application's configuration or directly in your IDE's run configuration.

Or use the following command to start the application:

```sh
$ mvn spring-boot:run -Dspring-boot.run.profiles=local
```
The application will start on port `8080`. You can access the API endpoints via `http://localhost:8080`.

### 2. Running with Docker

To run the application in a Docker container, use the following command:

```sh
$ docker run -d -p 8081:8081 --name r2 -e RDS_USERNAME=admin -e RDS_PASSWORD=??? weihaodev/whdev:r2
```
This command will run the Docker container with the appropriate environment variables for connecting to the online database.

## Notes

- **Java Version Requirement**: Please make sure your Java version is **17** to ensure compatibility.
- **Database Connection**: Ensure that your MySQL server is running and accessible with the credentials provided in the configuration file.
- **Profiles**: The project uses different `application.properties` files for different environments. When building for production, the database endpoint will automatically switch to the online database.

## API Endpoints

- **Swagger**: http://localhost:8081/swagger-ui/index.html#
- 
## FAQ

- If you encounter issues with dependency installation, try cleaning the project and reinstalling dependencies:
  ```sh
  $ mvn clean install
  ```
- If the application fails to start, check the MySQL server status and ensure the database credentials are correct in the configuration file.

## Contact

If you have any questions, please contact the author at **[yue032994@outlook.com](mailto:yue032994@outlook.com)**.

