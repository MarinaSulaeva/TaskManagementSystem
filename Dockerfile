FROM openjdk:17
#ADD /target/TaskManagementSystem-0.0.1-SNAPSHOT.jar backend.jar
ADD backend.jar backend.jar
ENTRYPOINT ["java", "-jar", "backend.jar"]
