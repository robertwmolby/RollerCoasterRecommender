# BE AWARE!!!
For the immediate time all live apis and swagger pages are inaccessible in order to avoid accruing ongoing AWS costs. If you desire to see them for the purpose of evaluation purposes, please contact me and I will create the necessary aws artfacts and provide you with updated url's.

# Roller Coaster Recommender Service
A user recommendation system for roller coasters, implemented as a Spring Boot microservice and deployed to AWS.

## Overview
The Roller Coaster Recommender Service project provides REST APIs for generating personalized coaster recommendations. It uses coaster metadata and user ratings to return accurate results.

This project serves as both a functional microservice and a technical portfolio piece demonstrating:

- Spring Boot microservice architecture (access to endpoints via Swagger can currently be found at (http://a3edcc49fa0874231a028402c7ba9ebf-41972715.us-east-2.elb.amazonaws.com/swagger-ui/index.html)
- Machine-learning integration
- API design (accessible via OpenAPI/Swagger)
- Containerization and cloud-native deployment
- AWS infrastructure architecture (EKS, RDS, ECR)
- Database schema and ORM design
- API usage as well as creation (using Python developed API currently deployed on AWS at http://ac3a45cc0862c4debaeed73d6650680d-1292001656.us-east-2.elb.amazonaws.com/docs)

## Features
### 1. Personalized Recommendations
- Filtering based on "features" associated to roller coasters and the rankings assigned to those ocasters by a user.  
- Custom adjustments to pure similarity rankings returned by cosine similarity in order to take into account what countries a user could easily travel to and put higher emphasis on user ratings

### 2. Coaster Similarity Endpoints
Given a coaster, the API returns the closest alternatives using:
- Coaster type, intensity, and other characteristics  
- Manufacturer   
- General user averages (where available)
- Where information (height, length, duration) was unavailable use of "imputed" values derived from similar coasters was used

### 3. CRUD + Metadata APIs
Well-documented endpoints for:
- Coasters  
- Countries  
- User Ratings
- Users

### 4. Full OpenAPI Coverage
Available via Swagger UI (`/swagger-ui.html` or '/v3/api-docs').

### 5. Fully Containerized
Packaged as a lightweight Docker image deployable in most environments.

### 6. SpringBoot and Lombok
The service is built using SpringBoot and follows best practices for microservice architecture.  It leverages SpringBoot Data, Actuator, Secucrity (disabled by default), and other common SpringBoot features.  It also uses Lombok for immutable POJOs and other boilerplate code.  It has been built with Java 21.  

## Deployment Architecture (AWS)
### AWS EKS (Elastic Kubernetes Service)
- Runs the Dockerized Spring Boot microservice  
- Liveness/readiness via actuator endpoints  

### AWS RDS (PostgreSQL)
- Stores coaster metadata, ratings, and access rules  
- Uses HikariCP for pooling   

### AWS ECR
- Hosts versioned Docker images  

## Security Considerations
This project is intentionally openly accessible for demonstration purposes.  Sensitive information (passwords, users, etc) are maintained as Kubernetes secrets  
In a production setting, it would be secured with:

- OAuth2 / OpenID Connect or   
- JWT identity propagation  
- Role-based domain access  
- Encrypted secrets using AWS Secrets Manager  

## Testing Strategy
The service includes:

- Unit tests for service and other logic  
- Reflection-based POJO verification tests  
- MockMvc controller tests  
- Deterministic test data for similarity validation  

## External API Intebration
This service leverages separate Python APIs developed for user recommendation purposes.  Details on those APIs can currently be found in AWS here (http://ac3a45cc0862c4debaeed73d6650680d-1292001656.us-east-2.elb.amazonaws.com/docs) and the github repository for the Python project is located here: (http://ac3a45cc0862c4debaeed73d6650680d-1292001656.us-east-2.elb.amazonaws.com/docs).

## Future Enhancements
- User-based collaborative filtering  
- OAuth2/JWT identity layer  
- REACT frontend for user interaction 
- Leverage separate Python API I developed providing Cosine similarity
