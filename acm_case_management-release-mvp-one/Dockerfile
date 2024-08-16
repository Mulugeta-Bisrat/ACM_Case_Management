# Use Eclipse Temurin OpenJDK 17 as the base image for the build stage
FROM eclipse-temurin:17-jdk as build
WORKDIR /app
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src
RUN chmod +x ./mvnw
RUN ./mvnw package -DskipTests

## Use Eclipse Temurin OpenJDK 17 as the base image for the run stage
#FROM eclipse-temurin:17-jdk as run
#WORKDIR /app
#COPY --from=build C:/Users/Peter/Desktop/ACM-Project/acm_case_management-release-mvp-one/target/casemanagement-0.0.1-SNAPSHOT.jar /app/casemanagement-0.0.1-SNAPSHOT.jar
#EXPOSE 8080
#ENTRYPOINT ["java", "-jar", "casemanagement-0.0.1-SNAPSHOT.jar"]
##ENTRYPOINT ["java", "-jar", "app.jar"]
# ---- Run Stage ----
FROM eclipse-temurin:17-jre-alpine as runtime

# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR file from the build stage to the run stage
COPY --from=build /app/target/*.jar app.jar

# Expose the application port
EXPOSE 8080

# Define the entry point to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]