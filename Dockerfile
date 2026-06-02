#Use an official Maven image to build the Spring boot app
#FROM maven:3.8.4-openjdk-17 AS build
FROM maven:3.9.6-eclipse-temurin-17 AS build

#Set the working directory
WORKDIR /app

# Copy the pom.xml and install dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the source code and build the application
COPY src ./src
RUN mvn clean package -DskipTests

# Use an official openjdk image to run the application
#FROM openjdk:17-jdk-slim
FROM eclipse-temurin:17-jdk

#Set the working directory
WORKDIR /app

# Copy the build JAR file from the build stage
#COPY --from=build /app/target/ResumeBuilder-0.0.1-SNAPSHOT.jar .
COPY --from=build /app/target/*jar app.jar

# Expose port 8080
EXPOSE 8080

#Specify the command to run the application
#ENTRYPOINT ["java", "-jar", "/app/ResumeBuilder-0.0.1-SNAPSHOT.jar"]
ENTRYPOINT ["java", "-jar", "app.jar"]
