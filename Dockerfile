FROM openjdk:20
EXPOSE 8080
ADD target/HW_8_1_Spring_Boot-0.0.1-SNAPSHOT.jar myapp.jar
ENTRYPOINT ["java","-jar","/myapp.jar"]