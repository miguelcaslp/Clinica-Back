#FROM maven:3.8.5-openjdk-17 as build
#COPY . .
#RUN mvn clean package -DskipTests

#FROM openjdk:17-jdk-slim
#COPY --from=build /target/tutorialhub-0.0.1-SNAPSHOT.jar /app/tutorialhub.jar
#EXPOSE 8080
#CMD ["java","-jar","/app/tutorialhub.jar"]


# Etapa 1: Compilación
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Imagen final de ejecución
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/tutorialhub-0.0.1-SNAPSHOT.jar tutorialhub.jar
EXPOSE 8080
CMD ["java", "-jar", "tutorialhub.jar"]
