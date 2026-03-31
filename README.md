# 🚖 Ride App – Microservices Architecture (Spring Boot)

A scalable ride-hailing backend system built using **Spring Boot Microservices**.  
The project demonstrates how modern ride applications (similar to Uber/Ola) can be designed using **distributed services, caching, authentication, and real-time communication**.

The system is designed with independent microservices, each responsible for a specific domain such as authentication, booking, location tracking, and real-time communication.

---

## 🏗️ Architecture

The application follows a **microservices architecture** where each service runs independently and communicates with others when required.

### Services included:
RideApp
- BookingService
- DiscoveryService
- Ride_AuthService
- Ride_EntityService
- Ride_LocationService
- Ride_Review
- SocketService


---

## ⚙️ Tech Stack

### Backend
- Java
- Spring Boot
- Spring Security
- JWT Authentication

### Microservices Infrastructure
- Spring Cloud
- Service Discovery

### Caching
- Redis

### Communication
- REST APIs
- WebSockets

### Database Layer
- JPA / Hibernate

---

## 🔐 Ride_AuthService

Handles authentication and authorization for the platform.

### Features
- JWT based authentication  
- Spring Security integration  
- Supports Passenger login  
- Supports Driver login  
- Secure API access across services  

### Responsibilities
- User login  
- Token generation  
- Token validation  
- Role-based authentication (Driver / Passenger)  

---

## 📦 Ride_EntityService

This service contains all domain models used across the application.

It acts as the **central entity layer** shared by other services.

### Entity Models
- BaseModel.java  
- Booking.java  
- BookingStatus.java  
- Car.java  
- CarType.java  
- Color.java  
- DBConstant.java  
- Driver.java  
- DriverApprovalStatus.java  
- ExactLocation.java  
- NamedLocation.java  
- OTP.java  
- Passenger.java  
- PassengerReview.java  

### Purpose
- Centralized entity definitions  
- Shared models across microservices  
- Database mapping using JPA  

---

## 📍 Ride_LocationService

Responsible for tracking driver locations and finding nearby drivers.

### Key Features
- Stores live driver location  
- Uses Redis Cache for fast location lookup  
- Retrieves nearby drivers based on passenger location  

### Responsibilities
- Update driver location  
- Fetch nearby drivers  
- Maintain real-time location cache  

---

## 🔌 SocketService

Handles WebSocket communication across the system.

### Responsibilities
- Manage real-time connections  
- Send ride updates to users  
- Push driver location updates  
- Maintain socket sessions  

### Enables real-time interaction between:
- Passenger  
- Driver  
- Booking service  

---

## 📖 BookingService

The core orchestration service responsible for ride management.

### Responsibilities
- Create ride booking  
- Interact with Auth Service  
- Interact with Location Service  
- Communicate via Socket Service  
- Manage ride lifecycle  

### Redis Usage
- Active ride tracking  
- Booking cache  
- OTP verification  

---

## ⭐ Ride_Review

Handles reviews and ratings between passengers and drivers.

### Features
- Passenger reviews driver  
- Rating storage  
- Review management  

---

## 🔎 DiscoveryService

Used for service discovery between microservices.

### Responsibilities
- Register services  
- Enable dynamic service lookup  
- Simplify inter-service communication  

---

## 🔁 Ride Booking Flow (Simplified)

1. Passenger logs in via Auth Service  
2. Passenger requests ride via Booking Service  
3. Booking Service requests nearby drivers from Location Service  
4. Driver receives request through Socket Service  
5. Driver accepts ride  
6. Booking Service tracks ride via Redis cache  
7. OTP verification for ride start  
8. Ride completion  
9. Review stored via Review Service  

---
