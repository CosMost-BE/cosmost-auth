FROM openjdk:17-ea-11-jdk-slim
COPY build/libs/auth-1.0.jar AuthService.jar
ENTRYPOINT ["java", "-jar", "AuthService.jar"]