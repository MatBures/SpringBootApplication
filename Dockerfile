FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/SpringBootApplication-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
ENTRYPOINT ["java","-jar","docker-spring-boot-postgres-1.0.0.jar"]