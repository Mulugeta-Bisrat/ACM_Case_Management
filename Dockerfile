# Use the official Amazon Corretto image as the base for the builder stage
FROM public.ecr.aws/amazoncorretto/amazoncorretto:17 AS builder

# Install Maven
RUN yum update -y && \
    yum install -y maven

# Set the working directory in the container
WORKDIR /app

# Copy the pom.xml and source code into the container
COPY pom.xml .
COPY src ./src

# Package the application
RUN mvn clean package -DskipTests

# Use the official Amazon Corretto image from AWS ECR Public to run the application
FROM public.ecr.aws/amazoncorretto/amazoncorretto:17

# Set the working directory in the container
WORKDIR /app

# Copy the jar file from the builder stage
COPY --from=builder /app/target/casemanagement-0.0.1-SNAPSHOT.jar app.jar

# Expose the port that the application will run on
EXPOSE 8080

# Define the command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
