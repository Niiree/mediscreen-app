FROM gradle:8.5-jdk17 AS build
WORKDIR /app

# Copier les fichiers de build
COPY build.gradle settings.gradle gradlew ./
COPY gradle ./gradle
COPY src ./src

# Rendre gradlew exécutable
RUN chmod +x gradlew

# Construire le projet sans exécuter les tests
RUN ./gradlew build -x test

FROM eclipse-temurin:17-jre
WORKDIR /app

# Copier le jar généré depuis l'étape de build
COPY --from=build /app/build/libs/*.jar app.jar

# Exposer le port 9000 (modifie si besoin)
EXPOSE 9000

# Lancer l'application
ENTRYPOINT ["java", "-jar", "app.jar"]
