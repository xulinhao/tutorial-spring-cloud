# 第三步: 试试熔断器与服务监控

在这部分教程中，我们将学会如何在一个组合服务上增加[熔断器]((https://martinfowler.com/bliki/CircuitBreaker.html))功能，
以及如何[可视化监控信息流]((https://medium.com/netflix-techblog/hystrix-dashboard-turbine-stream-aggregator-60985a2e51df).)来帮助运维人员随时了解服务的健康状态。


## 技能点

- 添加熔断器功能
- 可视化监控服务的健康状态
- 定制化 docker 镜像配置


## 试试看

### 普通运行模式

1. 选择一个项目目录并克隆项目

    ```
    git clone git@github.com:xulinhao/tutorial-spring-cloud.git
    ```

2. 创建工作分支 `B2`

    ```
    cd tutorial-spring-cloud
    git checkout v0.3.0 -b B3
    ```

3. 在不同命令窗口中运行以下命令并用浏览器打开 [http://localhost:8761](http://localhost:8761)

    ```
    # 在不同的命令窗口中运行以下命令
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
    
4. 打开消息队列管理页面 RabbitMQ [localhost:15672/](localhost:15672/), 用 
`guest:guest` 帐号登录后看看是否出现了一个通道（Channel）`springCloudHystrixStream`

5. 打开熔断器监控页面 [localhost:7979/hystrix](localhost:7979/hystrix) 并输入
监控信息流的地址 [localhost:8989](localhost:8989) 看看是否有监控信息展现在监控页面中？
    ```
    # 访问这个链接看看你获得什么信息？
    curl http://localhost:8989/
    ```

6. 查询一个电影信息，再重复第 5 步，看看你能获得什么？
    ```
    # by curl
    curl localhost:8762/api/movie/1
    ```

7. 再重复一下第 4 步，看看结果如何？

8. 停止 `cast` 服务，并再查询一个电影信息，看看熔断器有没有被激活（多查询几次试试）？
    ```
    curl localhost:8080/api/v1/movie/1
    ```

9. 用压力测试工具 `ab` 来激活熔断器，看看监控页面的变化？
    ```
    ab -n 50 -c 5 localhost:8080/api/v1/movie/1
    ```

### 容器运行模式

1. 构建所有服务的镜像

    ```
    ./bin/build-all.sh
    
    # check if all images are built successfuly
    docker images
    ```

2. 通过 `docker-compose` 运行所有服务容器

    ```
    docker-compose up -d
    
    # check if all containers are started successfully
    docker-compose ps
    ```

3. 试试 `Run 1-1` 小节中的步骤，看看你能得到什么结果？
    ```
    # 注意: 由于单机性能问题，在这部分实验中我注释掉了 api 和 zuul 服务；
    #      运行 docker-compose up 命令，执行下面的命令访问电影服务，
    #      看看你能获得什么结果？
    curl localhost:8080/movie/1
    ```

4. 发生了什么事情？为什么无法获取监控信息流？
试试这个监控链接 [turbine:8989](turbine:8989)？


## 深入代码

### 项目结构

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


### 组件

现在让我们依次阅读所有组件的源代码和相关的配置：

> 需要关注点包括（1）Application.java; (2) resources/application.yml; (3) pom.xml; and
 (4) docker-compose.yml

- Hystrix
- Hystrix Dashboard
- Turbine


### 坑们

#### 如何在同一台服务器上监控多个熔断器以及监控服务

**Solution**: [https://github.com/Netflix/Hystrix/issues/117](https://github.com/Netflix/Hystrix/issues/117)

#### 如何在 docker 容器环境下使用熔断器及其监控服务

**Solution**: (1) 在模块 movie-composite 和 turbine 中增加 application-docker.yml 
配置文件；(2) 在 `docker-compose.yml` 中增加相应的环境变量。


## 进一步阅读

1. [hystrix-metrics-event-stream](https://github.com/Netflix/Hystrix/tree/master/hystrix-contrib/hystrix-metrics-event-stream)
2. [all-and-sundry](http://www.java-allandsundry.com/2016/05/spring-cloud-with-turbine-amqp.html)


## 下一步

在本教程的 [第四部分](part-4-cn.md)，我们将看看如何追踪一个服务的全生命周期。

