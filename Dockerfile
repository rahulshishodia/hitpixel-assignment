FROM openjdk:21-slim

WORKDIR /app

COPY . ./
RUN ./mvnw clean install -DskipTests

EXPOSE 8080

CMD java -jar target/*.jar
