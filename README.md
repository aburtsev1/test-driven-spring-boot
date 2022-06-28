# Library application

![CI status](https://github.com/xpinjection/test-driven-spring-boot/actions/workflows/maven.yml/badge.svg)

Sample Spring Boot cloud-native application written in TDD style.

## Local run (The easiest way)

The easiest way to run application locally is to use _LocalLibraryApplication_ class. It will run all needed dependencies in the Docker containers and configure application to use them automatically.

Example: 
1. Install Docker on your machine https://docs.docker.com/get-docker/ 
2. Set username and password for security in application-admin.yaml in security section (name=admin ; password=xpinjection)
3. Create application.properties in resources folder. Put next properties in application.properties: 
spring.profiles.active=dev,admin
4. Build project
5. Run  main method in  _LocalLibraryApplication_ class 
6. Open http://localhost:8080

## Local run (The hardest way)

The hardest way to run application locally =)  is just run _LibraryApplication_ class with following preconditions

Example: 
1. Install postgres database locally from https://www.postgresql.org/ 
2. Create database with name = 'library' , to do this run command: 
'createdb -U <username> library'
3. Set db admin username and password in application.yaml in datasource section. 
4. Set username and password for security in application-admin.yaml in security section
5. Create application.properties in resources folder. Put next properties in application.properties: 
spring.profiles.active=dev,admin
6. Build project
7. Run  main method in  _LocalLibraryApplication_ class 
8. Open http://localhost:8080
 

## Configuration

To enable Actuator endpoints admin profile has to be activated with proper secrets provided via system properties or other suitable external configuration option:

> _-Dspring.security.user.name=admin -Dspring.security.user.password=xpinjection -Dspring.profiles.active=dev,admin_
  
  Example: 
  1. Run app with properties locally
  2. Open url to check all actuator endpoints http://localhost:8080/admin 
  3. user = admin , password = xpinjection

## Consumer-driven contracts

[Pact](https://docs.pact.io/) is used to describe and manage REST API contracts.

Sample instance of the Pact Broker in Docker could be started with `/pact/docker-compose.yaml`. It starts the Pact Broker and the PostgreSQL database as dependency. Data is not stored on volumes, so will be cleaned after restart.

Pact integration could be enabled during tests execution with `pactbroker.enabled` system property.

Use system variable `pact.verifier.publishResults` to control pact verification results publishing to the Pact Broker. Pact Broker configuration is located in `pom.xml` and could be overridden with system properties as well.

System properties `pact.provider.version` and `pact.provider.branch` should be used to pass correct version of the application and git branch for tracking in the Pact Broker.
