FROM openjdk:23
COPY app.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]