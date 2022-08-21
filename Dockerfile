FROM openjdk:8
COPY target/auditAuth.jar auditAuth.jar
EXPOSE 8400
ENTRYPOINT ["java","-jar","/auditAuth.jar"]