FROM ubuntu:latest
LABEL authors="svetl"

ENTRYPOINT ["top", "-b"]

# Используйте официальный образ Gradle в качестве базового
FROM gradle:8.0-jdk21 AS build

# Установите рабочую директорию
WORKDIR /app

# Скопируйте файл build.gradle и загрузите зависимости
COPY build.gradle settings.gradle ./
RUN gradle dependencies --no-daemon

# Скопируйте исходный код
COPY src ./src

# Соберите приложение
RUN gradle build --no-daemon

# Используйте официальный образ OpenJDK для выполнения
FROM openjdk:21-jdk-slim

# Установите рабочую директорию
WORKDIR /app

# Скопируйте собранное приложение из предыдущего этапа
COPY --from=build /app/build/libs/Task-0.0.1-SNAPSHOT-plain.jar app.jar

# Запустите приложение
ENTRYPOINT ["java", "-jar", "app.jar"]