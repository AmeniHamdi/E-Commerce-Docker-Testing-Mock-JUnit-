FROM openjdk:8-jdk-alpine
EXPOSE 8089
ADD target/tpAchatProject*.jar tpAchatProject-1.0.jar 
ENTRYPOINT ["java","-jar","/tpAchatProject-1.0.jar"]

