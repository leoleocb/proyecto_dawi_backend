FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY target/ms-clinica-1.0.0.jar app.jar

EXPOSE 8002

ENTRYPOINT ["java", "-jar", "app.jar"]