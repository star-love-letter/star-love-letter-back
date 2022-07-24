FROM openjdk:11

COPY *.jar /app.jar

EXPOSE 8089

ENTRYPOINT ["java","-jar","/app.jar"]