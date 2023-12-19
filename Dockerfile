FROM openjdk:11-jre-slim

ARG JAR_FILE=./build/libs/*-SNAPSHOT.jar

COPY ${JAR_FILE} street-review.jar

ENTRYPOINT ["java","-jar","/street-review.jar"]
