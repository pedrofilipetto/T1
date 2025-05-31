# Use uma imagem base leve com Java 17
FROM eclipse-temurin:17-jre-alpine

# Diretório de trabalho dentro do container
WORKDIR /app

# Copia o JAR gerado pelo Maven para o container
COPY target/*.jar app.jar

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
