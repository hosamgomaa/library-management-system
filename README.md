# Library Management System
  This is a Spring Boot application designed to manage a library's collection of books and patrons.
  The application provides a RESTful API to perform CRUD operations on books, patrons, and borrowing records.
  It also includes features like Pagination, Caching, Unit Testing, Logging using AOP and Secuirty using JWT.


## Requirements

For building and running the application you need:

 - Java 17 or higher
 - Maven 3.6+
 - MS SQL SERVER

## Running the application locally

Configure the database, modify the application.yml

    datasource:
    url: jdbc:sqlserver://localhost:1433;databaseName=your_db_name
    username: your-db-username
    password: your-db-password
Provide credineals for authentication

    seeded-user:
    username : your-username
    password :  your-password

 Build the Application
  
    mvn clean install

Run the Application
  
    mvn spring-boot:run
  

## Access the Application
The Application is running on http://localhost:8080

### API Endpoints


#### Authentication  Endpoint

- **POST /api/auth/login:** Return JWT for Authenticated User



#### Book Management Endpoints

- **GET /api/books:** Retrieve a list of all books.
- **GET /api/books/{id}:** Retrieve details of a specific book by ID.
- **POST /api/books:** Add a new book to the library.
- **PUT /api/books/{id}:** Update an existing book's information.
- **DELETE /api/books/{id}:** Remove a book from the library.


#### Patron Management Endpoints

- **GET /api/patrons:** Retrieve a list of all patrons.
- **GET /api/patrons/{id}:** Retrieve details of a specific patron by ID.
- **POST /api/patrons:** Add a new patron to the system.
- **PUT /api/patrons/{id}:** Update an existing patron's information.
- **DELETE /api/patrons/{id}:** Remove a patron from the system.

### Borrowing Endpoints

- **POST /api/borrow/{bookId}/patron/{patronId}:** Allow a patron to borrow a book.
- **PUT /api/return/{bookId}/patron/{patronId}:** Record the return date of a borrowed book by a patron.
