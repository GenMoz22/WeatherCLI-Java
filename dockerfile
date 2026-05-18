FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY target/weatherapp-1.0.jar /app/weatherapp.jar
CMD ["java", "-jar", "/app/weatherapp.jar"]
