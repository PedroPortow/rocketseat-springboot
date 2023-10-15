# Defina a imagem base do Ubuntu
FROM ubuntu:latest AS build

# Atualize e instale o OpenJDK
RUN apt-get update
RUN apt-get install -y openjdk-17-jdk

# Copie o código-fonte para o contêiner
COPY . /app

# Defina o diretório de trabalho como /app
WORKDIR /app

# Instale o Maven
RUN apt-get install -y maven

# Execute o comando Maven
RUN mvn clean install

# Mude para uma imagem base mais leve
FROM openjdk:17-jdk-slim

# Exponha a porta 8080
EXPOSE 8080

# Copie o JAR construído a partir da etapa anterior
COPY --from=build /app/target/todolistapi-1.0.0.jar /app.jar

# Defina o comando de entrada
CMD ["java", "-jar", "/app.jar"]
