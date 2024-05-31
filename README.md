# Magallanes - Booking Trips Project - Project for M3 Level

Magallanes is a project for booking trips, designed with Domain-Driven Design (DDD) and Hexagonal Architecture styles. It follows the Modulitic Monolith architectural style for better organization and scalability. This project utilizes the following technologies:

- Spring Modulith
- Spring OpenFeign
- Flyway
- Rabbit MQ
- Spring Email
- Mapstruct
- JWT

## Installation

1. Clone the repository
2. Make sure you have Java 17 or higher installed
3. Set up Rabbit MQ and configure it as per project requirements
4. Configure the Postgres database schema

## Usage

1. Build the project using Gradle::bootRun
2. Run the application
3. Access the application through the provided endpoints
4. Explore the different modules and functionalities for booking trips

## Modules

### 1. Trip Module

- Responsible for handling trip bookings
- Implements DDD concepts for domain logic
- Utilizes Spring Modulith for modular organization
- 
### 2. Account Module

- Manages users and their roles
- Implements JWT for authentication and authorization
- Ensures secure access to booking functionalities

### 3. Core Module

- Manages shared functionality
- Uses Spring Feign for Countries service communication
- 
### 4. Email Module

- Handles email notifications for bookings and updates
- Uses Spring Email for sending emails
- 
### 5. Image Module

- Handles images for different Places
- Stores images in database
- 
### 5. Notification Module

- Uses Rabbit MQ to send notifications