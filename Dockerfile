# Build stage
FROM maven:3.9.9-eclipse-temurin-17 AS builder
WORKDIR /workspace
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw mvnw
COPY mvnw.cmd mvnw.cmd
COPY src src
RUN chmod +x mvnw && ./mvnw -q -DskipTests package

# Runtime stage
FROM eclipse-temurin:17-jre
WORKDIR /app
RUN useradd -r -u 1001 appuser
COPY --from=builder /workspace/target/springboot-production-showcase-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
USER appuser
ENTRYPOINT ["java","-jar","/app/app.jar"]
