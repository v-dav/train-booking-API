# Train Booking API Prototype
![chrome-capture-2024-9-3](https://github.com/user-attachments/assets/302373ce-6a1c-4938-89d5-9e1ba6c5d4c9)

## API documentation [here](https://traindemo-latest.onrender.com/api/v1/swagger-ui/index.html)
## Live here [here](https://train-booking-api-tid5.onrender.com/)

## Backend
### Stack
1. Java Spring Boot 3
2. Gradle
3. Spring Security
4. Google OAuth
5. Apache Tomcat
6. Springdoc OpenAPI with SwaggerUI for API documentation
7. PostgreSQL hosted on Supabase (Trains, Bookings, Users)
8. Deployed via Docker container on Render

### Main Components:
1. Train Service
3. Booking Service
4. User Service

### API Endpoints:
1. `/train` - CRUD operations for trains
3. `/booking` - Create, read, and cancel bookings
4. `/user` - User registration and management
5. `/status`- Checks the API status
6. `/swagger-ui/index.html` - API documentation

## Frontend
Simple interface to search for trains, view available seats, and make bookings
1. React.js
2. fetchAPI
2. Vanilla JS
3. Vite.js
4. Bootstrap CSS
5. Deployed on Render separately

## Key Features:
1. Search and book for trains
2. View train schedules and available seats
4. User registration and login with email password and Google OAuth
5. Admin dashboard (CRUD operations on all entities and search all)
6. User dashboard (book trains and manage bookings and search bookings)

## Technical Aspects to Highlight:
1. RESTful API design
2. Data validation and error handling
3. Basic authentication and authorization leveraging Spring Security
4. Database relationships and queries with PostgreSQL
5. Integration between frontend and backend
6. Testing with Postman 
7. Containerized with Docker 
8. Deployment on Render
