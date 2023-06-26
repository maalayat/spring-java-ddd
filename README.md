# spring-java-ddd ☕

Domain-Driven Design course accompaniment project

- Spring Boot
- Gradle

## Build
Build the project for the first time: `./gradlew build`

## Test
1. Firsts you need a mysql instance, you can use docker:

`docker run --name [YOUR_MYSQL_NAME] -p3306:3306 -e MYSQL_ROOT_PASSWORD=[your_password] -d mysql:8.0.31`

2. Update your mysql configuration in `application.properties`
3. Run `./gradlew test`

## Check

- Create [the project JAR](https://docs.gradle.org/current/userguide/java_plugin.html#sec:jar): `make build`
- Run the tests and plugins verification tasks: `make test`
