# Use official maven/Java 17 image to create build artifact
# named as build
FROM maven:3.8.1-openjdk-17-slim AS build

# Set the current working directory inside the image
WORKDIR /second

# Copy the pom.xml file to download dependencies
COPY pom.xml .

# Download the dependencies
RUN mvn dependency:go-offline -B

# Copy the project source
COPY src src

# Package the application
RUN mvn package -DskipTests

# Use OpenJDK for runtime
FROM openjdk:17-slim

WORKDIR /second

# Copy the jar file from build image
COPY --from=build /second/target/*.jar app.jar

# Run the jar file
ENTRYPOINT ["java","-jar","/second/app.jar"]
