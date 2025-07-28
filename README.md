# 🚀 Ecommerce app

A brief description of what this project does and who it's for.

---

## 📦 Features

- ✅ Containerized with Docker and Docker Compose
- ✅ PostgreSQL as database
- ✅ pgAdmin for database UI
- ✅ Persistent data storage using Docker volumes
- ✅ Easily configurable with environment variables
- ✅ Optional: seed SQL script support

---

## 🛠️ Tech Stack

_- **Backend**: [Node.js / Java / Python / etc.]
- **Database**: PostgreSQL
- **Tools**: Docker, Docker Compose, pgAdmin

---

## 📂 DB SETUP with containers. 
Run => docker compose up -d. 
It will automatically run container properties specified in that file. docker-compose.yaml file..



### Different Services postgres and pgadmin docker with network to communicate
docker run -d \
--name postgres_container \
-e POSTGRES_USER=embarkx \
-e POSTGRES_PASSWORD=embarkx \
-e PGDATA=/data/postgres \
-v postgres:/data/postgres \
-p 5432:5432 \
--network postgres \
--restart unless-stopped \
postgres


docker run -d \
--name pgadmin_container \
-e PGADMIN_DEFAULT_EMAIL=pgadmin4@pgadmin.org \
-e PGADMIN_DEFAULT_PASSWORD=admin \
-e PGADMIN_CONFIG_SERVER_MODE=False \
-v pgadmin:/var/lib/pgadmin \
-p 5050:80 \
--network postgres \
--restart unless-stopped \
dpage/pgadmin4
