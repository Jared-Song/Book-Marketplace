FROM openjdk:11

VOLUME /tmp

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} app.jar

EXPOSE 8086

ENTRYPOINT ["java", "-jar", "/app.jar"]
