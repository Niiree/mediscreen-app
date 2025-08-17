FROM gradle:8.5-jdk17 AS build
WORKDIR /app

# Copier les fichiers nécessaires pour le build
COPY build.gradle settings.gradle gradlew ./
COPY gradle ./gradle
COPY src ./src

# Rendre le wrapper Gradle exécutable
RUN chmod +x gradlew

# Construire le projet sans lancer les tests
RUN ./gradlew clean bootJar -x test

FROM eclipse-temurin:17-jre
WORKDIR /app

# Copier le jar construit depuis l'étape de build
COPY --from=build /app/build/libs/*.jar app.jar

# Vérifier que le jar est bien présent (optionnel)
RUN ls -l /app

# Exposer le port 9002 (selon ton besoin)
EXPOSE 9002

# Lancer l'application
ENTRYPOINT ["java", "-jar", "app.jar"]
