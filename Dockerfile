
FROM maven:3.6.0-jdk-8-alpine AS MAVEN_BUILD
MAINTAINER Mohamed Abdelrahman
COPY pom.xml /build/
COPY src /build/src/
WORKDIR /build/
RUN mvn package

FROM openjdk:8-jdk-alpine
WORKDIR /app
COPY --from=MAVEN_BUILD /build/target/*.jar /app/codebase.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "codebase.jar"]
