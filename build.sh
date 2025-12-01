#!/bin/bash
cd users
./mvnw clean package install -DskipTests

cd ../email
./mvnw clean package install -DskipTests

cd ../auth
./mvnw clean package install -DskipTests

cd..
docker compose up -d --build
