# 🎢 Roller Coaster Recommender Service
A content-based and user-aware recommendation engine for roller coasters, implemented as a Spring Boot microservice and deployed to AWS.

## Overview
The **Roller Coaster Recommender Service** provides REST APIs for generating personalized or similarity-based coaster recommendations. It uses coaster metadata, user ratings, and computed vector features to return accurate and deterministic results optimized for low-latency applications.

This project serves as both a functional microservice and a technical portfolio piece demonstrating:

- Spring Boot microservice architecture
- Machine-learning-aligned recommendation techniques
- API design (accessible via OpenAPI/Swagger)
- Containerization and cloud-native deployment
- AWS infrastructure architecture (EKS, RDS, ECR)
- Database schema and ORM design
- API usage (using Python developed API currently deployed on AWS at http://ac3a45cc0862c4debaeed73d6650680d-1292001656.us-east-2.elb.amazonaws.com/docs)

## ✨ Features
### 1. Personalized Recommendations
- Content-based filtering  
- Cosine similarity between encoded coaster attributes  
- Custom adjustments to baseline recommendations using country and increasing importance of community rating versus general coaster metadata

### 2. Coaster Similarity Endpoints
Given a coaster, the API returns the closest alternatives using:
- Ride type  
- Manufacturer  
- Thrill characteristics  
- General user averages (where available)
- Statistical/embedded ML features  

### 3. CRUD + Metadata APIs
Well-documented endpoints for:
- Coasters  
- Countries  
- Ratings
- Users

### 4. Full OpenAPI Coverage
Available via Swagger UI (`/swagger-ui.html` or '/v3/api-docs').

### 5. Fully Containerized
Packaged as a lightweight Docker image deployable in any environment.

## 🚀 Deployment Architecture (AWS)
### AWS EKS (Elastic Kubernetes Service)
- Runs the Dockerized Spring Boot microservice  
- Scalable deployment using Kubernetes HPA  
- Load-balanced using AWS Load Balancer Controller  
- Liveness/readiness checks ensuring smooth rollouts  

### AWS RDS (PostgreSQL)
- Stores coaster metadata, ratings, and access rules  
- Uses HikariCP for pooling  
- Supports future scaling and durability  

### AWS ECR
- Hosts versioned Docker images  

## 🔐 Security Considerations
This project is intentionally open for demonstration purposes.  
In a production setting, it would be secured with:

- OAuth2 / OpenID Connect  
- JWT identity propagation  
- API gateway authentication & rate limiting  
- Role-based domain access  
- Encrypted secrets using AWS Secrets Manager  

## 🧪 Testing Strategy
The service includes:

- Unit tests for service and other logic  
- Reflection-based POJO verification tests  
- MockMvc controller tests  
- Deterministic test data for similarity validation  

## 📈 Why I Built This Project
### 1. Demonstrate practical engineering skills
I wanted this project to showcase end-to-end microservice development including:

- Spring Boot architecture  
- PostgreSQL schema design  
- Docker and Kubernetes deployment  
- AWS EKS/RDS/ECR setup  
- Real-world patterns for scalable backend services  
- Clean, layered, production-oriented coding
- Integration of more common SpringBoot services with ML endpoints deployed and developed separately (by me)

It illustrates how I design systems: clear separations of concern, maintainable abstractions, and cloud-native execution.

### 2. Build a real recommendation engine end-to-end
I wanted a project that integrates:

- Data modeling  
- Feature engineering  
- Vector similarity algorithms  
- Real-time API design  
- Performance and caching considerations  

It is technically interesting while still practical and easy to demo.

## 📘 Future Enhancements
- User-based collaborative filtering  
- OAuth2/JWT identity layer  
- REACT frontend for user interaction 
- Leverage separate Python API I developed providing Cosine similarity
