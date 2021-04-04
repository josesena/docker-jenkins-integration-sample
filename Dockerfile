FROM openjdk:11
EXPOSE 8090
ARG JAVA_FILE=target/*.jar

COPY ${JAVA_FILE} app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]

