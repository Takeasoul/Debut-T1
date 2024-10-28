FROM openjdk:21-oracle
WORKDIR /app
COPY target/Debut-0.0.1-SNAPSHOT.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]