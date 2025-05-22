FROM maven:3.5.2-jdk-8-alpine AS MAVEN_BUILD
MAINTAINER Sarvesh Ojha
COPY pom.xml /build/
COPY src/ /build/src
WORKDIR /build/
RUN mvn package
FROM openjdk:8-jre-alpine
WORKDIR /firstmicroservice
COPY --from=MAVEN_BUILD /build/target/firstmicroservice.jar /firstmicroservice/
ENTRYPOINT ["java", "-jar", "firstmicroservice.jar"]