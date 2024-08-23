# Use the Maven/Java image from AWS ECR to create a build artifact
FROM 816069124974.dkr.ecr.us-west-2.amazonaws.com/base/maven:3.8.4-openjdk-17 AS builder

# Set the working directory in the container
WORKDIR /app

# Copy the pom.xml and download dependencies first to leverage Docker cache
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy the rest of the application source code
COPY src ./src

# Package the application
RUN mvn clean package -DskipTests

# Use the Eclipse Temurin OpenJDK image from AWS ECR to run the application
FROM 816069124974.dkr.ecr.us-west-2.amazonaws.com/base/eclipse-temurin:17-jre-jammy

# Set the working directory in the container
WORKDIR /app

# Copy the jar file from the builder stage
COPY --from=builder /app/target/casemanagement-0.0.1-SNAPSHOT.jar app.jar

# Expose the port that the application will run on
EXPOSE 8080

# Define the command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
