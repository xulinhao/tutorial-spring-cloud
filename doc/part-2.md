# Part 2: Using Eureka, Ribbon, Feign and Zuul

In this tutorial, we will learn how to register a microservice to Eureka 
Server and how to use Feign or RestTemplate to access service via Zuul.


## Skill

- Making a multi-module Maven project
- Discovering and registering microservice to Eureka Server
- Using Feign or RestTemplate to access services
- Using Zuul as gateway for service access
- Using docker-compose to run multiple containers
- Using H2 and JPA for data access


## Try-It

### Run 1-1

1. Choose a directory and clone the project

    ```
    git clone git@github.com:xulinhao/tutorial-spring-cloud.git
    ```

2. Make a branch

    ```
    cd tutorial-spring-cloud
    git checkout v0.2.0 -b B2
    ```

3. Run and open [http://localhost:8761](http://localhost:8761)

    ```
    # run each command in a dedicated shell window
    cd ops/eureka; mvn spring-boot:run
    cd ops/zuul; mvn spring-boot:run
    cd core/movie; mvn spring-boot:run
    cd core/cast; mvn spring-boot:run
    cd core/rate; mvn spring-boot:run
    cd composite/movie-composite; mvn spring-boot:run
    cd composite/movie-api; mvn spring-boot:run
    ```
    
4. Query a movie in your browser [localhost:8762/api/movie/1](localhost:8762/api/movie/1)
    ```
    # by curl
    curl localhost:8762/api/movie/1
    ```

5. Query a service by passing around Zuul
    ```
    curl localhost:8761/eureka/apps
    
    # assume the movie service's port is 54321
    curl localhost:54321/movie/1
    ```

6. See what returns if querying a non-expoosed service by Zuul
    ```
    curl localhost:8762/api/v1/movie/1
    ```

### Run by docker-compose

1. Build all images

    ```
    ./bin/build-all.sh
    
    # check if all images are built successfuly
    docker images
    ```

2. Run all images

    ```
    docker-compose up -d
    
    # check if all containers are started successfully
    docker-compose ps
    ```

3. Scale the number of services
    ```
    docker-compose scale movie=3
    ```

4. What if we start another movice service by `mvn spring-boot:run`?


## In-depth Study

### Project Structure

```
.
├── bin
├── composite
│   ├── movie-api
│   │   ├── Dockerfile
│   │   ├── movie-api.iml
│   │   ├── pom.xml
│   │   └── src
│   └── movie-composite
├── core
│   ├── cast
│   ├── movie
│   └── rate
├── ops
│   ├── eureka
│   └── zuul
├── docker-compose.yml
└── pom.xml
```

### Components

- Eureka
- Zuul
- Movie, Cast and Rate
- Movie-Composite
- Movie-API

### Troubleshooting

#### Packaging and Reference

**Problem description**: the `movie-composite` module depends on `movie`, 
`cast` and `rate` modules; however, if not use `repackage` goal then we will have artifact 
dependency problem (e.g., ClassNotFoundException) when running movie-composite service.
 
**Solution**: using `repackage` goal

#### Database Initilization

**Problem description**: if use `resources/data.sql` to initialize database 
for movie, cast and rate, then we will have problem of initializing database by 
`resources/data.sql` file when starting `movie-composite` service.

**Solution**: not use `resources/data.sql` way to initialize database


## Further Reading

1. [Spring Cloud - Bootstrapping](http://www.baeldung.com/spring-cloud-bootstrapping)
2. [Router and Filter: Zuul](http://cloud.spring.io/spring-cloud-static/spring-cloud-netflix/1.4.4.RELEASE/single/spring-cloud-netflix.html#_router_and_filter_zuul)
3. [Declarative REST Client: Feign](http://cloud.spring.io/spring-cloud-static/spring-cloud-netflix/1.4.4.RELEASE/single/spring-cloud-netflix.html#spring-cloud-feign)
4. [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)


## Next Step

In the [next tutorial section](part-3.md) in the [Tutorial Series](../README
.md), we will see how to use Circuit Breaker to deal with failed service 
calls and how Hytrix and Turbine can help monitor the health status of services.
