# Etapa de construção
FROM ubuntu:latest AS build

RUN apt-get update
RUN apt-get install -y openjdk-17-jdk
RUN apt-get install -y maven

WORKDIR /app

# Copie o arquivo pom.xml separadamente para otimizar o cache
COPY pom.xml .
RUN mvn dependency:go-offline

# Copie o restante do código-fonte
COPY . .

# Execute o comando Maven para construir o aplicativo
RUN mvn clean install

# Etapa de produção
FROM openjdk:17-jdk-slim

WORKDIR /app

EXPOSE 8080

# Copie o JAR construído da etapa de construção
COPY --from=build /app/target/todolist-1.0.0.jar app.jar

ENTRYPOINT [ "java", "-jar", "app.jar" ]
