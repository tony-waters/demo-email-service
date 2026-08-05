FROM eclipse-temurin:25-jre
WORKDIR /app

COPY target/demo-email-service-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
