FROM openjdk:17
ADD /target/task_managment.jar task_managment.jar
ENTRYPOINT ["java", "-jar", "task_managment.jar"]
