FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/task_manager_api-0.0.1-SNAPSHOT.jar  app.jar

EXPOSE 8080

ENV SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/task_manager
ENV SPRING_DATASOURCE_USERNAME=myuser
ENV SPRING_DATASOURCE_PASSWORD=mypassword

ENTRYPOINT ["java", "-jar", "app.jar"]