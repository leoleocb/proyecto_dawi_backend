FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY target/ms-reportes-1.0.0.jar app.jar

EXPOSE 8003

ENTRYPOINT ["java", "-jar", "app.jar"]