# Stage 1: Build stage with Maven and JDK 17 to build the Spring Boot application
FROM oracle/graalvm-ce:17 as builder

# Set the working directory inside the container
WORKDIR /app

# Copy the source code and pom.xml to the container
COPY pom.xml .
COPY src ./src

# Build the application with Maven
RUN gu install native-image
RUN mvn clean package -DskipTests

# Stage 2: Final stage with MySQL and JRE 8 to run the Spring Boot application
FROM mysql:latest

# Expose the MySQL port
EXPOSE 3306

# Environment variables for MySQL database configuration (adjust as necessary)
ENV MYSQL_ROOT_PASSWORD=root
ENV MYSQL_DATABASE=aeon