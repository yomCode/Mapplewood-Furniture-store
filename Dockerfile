FROM openjdk:11
ADD ../target/oakLand-v1-be.jar oakLand-v1-be.jar
ENTRYPOINT ["java","-jar", "oakLand-v1-be.jar"]
EXPOSE 8080