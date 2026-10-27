FROM eclipse-temurin:21-jdk-noble AS build

WORKDIR /workspace

COPY .mvn/ .mvn/
COPY mvnw pom.xml ./

RUN ./mvnw dependency:go-offline

COPY src/ src/

RUN ./mvnw package -DskipTests

#stage2
FROM eclipse-temurin:21-jre-noble
RUN groupadd --system app && useradd --system --gid app app

WORKDIR /app
COPY --from=build /workspace/target/*.jar app.jar
USER app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
