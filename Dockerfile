# ----------- Stage 1: Build the JAR -----------
FROM gradle:8.13-jdk17 AS builder

# Set working directory
WORKDIR /app

# Copy everything (or optimize later with layers)
COPY . .

# Build the Spring Boot application (skip tests if you like)
RUN gradle build -x test

# ----------- Stage 2: Run the JAR -----------
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy the built JAR from the previous stage
COPY --from=builder /app/build/libs/*.jar app.jar

# Expose the app's port
EXPOSE 8080

# Run the JAR
ENTRYPOINT ["java", "-jar", "app.jar"]
