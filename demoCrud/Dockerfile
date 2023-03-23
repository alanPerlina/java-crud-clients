# Use the official maven/Java 8 image to create a build artifact.
# https://hub.docker.com/_/maven
FROM openjdk:17-alpine
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=/target/demoCrud.jar
COPY ${JAR_FILE} demoCrud.jar
CMD ["java", "-jar", "/demoCrud.jar"]