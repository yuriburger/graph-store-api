FROM maven:3-eclipse-temurin-19 as build-env

WORKDIR /app

# Copy everything and build
COPY . ./

RUN mvn package

# Build runtime image
FROM eclipse-temurin:19-jre

WORKDIR /app
COPY --from=build-env /app/target/graphstoreapi-0.0.1-SNAPSHOT.jar .

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app/graphstoreapi-0.0.1-SNAPSHOT.jar"]