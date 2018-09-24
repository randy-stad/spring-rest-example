spring-rest-example
==============================================

A simple example using Spring Boot to build a REST server.  The example uses an in memory database, data is not persisted.

## Building

To build the example, you will need to install:

* Java 10 JDK (minimum)
* Maven3

Once installed build the example:

```
$ ./mvn package
```

The standard Maven stages are supported, please see the [Maven](https://maven.apache.org) documentation.  The package stage will compile and test the example.  As a final step it will package the server in a JAR file.

## Running

To run the example REST server:

```
$ java -jar ./target/api-1.0.0.jar 
```

If you want to pre-load the example database, pass a JSON document as an argument to the command.  A sample database is found in the test resources directory:

```
$ java -jar ./target/api-1.0.0.jar ./src/test/resources/database.json
```

## API Documentation

The example includes [Swagger](https://swagger.io) API documentation support along with the simple to use Swagger UI.  The UI is useful for executing the API if you don't want to use the curl examples below.

To display the json Swagger document, navigate to ```http://localhost:8080/v2/api-docs```.  To use the Swagger UI to call the API, navigate to ```http://localhost:8080/swagger-ui.html```

## Using

Use [curl](https://curl.haxx.se) or you favorite tool to call the REST server.  The server exposes the ```/employees``` object and allows you to perform the normal CRUD (create, read, update, delete) operations on the object.  In addition, like a proper RESTful service, you can get a list of all employees in the database.

Once the server is started, and if you are using the sample database, to get a list of all the employees, use this curl command:

```
curl http://localhost:8080/employees
```

The result is displayed and lists the employees in the database.

To get a single employee by id (replace {id} with the identifier of the employee)

```
curl http://localhost:8080/employees/{id}
```

To create a new employee, you perform a POST operation on the ```/employees``` object.  Note that you will need to specify the Content-Type of the data, the example service accepts JSON.  A sample new employee is found in the test resources directory:

```
curl -X POST -d @./src/test/resources/sally.json -H "Content-Type: application/json" http://localhost:8080/employees
```

As is expected, the POST returns the new employee result.

To update an employee, you send a PUT request specifying the employee id in the URL.  A sample update document is found in the test resources directory.  To update employee 2:

```
curl -X PUT -d @./src/test/resources/randy-update.json -H "Content-Type: application/json" http://localhost:8080/employees/2
```

To delete an employee, you send a DELETE request specifying the employee id in the URL.  This example secures the delete operation and you must provide a username and password before you can delete an employee.  If you do not provide the username and password, the server responds with a HTTP status code of 401 (Unauthorized).  Authorization is implemented with the HTTP Basic authentication scheme, you should NEVER use this in a production environment.

To provide the username and password simply add them to the url (username is user1, password is password1):

```
curl -X DELETE http://user1:password1@localhost:8080/employees/2
```

If you attempt to retrieve employee 2, the server will respond with a 404 (Not Found) HTTP status.
