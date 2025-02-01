 #Step 1: Use the official Maven image to build the project
FROM maven:3.8.5-openjdk-17 AS build

# Step 2: Set the working directory in the container
WORKDIR /app

# Step 3: Copy the project files to the container
COPY . .

# Step 4: Build the project using Maven
RUN mvn clean package -DskipTests

# Step 5: Use the official OpenJDK image for the runtime
FROM openjdk:17-jdk-alpine

# Step 6: Set the working directory in the container
WORKDIR /app

# Step 7: Copy the JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Step 8: Expose the application's port (8080)
EXPOSE 8080

# Step 9: Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]