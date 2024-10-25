FROM openjdk:17
ADD /target/TaskManagementSystem-0.0.1-SNAPSHOT.jar task_managment.jar
ENTRYPOINT ["java", "-jar", "task_managment.jar"]
