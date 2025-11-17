# Use a slim, official Java 17 runtime image
FROM eclipse-temurin:17-jdk-jammy

# Set working directory in the container
WORKDIR /app

# Copy the fat JAR produced by Maven into the container
COPY target/ecommerce-0.0.1-SNAPSHOT.jar app.jar

ENV SPRING_PROFILES_ACTIVE=docker
# Expose the port your Spring app listens on
EXPOSE 8080

# Start the jar
ENTRYPOINT ["java","-jar","/app/app.jar"]