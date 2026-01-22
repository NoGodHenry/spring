# ------------------- BUILD STAGE -------------------
FROM ghcr.io/graalvm/jdk-community:21 AS builder

WORKDIR /app

# Copy Gradle wrapper and build files
COPY gradlew .
COPY gradle ./gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .
COPY src ./src

# Make gradlew executable
RUN chmod +x ./gradlew

# Build Spring Boot jar
RUN ./gradlew clean bootJar -x test

# ------------------- RUNTIME STAGE -------------------
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copy the jar from the builder
COPY --from=builder /app/build/libs/*.jar app.jar

# Expose port 8080
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java","-jar","app.jar"]
