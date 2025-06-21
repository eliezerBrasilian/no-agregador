FROM gradle:8.5-jdk21 AS build

WORKDIR /app

COPY build.gradle settings.gradle ./
COPY gradle ./gradle

#  (cache layer)
RUN gradle dependencies --no-daemon

COPY src ./src

RUN gradle build --no-daemon

FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

# Instala curl para health checks (opcional)
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

# Copia o JAR construído do stage anterior
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

ENV JAVA_OPTS="-Xmx512m -Xms256m"

# Comando para executar a aplicação
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"] 