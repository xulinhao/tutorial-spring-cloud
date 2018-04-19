# Part 3: Trying out Circuit Breaker, Hytrix and Turbine

In this tutorial, we will learn how to enable a [Circuit Breaker](https://martinfowler.com/bliki/CircuitBreaker.html) that is
very important to a microservice application, when some services have failed 
or responsed slowly due to some hardware issue; we also show how to monitor 
and display the service heath status by [Hystrix and Turbine](https://medium.com/netflix-techblog/hystrix-dashboard-turbine-stream-aggregator-60985a2e51df).


## Skill

- Using Circuit Breaker
- Monitoring service heath status by Hystrix and Turbine
- Customzing the docker image build of a service


## Try-It

### Run 1-1

1. Choose a directory and clone the project

    ```
    git clone git@github.com:xulinhao/tutorial-spring-cloud.git
    ```

2. Make a branch

    ```
    cd tutorial-spring-cloud
    git checkout v0.3.0 -b B3
    ```

3. Run and open [http://localhost:8761](http://localhost:8761)

    ```
    # run each command in a dedicated shell window
    cd ops/eureka; mvn spring-boot:run
    cd ops/hystrix; mvn spring-boot:run
    cd ops/turbine; mvn spring-boot:run
    cd ops/zuul; mvn spring-boot:run
    cd core/movie; mvn spring-boot:run
    cd core/cast; mvn spring-boot:run
    cd core/rate; mvn spring-boot:run
    cd composite/movie-composite; mvn spring-boot:run
    cd composite/movie-api; mvn spring-boot:run
    ```
    
4. Open RabbitMQ [localhost:15672/](localhost:15672/), login with 
`guest:guest` and check if a default channel `springCloudHystrixStream` exists

5. Open Hystrix dashboard [localhost:7979/hystrix](localhost:7979/hystrix) 
and then input the link [localhost:8989](localhost:8989), to check if a hystrix 
stream is coming?
    ```
    # See what you will get?
    curl http://localhost:8989/
    ```

6. Trigger the Hystrix service and then retry the step 5!
    ```
    # by curl
    curl localhost:8762/api/movie/1
    ```

7. Retry the step 4 and see what your will get?

8. Stop the `cast` service and query a movie to see if the circuit breaker 
will be opened?
    ```
    curl localhost:8080/api/v1/movie/1
    ```

9. Stress a movie query massively and then see what you will get?
    ```
    ab -n 50 -c 5 localhost:8080/api/v1/movie/1
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

3. Follow the steps in the `Run 1-1` section and see what you will get?
    ```
    # NOTE: for the performance issue on a single machine,
    #       the docker-compose.yml is changed slightly where
    #       the services api and zuul are disabled; and please
    #       try to access the movie-composite service directly
    curl localhost:8080/movie/1
    ```

4. What is going wrong? Try [turbine:8989](turbine:8989) in Hystrix Dashboard?


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
│   ├── hystrix
│   ├── turbine
│   └── zuul
├── docker-compose.yml
└── pom.xml
```

### Components

- Hystrix
- Hystrix Dashboard
- Turbine

### Troubleshooting

#### Dashboard + Turbine for multiple services on the same server

**Solution**: [https://github.com/Netflix/Hystrix/issues/117](https://github.com/Netflix/Hystrix/issues/117)

#### Dashboard + Turbine in Docker

**Solution**: (1) using application-docker.yml in movie-composite and 
turbine; (2) add environment variable in `docker-compose.yml`


## Further Reading

1. [hystrix-metrics-event-stream](https://github.com/Netflix/Hystrix/tree/master/hystrix-contrib/hystrix-metrics-event-stream)
2. [all-and-sundry](http://www.java-allandsundry.com/2016/05/spring-cloud-with-turbine-amqp.html)


## Next Step

In the [next tutorial section](part-4.md) in the [Tutorial Series](../README
.md), we will see how to use Zipkin to trace service calls.
