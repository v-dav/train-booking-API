# Train Booking System

## Backend
### Stack
1. Java Spring Boot 3
2. Gradle
3. Spring Security
4. Google OAuth
5. Apache Tomcat
6. Springdoc OpenAPI with SwaggerUI for API documentation
7. PostgreSQL hosted on Supabase (Trains, Bookings, Users)

### Main Components:
1. Train Service
3. Booking Service
4. User Service

### API Endpoints:
1. `/trains` - CRUD operations for trains
3. `/bookings` - Create, read, and cancel bookings
4. `/users` - User registration and management
5. `/status`- Checks the API status

## Frontend
Simple interface to search for trains, view available seats, and make bookings
1. React.js
2. fetchAPI
2. Vanilla JS
3. Vite.js
4. Bootstrap CSS

## Key Features:
1. Search and book for trains
2. View train schedules and available seats
4. User registration and login with email password and Google OAuth
5. Admin dashboard (CRUD operations on all entities and search all)
6. User dashboard (book trains and manage bookings and search bookings)

## Technical Aspects to Highlight:
1. RESTful API design
2. Data validation and error handling
3. Basic authentication and authorization and Google OAuth leveraging Spring Security
4. Database relationships and queries with PostgreSQL
5. Integration between frontend and backend
6. Testing with Postman 
7. Containerized with Docker 
8. Deployment on Render
