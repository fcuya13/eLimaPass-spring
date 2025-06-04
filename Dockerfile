# Build stage
FROM maven:3.9-eclipse-temurin-21-alpine as build
WORKDIR /workspace/app

# Copy pom.xml first for dependency resolution (better layer caching)
COPY pom.xml .

# Download dependencies
RUN mvn dependency:go-offline -B

# Copy source code
COPY src/ src/

# Build application
RUN mvn package -DskipTests -B

# Runtime stage
FROM eclipse-temurin:21-jre-alpine
VOLUME /tmp
WORKDIR /app

# Copy the built JAR file
COPY --from=build /workspace/app/target/*.jar app.jar

# Run the application
ENTRYPOINT ["java","-jar","/app/app.jar"]