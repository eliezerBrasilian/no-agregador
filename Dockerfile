FROM openjdk:22
#WORKDIR /usr/src/app

# Endereço da pasta target
ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} app.jar

# Comandos que serão executados assim que o container inicializar
ENTRYPOINT ["java", "-jar", "/app.jar"]
