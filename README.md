# Full Stack Blog Application

This is a full-stack blog application built with:

- Frontend: React 
- Backend:Spring Boot 
- Database:MySQL

### How to Run the Application

### Prerequisites

Make sure you have these installed on your system:

- Node.js & npm
- Java 17+ (for Spring Boot)
- Maven
- MySQL

---

## Running the Backend (Spring Boot)

1. Create MySQL database

Open MySQL and create a database (e.g., `blog_app`):

```sql
CREATE DATABASE blog_app;

2.Configure database credentials
spring.datasource.url=jdbc:mysql://localhost:3306/blog_app
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD


3.Run the backend:
In the terminal:
cd blog api
mvn spring-boot:run

# SpringBoot will start on :http://localhost:8080


### Running the Frontend

1.Open a new terminal window

2.Navigate to the frontend directory:
cd blogapp

3.Install dependencies:
npm install

4.Start the React development server:
npm start

## React will start on: http://localhost:3000
