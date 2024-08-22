# Use the official Amazon Corretto image to create a build artifact
# Amazon Corretto is a no-cost, multiplatform, production-ready distribution of the OpenJDK.
FROM amazoncorretto:17 AS builder

# Set the working directory in the container
WORKDIR /app

# Copy the pom.xml and source code into the container
COPY pom.xml .
COPY src ./src

# Package the application
RUN mvn clean package -DskipTests

# Use the official Amazon Corretto image to run the application
FROM amazoncorretto:17

# Set the working directory in the container
WORKDIR /app

# Copy the jar file from the builder stage
COPY --from=builder /app/target/casemanagement-0.0.1-SNAPSHOT.jar app.jar

# Expose the port that the application will run on
EXPOSE 8080

# Define the command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]


