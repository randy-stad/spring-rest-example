spring-rest-example
==============================================

A simple example using Spring Boot to build a REST server.

To build the example, you will need to install:

* Java 10 JDK (minimum)
* Maven3

Once installed build the example:

```
$ ./mvn package
```

This builds and tests the example and will create a JAR file.  To run the example REST server:

```
$ java -jar ./target/api-1.0.0.jar 
```

If you want to pre-load the example database, pass a JSON document as an argument to the command.  A sample database is found in the test resources directory:

```
$ java -jar ./target/api-1.0.0.jar ./src/test/resources/database.json
```
