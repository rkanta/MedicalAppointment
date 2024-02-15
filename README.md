# Medical Appointment App API Documentation

## Appointment API

This document provides the API documentation for the Medical Appointment Management System. It includes endpoint descriptions, request/response formats, and usage examples.

### Endpoints

#### 1. Schedule Appointment

Creates a new appointment with the provider in available slots.

- **Method**: POST
- **URL**: `/api/appointments`
- **Content-Type**: `application/json`
- **Request Body**:
    ```json
    {
        "patientId": "string",
        "providerId": "string",
        "dateTime": "YYYY-MM-DDTHH:MM:SS",
        "status": "Scheduled"
    }
    ```
- **Response**:
    ```json
    {
        "id": "long",
        "patientId": "string",
        "providerId": "string",
        "dateTime": "YYYY-MM-DDTHH:MM:SS",
        "status": "Scheduled"
    }
    ```
- **Example**:
    ```bash
    curl -X POST -H "Content-Type: application/json" -d '{"patientId": "123456", "providerId": "789012", "dateTime": "2023-12-31T10:00:00", "status": "Scheduled"}' http://localhost:8080/api/appointments
    ```

#### 2. Get Appointments by Patient ID

Retrieves a list of appointments for a specific patient.

- **Method**: GET
- **URL**: `/api/appointments/{patientId}`
- **Response**:
    ```json
    [
        {
            "id": "long",
            "patientId": "string",
            "providerId": "string",
            "dateTime": "YYYY-MM-DDTHH:MM:SS",
            "status": "Scheduled"
        }
    ]
    ```
- **Example**:
    ```bash
    curl -X GET http://localhost:8080/api/appointments/123456
    ```

#### 3. Cancel Appointment

Cancels a specific appointment by ID.

- **Method**: DELETE
- **URL**: `/api/appointments/{appointmentId}`
- **Response**: HTTP 200 OK if successful
- **Example**:
    ```bash
    curl -X DELETE http://localhost:8080/api/appointments/1
    ```
