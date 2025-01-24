FROM openjdk:23
COPY app.jar app.jar
COPY resources/ resources/
ENTRYPOINT ["java", "-jar", "app.jar"]