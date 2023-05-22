FROM openjdk:17
ADD target/SpringBoot-WebFluxCrudWithMongoReactiveDB-0.0.1-SNAPSHOT.jar cruddemo.jar
ENTRYPOINT ["java" ,"-jar", "cruddemo.jar"]