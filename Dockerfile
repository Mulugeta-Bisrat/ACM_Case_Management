# Use the official Amazon Corretto image as the base for the builder stage
FROM public.ecr.aws/amazoncorretto/amazoncorretto:17 AS builder

# Install wget and other necessary tools
RUN yum update -y && \
    yum install -y wget tar

# Install Maven 3.8.4
RUN wget https://downloads.apache.org/maven/maven-3/3.8.4/binaries/apache-maven-3.8.4-bin.tar.gz && \
    tar -zxvf apache-maven-3.8.4-bin.tar.gz -C /opt && \
    ln -s /opt/apache-maven-3.8.4/bin/mvn /usr/bin/mvn

# Verify Maven installation
RUN mvn -version

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
