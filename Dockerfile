# Use a imagem oficial do OpenJDK 17 com o Gradle como a imagem base
FROM gradle:8.5.0-jdk17
WORKDIR /app
COPY build.gradle .
COPY settings.gradle .
COPY src ./src
RUN gradle build
EXPOSE 8080
CMD ["java", "-jar", "build/libs/sicredi-votes-1.0.0.jar"]