FROM eclipse-temurin:17-jdk-alpine

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} app.jar

#lancer le conteneur avec un utilisateur non-root
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

EXPOSE 8080


ENTRYPOINT ["java", "-jar", "/app.jar"]
