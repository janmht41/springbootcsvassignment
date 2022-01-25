FROM openjdk:11
ADD build/libs/assignment2-0.0.1-SNAPSHOT.jar trial.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "trial.jar"]
