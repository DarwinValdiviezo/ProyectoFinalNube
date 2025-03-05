# NUEVO ARCHIVO

# ETAPA 1: COMPILATION

# Usar una imagen de OpenJDK
FROM openjdk:17-jdk-slim AS builder
# Setear el directorio de trabajo
WORKDIR /app
# Copiar solo lo necesario para instalar dependencias
COPY ./.mvn ./.mvn
COPY ./mvnw ./mvnw
COPY ./pom.xml ./pom.xml
# 🔥 SOLUCIÓN: Dar permisos de ejecución a mvnw
RUN chmod +x mvnw
# Descargar dependencias en cache
RUN ./mvnw dependency:go-offline
# Copiar el codigo fuente y compilar
COPY ./src ./src
RUN ./mvnw clean package -DskipTests

# ETAPA 2: IMAGEN DE PRODUCCION

# Usar una imagen de OpenJDK
FROM openjdk:17-jdk-slim
# Setear el directorio de trabajo
WORKDIR /app
# Copiar el JAR desde la etapa de compilation
COPY --from=builder /app/target/client-app-0.0.1-SNAPSHOT.jar .
# Exponer el puerto
EXPOSE 8080
# Comando de inicio
ENTRYPOINT ["java", "-jar", "client-app-0.0.1-SNAPSHOT.jar"]