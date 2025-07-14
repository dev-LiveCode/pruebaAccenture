# Etapa 1: Construcción del proyecto con Maven y Java 17
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app

# Copiar el pom.xml y descargar dependencias (caché eficiente)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiar el resto del proyecto
COPY src ./src

# Construir el JAR sin ejecutar pruebas
RUN mvn clean package -DskipTests

# Etapa 2: Imagen ligera para ejecución
FROM eclipse-temurin:17-jre

WORKDIR /app

# Copiar el JAR generado desde la etapa de build
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]