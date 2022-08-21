FROM openjdk:8
ADD target/auditAuth-0.0.1-SNAPSHOT.jar auditAuth.jar
EXPOSE 8400
ENTRYPOINT ["java","-jar","/auditAuth.jar"]