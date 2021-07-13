FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/ISO8583-0.0.1-SNAPSHOT-jar-with-dependencies.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]