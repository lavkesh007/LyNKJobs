FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY . .

RUN apt-get update && apt-get install -y maven

RUN mvn clean package -Dmaven.test.skip=true
CMD ["java", "-jar", "target/JobPortfolio-0.0.1-SNAPSHOT.jar"]