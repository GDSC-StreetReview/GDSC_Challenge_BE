FROM openjdk:11-jre-slim

# SSL 인증서를 복사
COPY domain.pem /usr/local/share/ca-certificates/domain.crt

# SSL 인증서를 시스템에 등록
RUN update-ca-certificates


ARG JAR_FILE=./build/libs/*-SNAPSHOT.jar

COPY ${JAR_FILE} street-review.jar

ENV SPRING_PROFILES_ACTIVE prod

ENTRYPOINT ["java", "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}", "-jar","/street-review.jar"]
