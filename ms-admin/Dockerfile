FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY target/ms-admin-1.0.0.jar app.jar

EXPOSE 8001

ENTRYPOINT ["java", "-jar", "app.jar"]