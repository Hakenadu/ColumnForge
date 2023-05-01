FROM maven:3.8.3-openjdk-17 as build
WORKDIR /app
COPY . .
RUN mvn -DskipTests package spring-boot:repackage

FROM openjdk:17-ea-17-slim-buster
WORKDIR /app
COPY --from=build /app/target/column-forge-api.jar ./column-forge-api.jar
EXPOSE 8080
CMD ["java", "-jar", "column-forge-api.jar"]