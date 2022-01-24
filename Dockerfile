FROM openjdk:17-alpine
VOLUME /tmp
COPY /target/AgentService-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]