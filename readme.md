# Clustered Data Warehouse API Documentation 

## Description
This API Allows you to persist transfer deals for auditing.

## Development Base URL
http://localhost:8080/api/v1


## Endpoints

### Create a Deal
- **URL:** `/deals`
- **Method:** `POST`
- **Request Body:**
  ```json
  {
    "fromCurrencyISO": "USD",
    "toCurrencyISO": "EUR",
    "timestamp": "2024-03-30T12:00:00",
    "amount": 1000.0
  }
- **Response Body:**
  ```json
  {
    "id": "generatedId",
    "fromCurrencyISO": "USD",
    "toCurrencyISO": "EUR",
    "timestamp": "2024-03-30T12:00:00",
    "amount": 1000.0
  }

- **Response Codes:**
    - 200 OK: Deal created successfully
    - 400 Bad Request:
        - Duplicate Deal: A deal with the same ID already exists
        - Missing Fields: Request body is missing required fields
