FROM openjdk:17-jdk-alpine
RUN apk add --no-cache git
VOLUME /tmp
WORKDIR /app
RUN git clone <repo-url> .
COPY ./build/libs/diplomski-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]