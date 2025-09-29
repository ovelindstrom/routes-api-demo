FROM registry.access.redhat.com/ubi8/openjdk-21 AS builder

WORKDIR /app

# Ensure the working directory has the correct permissions
USER root
RUN mkdir -p /app && chmod -R 777 /app


# Build dependency offline to leverage Docker layer caching
COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn --version
RUN mvn package -Dmaven.test.skip=true

# compute the created jar name and put it in a known location to copy to the next layer.
# If the user changes pom.xml to have a different version, or artifactId, this will find the jar

RUN grep version target/maven-archiver/pom.properties | cut -d '=' -f2 >.env-version
RUN grep artifactId target/maven-archiver/pom.properties | cut -d '=' -f2 >.env-id
RUN mv target/$(cat .env-id)-$(cat .env-version).jar target/export-run-artifact.jar

FROM registry.access.redhat.com/ubi8/openjdk-21-runtime
COPY --from=builder /app/target/export-run-artifact.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"] route-api
