FROM openjdk:11
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE}  stackerrors-backend-release-2.1.jar
ENTRYPOINT ["java" , "-jar" , "/stackerrors-backend-release-2.1.jar"]

