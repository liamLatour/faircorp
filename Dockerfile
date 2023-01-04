FROM openjdk:17-alpine

ENV PORT=8080
COPY build/libs/\*.jar app.jar

EXPOSE ${PORT}
ENTRYPOINT java -jar /app.jar --server.port=$PORT