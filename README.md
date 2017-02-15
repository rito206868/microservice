# microservice
Camel Proxy Services

This service is a demo to show how you can create a proxy using Apache Camel and Spring Boot between client and any 3rd part API.

The API/URI detail is given in the application.properties file.

Right now its designed to call emaployee-detail-master service which can be further modified to any other service.

Its a maven project, so build with mvn command and run in local without deplying using mvn spring-boot:run command.

The proxy is accessible on this path http://localhost:9090/employee/{id}, here 9090 is the default port specified 
and host also is default to localhost(0.0.0.0)

Please contact r.a.chakraborty@accenture.com for further detail.
