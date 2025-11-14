# Stage 1: Build the application
FROM maven:3.9.5-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn -B package

# Stage 2: Create the final image
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
# FROM maven:3.9.4-eclipse-temurin-21 AS build

# WORKDIR /app

# # Copy the Maven descriptor first (for dependency download optimizations)
# COPY pom.xml .

# # Download dependencies (optional but can help layer caching)
# RUN mvn dependency:go-offline

# # Now copy the source code
# COPY src ./src

# # Build the application, compile and packaging into jar
# RUN mvn -B package

# # ──────────────────────────────
# # 2) Runtime Stage
# # ──────────────────────────────
# FROM eclipse-temurin:21-jre
# WORKDIR /app

# # Copy the generated .jar from the build stage
# COPY --from=build /app/target/*.jar app.jar

# # Specify the command to run your application
# ENTRYPOINT ["java", "-jar", "app.jar"]

