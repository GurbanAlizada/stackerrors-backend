FROM openjdk:11
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE}  breynli-backend-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java" , "-jar" , "/breynli-backend-0.0.1-SNAPSHOT.jar"]

