FROM openjdk:17-jdk-slim

WORKDIR /app

COPY . .

RUN apt-get update && apt-get install -y maven

RUN mvn clean package -DskipTests

CMD ["java", "-jar", "target/JobPortfolio-0.0.1-SNAPSHOT.jar"]