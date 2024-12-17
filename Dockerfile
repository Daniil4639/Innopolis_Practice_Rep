FROM eclipse-temurin:latest
COPY db-api.jar app.jar
COPY application.properties application.properties
ENTRYPOINT ["java", "-jar", "app.jar"]