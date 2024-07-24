

FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/casemanagement-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
LABEL authors="frenamhretab"
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/app.jar"]
