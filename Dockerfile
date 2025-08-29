FROM gradle:jdk17 AS build
WORKDIR /app

COPY build.gradle.kts .
COPY settings.gradle.kts .
COPY gradle ./gradle
COPY gradlew .

# Copy source code and build the application
COPY src ./src
RUN ./gradlew clean build -x test

# Use an official OpenJDK image to run the application
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/build/libs/web-0.0.1-SNAPSHOT.jar .

# Expose port 8080
EXPOSE 8080

# Specify the command to run the application
ENTRYPOINT ["java", "-jar", "/app/web-0.0.1-SNAPSHOT.jar"]