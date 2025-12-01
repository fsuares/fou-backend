@echo off

cd users
call mvnw.cmd clean package install -DskipTests

cd ..\email
call mvnw.cmd clean package install -DskipTests

cd ..\auth
call mvnw.cmd clean package install -DskipTests

cd ..
docker compose up -d --build

pause
