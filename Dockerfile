

FROM openjdk:11
ADD ../target/OakLand-v1-be.jar OakLand-v1-be.jar
ENTRYPOINT ["java","-jar", "OakLand-v1-be.jar"]
EXPOSE 8080