# User Service

## Problem Statement

You are working on a project that requires you to create a user service. Implement the required functions that will allow you to sign up a user, log in a user, log out a user and validate token. The user service should be able to store the user data in a database. Also implement logging and monitoring for the user service.

## Requirements
1. Implement a function that will allow a user to sign up. The function should take in the user's name, email and password and store the user data in a database. Password should be hashed using bcrypt before storing it in the database.
2. Implement a function that will allow a user to log in. The function should take in the user's email and password, validate the user's credentials and return a token. The token should be generated using RandomAlphanumericStringGenerator and should be valid for 30 days. If user token is not expired and user is already logged in, return the existing token.
3. Implement a function that will allow a user to log out. The function should take in the user's token and invalidate the token.
4. Implement a function that will allow you to validate a user's token. The function should take in the user's token and return the user's data if the token is valid.
5. Throw appropriate exceptions where necessary.
6. Along with data, return http status codes as well for each API.
7. Handle exceptions using exception handling middleware.
8. Testing ProductService-ConnectingThirdPartyAPI: Generate a token using logIn function and use in ProductService header for authorization. Validate the token by calling validateToken function using restTemplate from ProductService.

#### Logging and Monitoring
1. Add the required dependencies in pom.xml:
   - `spring-boot-starter-actuator` : Provides built-in endpoints for monitoring the application.
   - `micrometer-registry-prometheus` : For monitoring the application using Prometheus. Prometheus is an open-source monitoring and alerting toolkit.
2. Add the following property in application.properties:
   - `management.endpoints.web.exposure.include=*` : To expose all the endpoints.
3. Hit `http://localhost:8080/actuator/` URL to check the available endpoints.
4. Hit `http://localhost:8080/actuator/health` URL to check if the application is up and running.
5. Micrometer will generate some metric logs and feed them to Actuator. Actuator will expose those logs at `http://localhost:8080/actuator/prometheus` endpoint.
