FROM maven:3.9.4-eclipse-temurin-21 AS build
<<<<<<< HEAD
WORKDIR /app

=======

WORKDIR /app

>>>>>>> 2f64d57968e9967be8f8def8ecf5ee9a7f32cb91
# Copy the Maven descriptor first (for dependency download optimizations)
COPY pom.xml .

# Download dependencies (optional but can help layer caching)
RUN mvn dependency:go-offline

# Now copy the source code
COPY src ./src

<<<<<<< HEAD
# Build the application, compile & packaging into j.ar
RUN mvn -B package
=======
# Build the application, compile and packaging into jar 
RUN mvn -B package -DskipTests
>>>>>>> 2f64d57968e9967be8f8def8ecf5ee9a7f32cb91

# ──────────────────────────────
# 2) Runtime Stage
# ──────────────────────────────
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy the generated .jar from the build stage
COPY --from=build /app/target/*.jar app.jar
<<<<<<< HEAD
=======

# Specify the command to run your application
ENTRYPOINT ["java", "-jar", "app.jar"]
>>>>>>> 2f64d57968e9967be8f8def8ecf5ee9a7f32cb91

# Specify the command to run your application
ENTRYPOINT ["java", "-jar", "app.jar"]