# Part 1: Dockerizing a Eureka Server

In this tutorial, we will learn how to make a Eureka Server and how to 
dockerize the Eureka Server application.


## Skill

- Making a Spring Boot application
- Making a Eureka Server which is the anchor point of all microservices.
- Dockerizing the Eureka Server


## Try-It

1. [Install Docker](https://docs.docker.com/install/)

2. Choose a directory and clone the project

    ```
    git clone git@github.com:xulinhao/tutorial-spring-cloud.git
    ```

3. Make a branch

    ```
    cd tutorial-spring-cloud
    git checkout v0.1.0 -b B1
    ```

4. Run and open [http://localhost:8761](http://localhost:8761)

    ```
    mvn spring-boot:run
    ```
    
5. Make and run a docker image
    > make sure to stop your running Eureka Server first

    ```
    mvn clean install
    docker images
    docker run -p 8761:8761 -t tutorial-spring-cloud/eureka:0.1
    docker ps
    ```


## In-depth Study

### Project Structure

This is a Maven multi-module project.

```
tutorial-spring-cloud/
├── ops
│   └── eureka
│       ├── Dockerfile
│       ├── pom.xml
│       └── src
│           └── main
│               ├── java
│               │   └── com
│               │       └── hypers
│               │           └── ops
│               │               └── eureka
│               │                   └── Application.java
│               └── resources
│                   └── application.yml
└── pom.xml
```

### Make a Eureka Server

1. Add both Spring Boot and Spring Cloud dependencies in 
[tutorial-spring-cloud/pom.xml](https://github.com/xulinhao/tutorial-spring-cloud/blob/master/pom.xml#L18-L34)

2. Add `spring-cloud-starter-eureka-server` in [ops/eureka/pom.xml](https://github.com/xulinhao/tutorial-spring-cloud/blob/master/ops/eureka/pom.xml#L17-L22)

3. Make a [`Application`](https://github.com/xulinhao/tutorial-spring-cloud/blob/master/ops/eureka/src/main/java/com/hypers/ops/eureka/Application.java) class with `@SpringBootApplication` and 
`@EnableEurekaServer`

4. Configure [`application.yml`](https://github.com/xulinhao/tutorial-spring-cloud/blob/master/ops/eureka/src/main/resources/application.yml) to make your Eureka Server as a Standalone mode

5. Now you can run it by `mvn spring-boot:run`

### Dockerize It

1. Add `dockerfile-maven-plugin` plugin in [ops/eureka/pom.xml](https://github.com/xulinhao/tutorial-spring-cloud/blob/master/ops/eureka/pom.xml#L30-L49)

2. Add [`Dockerfile`](https://github.com/xulinhao/tutorial-spring-cloud/blob/master/ops/eureka/Dockerfile)

3. Run `mvn clean install` or `mvn package` to build it

4. Run `docker images` to check if the image


## Further Reading

1. [Service Discovery: Eureka Server](http://cloud.spring.io/spring-cloud-static/Edgware.SR2/multi/multi_spring-cloud-eureka-server.html)
2. [Spring Boot with Docker](https://spring.io/guides/gs/spring-boot-docker/)


## Next Step

In the [next tutorial section](part-2.md) in the [Tutorial Series](../README.md), we will add three core services, study how to register them with 
Eureka and Zuul, and how to access service by Feign.
