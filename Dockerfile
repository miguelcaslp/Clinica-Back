#FROM maven:3.8.5-openjdk-17 as build
#COPY . .
#RUN mvn clean package -DskipTests

#FROM openjdk:17-jdk-slim
#COPY --from=build /target/tutorialhub-0.0.1-SNAPSHOT.jar /app/tutorialhub.jar
#EXPOSE 8080
#CMD ["java","-jar","/app/tutorialhub.jar"]

#
# Build stage
#
FROM maven:3.8.5-openjdk-17 AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

#
# Package stage
#
FROM openjdk:17-jdk-slim
COPY --from=build /home/app/target/getyourway-0.0.1-SNAPSHOT.jar /usr/local/lib/demo.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/demo.jar"]