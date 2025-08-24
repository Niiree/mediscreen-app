# Mediscreen (branch `develop`)

Plateforme composée de **microservices** pour gérer les **patients**, leur **historique de notes**, et évaluer le **risque de diabète**. L’ensemble est orchestré avec **Docker Compose**, base principale **PostgreSQL** (et **MongoDB** recommandé pour les notes).

## 📦 Services
- `microservice-patient` — gestion des patients 
- `microservice-note` — gestion des notes cliniques par patient.
- `microservice-diabete` — calcul du risque diabète à partir des notes + âge/genre.
- `microservice-gateway` — point d’entrée (routing, sécurité future).
- `microservice-clientui` — interface Web (consomme la Gateway).

## 🔭 Architecture (simplifiée)
```
[ ClientUI ] → [ Gateway ] → [ Patient (Postgres) ]
                              → [ Note (MongoDB)  ]
                              → [ Diabete (stateless)]
```

## 🚀 Démarrage rapide (Docker)
```bash
docker compose up --build
```
> Compose démarre Postgres → (optionnel: Flyway) → services. Vérifiez les mappings de ports selon votre `docker-compose.yml`.

## ⚙️ Configuration (exemples)
- **Patient** (Postgres) : `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`, `SPRING_DATASOURCE_PASSWORD`, `SPRING_JPA_HIBERNATE_DDL_AUTO=none`
- **Note** (MongoDB) : `SPRING_DATA_MONGODB_URI`
- **Gateway/UI** : `SERVER_PORT`, CORS, URLs des backends

## 🧪 Qualité & tests (reco)
- Unitaires (règles diabète), MVC (controllers), Repos (Testcontainers Postgres/Mongo), **contract tests** (stabiliser JSON entre UI ↔ services)
- Données seed pour e2e/démo

## 🏗️ Développement local (hors Docker)
Prérequis : JDK 17+, Postgres en local et Mongo.

