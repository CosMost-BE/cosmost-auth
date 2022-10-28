FROM openjdk:17-ee-11-jdk-slim
COPY build/libs/cosmost-1.0.jar CosmostService.jar
ENTRYPOINT ["java", "-jar", "CosmostService.jar"]