FROM eclipse-temurin:17
LABEL authors="Jamal"
WORKDIR /app
COPY target/projetoTCC-0.0.1-SNAPSHOT.jar /app/apiV1.jar
ENTRYPOINT ["java", "-jar", "apiV1.jar"]