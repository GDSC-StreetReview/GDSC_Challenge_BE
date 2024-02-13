FROM openjdk:11-jre-slim

ARG JAR_FILE=./build/libs/*-SNAPSHOT.jar

COPY ${JAR_FILE} street-review.jar

ENV SPRING_PROFILES_ACTIVE prod

ENTRYPOINT ["java", "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}", "-jar","/street-review.jar"]
