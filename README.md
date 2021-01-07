# Spring boot playground

[![CircleCI](https://circleci.com/gh/alkettarko/spring-boot-playground.svg?style=shield&circle-token=8747c78c9c097141f1c52a2efcd91d7aa3cc6f5e)](https://github.com/alkettarko/spring-boot-playground)
[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](https://opensource.org/licenses/MIT)

A playground project for spring boot world

### Pre-requisites

* Java 11
* Maven 3.6.1

### Used technologies
* H2 in memory database
* Mapstruct 
* Lombok
* JPA/Hibernate
* Javax validation
* Junit 5
* Swagger 2

### Package and run the project
``` 
 mvn clean install
 mvn spring-boot:run
```
### Running the tests
``` 
 mvn test
```

 Check out API-s documentation:
 http://localhost:8084/swagger-ui.html
