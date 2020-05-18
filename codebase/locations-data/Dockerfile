FROM openjdk:14-alpine
RUN mkdir /app
ADD ./target/locations-data*.jar /app/application.jar
ENTRYPOINT ["java", "-jar", "/app/application.jar"]