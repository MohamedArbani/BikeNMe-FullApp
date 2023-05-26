# stage 1

FROM maven:3-openjdk-11-slim AS MAVEN_BUILD
WORKDIR /backend
COPY . .
RUN mvn package -DskipTests


# stage 2

FROM openjdk:18-ea-11-alpine
WORKDIR /app

COPY --from=MAVEN_BUILD backend/target/RestFull-0.0.1.jar /app 

ENTRYPOINT ["java", "-jar", "RestFull-0.0.1.jar"]


