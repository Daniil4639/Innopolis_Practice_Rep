FROM eclipse-temurin:latest
COPY db-api.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]