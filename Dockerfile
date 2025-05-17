# gateway/Dockerfile
FROM eclipse-temurin:17-jdk

# Ajuste aqui se seu jar tiver nome fixo diferente
ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]
