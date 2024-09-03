# Stage 1: Build the application
FROM openjdk:22-jdk-slim AS build

# Install wget, unzip, and other necessary tools using apt-get
RUN apt-get update && apt-get install -y wget unzip

# Install Gradle
RUN wget https://services.gradle.org/distributions/gradle-8.9-bin.zip -P /tmp \
    && unzip -d /opt/gradle /tmp/gradle-8.9-bin.zip \
    && rm /tmp/gradle-8.9-bin.zip
ENV GRADLE_HOME /opt/gradle/gradle-8.9
ENV PATH $GRADLE_HOME/bin:$PATH

# Set the working directory inside the container
WORKDIR /home/gradle/project

# Copy the project files
COPY --chown=root:root . .

# Ensure the Gradle wrapper is executable
RUN chmod +x gradlew

# Build the project
RUN ./gradlew build --no-daemon

# Stage 2: Launch the app
FROM amazoncorretto:22-alpine-jdk

# Adjust this path based on the actual output directory
ARG JAR_FILE=build/libs/*.jar
COPY --from=build /home/gradle/project/${JAR_FILE} app.jar

# Expose the port your application runs on
EXPOSE 8080

ENTRYPOINT ["java","-jar","/app.jar"]
