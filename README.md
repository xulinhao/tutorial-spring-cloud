# A Simple Tutorial of Spring Cloud

A tutorial shows how to make a [Microservice](https://github.com/xulinhao/study-resource/blob/master/devops/MS.md) application with 
[Spring Cloud](https://projects.spring.io/spring-cloud/) step-by-step.

Well, let us make a movie application that is composed of three services: 
movie, cast and rate. For the sake of microservice, we assume each service 
has its own database and when a user looks for the detailed information of a 
movie, then the results of all three services will be combined and returned 
finally. 

The landscape of the whole system is displayed bellow and all 
Spring Cloud components we used are also included. In this tutorial, we will
 add all these components one-by-one to achieve our final goal: a complete 
microservice application.

![system architecture](doc/system.png)

Notice that four things are used to guide our understanding on each tutorial 
section: skill, try-it, in-depth study and further reading.

Enjoy your study!


## [Part 1: Dockerizing a Eureka Server](doc/part-1.md)

## [Part 2: Using Eureka, Ribbon, Feign and Zuul](doc/part-2.md)

## Part 3: Trying out Circuit Breaker, Hytrix and Turbine

## Part 4: Adding a Config Server

## Part 5: Tracing with Zipkin and Sleuth

## Part 6: Logging with ELK