FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

RUN apk update && apk add --no-cache ca-certificates && update-ca-certificates

RUN addgroup -S weathergroup && adduser -S weatheruser -G weathergroup
RUN mkdir -p /app/logs

COPY target/weatherapp-1.0.jar /app/weatherapp.jar
RUN chown -R weatheruser:weathergroup /app

USER weatheruser

ENTRYPOINT ["java", "-Djava.net.preferIPv4Stack=true", "-jar", "/app/weatherapp.jar"]