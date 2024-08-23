# Use the build argument for Maven base image repository
ARG MAVEN_REPO
FROM ${MAVEN_REPO}:3.8.4-openjdk-17 AS builder

# Set the working directory in the container
WORKDIR /app

# Copy the pom.xml and download dependencies first to leverage Docker cache
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy the rest of the application source code
COPY src ./src

# Package the application
RUN mvn clean package -DskipTests

# Use the build argument for the runtime base image repository
ARG RUNTIME_REPO
FROM ${RUNTIME_REPO}:17-jre-jammy

# Set the working directory in the container
WORKDIR /app

# Copy the jar file from the builder stage
COPY --from=builder /app/target/casemanagement-0.0.1-SNAPSHOT.jar app.jar

# Expose the port that the application will run on
EXPOSE 8080

# Define the command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
