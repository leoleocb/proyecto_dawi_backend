FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY target/ms-auth-1.0.0.jar app.jar

EXPOSE 8000

ENTRYPOINT ["java", "-jar", "app.jar"]