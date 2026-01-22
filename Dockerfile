# ------------------- BUILD STAGE -------------------
FROM ghcr.io/graalvm/graalvm-ce:21.3.0-java11 AS builder

# Set working directory
WORKDIR /app

# Copy Gradle wrapper and build files
COPY gradlew .
COPY gradle ./gradle
COPY build.gradle.kts settings.gradle.kts ./

# Pre-download dependencies to cache layers
RUN ./gradlew build --dry-run

# Copy the rest of the source code
COPY src ./src

# Build the Spring Boot fat jar
RUN ./gradlew clean bootJar -x test

# ------------------- RUNTIME STAGE -------------------
FROM ghcr.io/graalvm/graalvm-ce:21.3.0-java11

WORKDIR /app

# Copy the fat jar from the builder stage
COPY --from=builder /app/build/libs/*.jar app.jar

# Expose application port
EXPOSE 8080

# Environment variables for MySQL (Rancher injects these as env vars)
ENV SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/mydb
ENV SPRING_DATASOURCE_USERNAME=root
ENV SPRING_DATASOURCE_PASSWORD=password

# Run the Spring Boot application
ENTRYPOINT ["java","-jar","app.jar"]
