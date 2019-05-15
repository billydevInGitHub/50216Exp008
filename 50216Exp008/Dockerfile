FROM java:8
FROM maven:alpine

WORKDIR /app

COPY . /app

RUN mvn -v
RUN mvn clean install -DskipTests
EXPOSE 8443
LABEL maintainer="billydev@gmail.com"
ENTRYPOINT ["java","-jar","/app/target/spring-boot-50216Exp008-0.0.1-SNAPSHOT.jar"]