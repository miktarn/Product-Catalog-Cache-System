# Product Catalog Cache System

## Overview
This Spring Boot application provides a RESTful web service for managing a product catalog. It supports basic CRUD operations, caching for improved performance, and uses an H2 in-memory database.

---

## Core Technologies
- **Java**: 23
- **Spring Boot**: 3.4.0
- **H2 Database**: with Flayway migrations
- **Spring Cache**: For caching
- **Maven**: Build tool
- **JUnit 5**: For testing
- **OpenAPI**: For API documentation

---
## Technical Details

1. **Caching**:
   - Caching is configured using Spring Cache.
   - Cache timeout is set to 10 minutes (`caching.spring.products-ttl-ms` in `application.yml`)

   - Cache eviction is triggered on `PUT` and `DELETE` requests.

2. **Flayway migrations**
   - `V1__create_products_table.sql`: Creates the products table to store product information, including fields for name, category, price, and timestamps.
   - `V2__save_example_products.sql`: Inserts sample product data into the products table for testing and development purposes.
   - `V3__create_index_for_products_category.sql`: Adds an index to the category column in the products table to improve query performance.
3. **Validation**:
   - Input validation is implemented using Jakarta Validation annotations.

4. **Global Exception Handler**:
   - Validation Errors: returning a `BAD_REQUEST` status with a detailed list of validation issues and a timestamp.
   - Entity Not Found: returning a `NOT_FOUND` status and an appropriate error message.

5. **Tests**
   - International tests for controller and validation
   - Test coverage 90%
---
## API Endpoints

### Product Endpoints

1. **List all products** (paginated):
    - `GET /api/v1/products`
    - Query Parameters:
        - `count` (default: 20): Number of products per page.
        - `page` (default: 0): Page number.

2. **Get single product**:
    - `GET /api/v1/products/{id}`

3. **Create new product**:
    - `POST /api/v1/products`
    - Request Body:
      ```json
      {
        "name": "Product Name",
        "description": "Product Description",
        "price": 99.99,
        "category": "Category Name",
        "stock": 100
      }
      ```

4. **Update product**:
    - `PUT /api/v1/products/{id}`
    - Request Body: Same as "Create new product".

5. **Delete product**:
    - `DELETE /api/v1/products/{id}`

6. **Get products by category**:
    - `GET /api/v1/products/category/{category}`

---
## Setup Instructions

### Prerequisites
1. **Java 23** installed.
2. **Maven** installed.
3. An IDE or text editor (e.g., IntelliJ IDEA).

### Steps
1. **Clone the repository**:
   ```bash
   git clone <repository-url>
   cd <repository-directory>
   ```

2. **Build the project**:
   ```bash
   mvn clean install
   ```

3. **Run the application**:
   ```bash
   mvn spring-boot:run
   ```
   The application will start on `http://localhost:8080`.

4. **Access the Swagger UI**:
   Navigate to `http://localhost:8080/swagger-ui.html` to explore the API documentation.
