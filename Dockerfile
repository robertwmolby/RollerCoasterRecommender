# ---- Build stage ----
FROM gradle:8.10.2-jdk21-alpine AS build

WORKDIR /app

# Copy Gradle config and resolve dependencies first for better caching
COPY build.gradle settings.gradle gradle.properties* ./
COPY gradle ./gradle

RUN gradle --no-daemon dependencies || true

# Now copy the source and build the boot jar
COPY src ./src
RUN gradle --no-daemon clean bootJar

# ---- Runtime stage ----
FROM eclipse-temurin:21-jre-alpine

# Create non-root user
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

WORKDIR /app

# Copy the fat jar from the build stage
# Adjust the name if your artifactId is different
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

USER appuser

ENTRYPOINT ["java","-jar","/app/app.jar"]
