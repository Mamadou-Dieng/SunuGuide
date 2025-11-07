# --- ÉTAPE 1 : LA CONSTRUCTION (BUILD STAGE) ---
FROM maven:3.9.5-eclipse-temurin-17 AS build

# Crée un répertoire de travail
WORKDIR /app

# Copie les fichiers de configuration du projet (pom.xml)
COPY pom.xml .

# Copie le code source de l'application
COPY src ./src

# Construit l'application et génère le JAR (ajuster le goal si vous utilisez Gradle)
RUN mvn clean package -DskipTests

# --- ÉTAPE 2 : L'EXÉCUTION (RUN STAGE) ---
# Utilise une image Java JRE/JDK plus légère pour l'exécution
FROM eclipse-temurin:17-jre-alpine

# Définit le répertoire de travail
WORKDIR /app

# Copie le fichier JAR de l'étape de construction vers l'étape d'exécution
# Le nom du JAR doit correspondre à celui généré par votre build (ex: nom-du-projet-0.0.1-SNAPSHOT.jar)
COPY --from=build /app/target/*.jar app.jar

# Expose le port par défaut de Spring Boot
EXPOSE 8080

# Définit la commande pour démarrer l'application
ENTRYPOINT ["java", "-jar", "app.jar"]