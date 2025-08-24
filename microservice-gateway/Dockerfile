# Étape 1 : Build avec Gradle
FROM gradle:8.5-jdk17 AS build
WORKDIR /app

# Copier uniquement les fichiers de build en premier (meilleur cache Docker)
COPY build.gradle settings.gradle ./
COPY gradle ./gradle
RUN gradle build -x test --no-daemon || return 0

# Copier ensuite le reste du projet
COPY . .
RUN gradle bootJar --no-daemon

# Étape 2 : Run avec JRE léger
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copier le jar depuis l'étape précédente
COPY --from=build /app/build/libs/*.jar app.jar

# Exposer le port du gateway
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]