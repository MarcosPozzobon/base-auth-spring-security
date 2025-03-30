FROM openjdk:17-jdk-slim

WORKDIR /app

COPY . .

RUN apt update && apt install -y maven && mvn clean package -DskipTests

RUN cp target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
