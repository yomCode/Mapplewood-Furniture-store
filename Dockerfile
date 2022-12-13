FROM openjdk:11
ADD ./target/OakLand-v1-be-0.0.1-SNAPSHOT.jar OakLand-v1-be-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "OakLand-v1-be-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080