FROM openjdk:20-jdk-alpine
VOLUME /tmp
WORKDIR /app
COPY ./build/libs/diplomski-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]