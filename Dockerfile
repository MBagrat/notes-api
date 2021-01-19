FROM openjdk:11-jdk
EXPOSE 8084
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=docker","/app.jar"]