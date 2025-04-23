#!/bin/sh

# Função para verificar se um comando existe
command_exists() {
    command -v "$1" >/dev/null 2>&1
}

# Verifica e instala Git se não estiver presente
if ! command_exists git; then
    echo "Git not found. Installing..."
    apk add --no-cache git
else
    echo "Git already installed."
fi

# Verifica e instala Maven se não estiver presente
if ! command_exists mvn; then
    echo "Maven not found. Installing..."
    apk add --no-cache maven
else
    echo "Maven already installed."
fi

# Pull the latest code from the main branch
git pull origin main

# Clean and package the application, skipping tests
mvn clean package -DskipTests

# Run the jar file
java -jar target/*.jar
