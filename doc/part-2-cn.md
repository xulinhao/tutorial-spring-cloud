# 第二步: 使用服务注册访问和负载均衡

在这部分教程中，我们将学会如何完成一个完整的服务发现与注册，如何实现服务组合，以及如何对外部用户提供服务访问接口。


## 技能点

- 搭建一个多模块项目
- 发现与注册微服务
- 内部服务调用
- 外部服务访问
- 采用容器运行多种服务实例
- 使用 JPA 进行数据访问


## 试试看

### 普通运行模式

1. 选择一个项目目录并克隆项目

    ```
    git clone git@github.com:xulinhao/tutorial-spring-cloud.git
    ```

2. 创建工作分支 `B2`

    ```
    cd tutorial-spring-cloud
    git checkout v0.2.0 -b B2
    ```

3. 在不同命令窗口中运行以下命令并用浏览器打开 [http://localhost:8761](http://localhost:8761)

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
    
4. 在浏览器中查询一个电影信息 [localhost:8762/api/movie/1](localhost:8762/api/movie/1)
    ```
    # by curl
    curl localhost:8762/api/movie/1
    ```

5. 获取所有发现并注册的微服务，并从内部进行服务的访问
    ```
    curl localhost:8761/eureka/apps
    
    # assume the movie service's port is 54321
    curl localhost:54321/movie/1
    ```

6. 试试看从网关访问一个不对外暴露的服务，系统会返回什么信息？
    ```
    curl localhost:8762/api/v1/movie/1
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

3. 增加服务实例
    ```
    docker-compose scale movie=3
    ```

4. 如果将`普通运行模式`和`容器运行模式`相混合在一起，系统会是什么样子的？


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
│   └── zuul
├── docker-compose.yml
└── pom.xml
```

### 组件

现在让我们依次阅读所有组件的源代码和相关的配置：

> 需要关注点包括（1）Application.java; (2) resources/application.yml; (3) pom.xml; and
 (4) docker-compose.yml

- Eureka
- Zuul
- Movie, Cast and Rate
- Movie-Composite
- Movie-API

### 坑们

#### 打包与依赖引用

**问题描述**: `movie-composite` 模块依赖 `movie`, 
`cast` 和 `rate` 三个模块; 如果没有使用 `repackage`，当我们运行 `movie-composite` 服务时就会遇到 
`ClassNotFoundException` 错误信息。 

**解决方法**: 使用 `repackage` 构建模块并生成 `jar` 包

#### 数据库初始化

**问题描述**: 如果通过 `resources/data.sql` 进行数据库初始化，那么在运行 `movie-composite` 
服务时，我们就会遇到无法初始化数据库的错误提示（由于发现了 `data.sql` 文件）。

**解决方法**: 不使用 `resources/data.sql` 文件进行数据库初始化


## 进一步阅读

1. [Spring Cloud - Bootstrapping](http://www.baeldung.com/spring-cloud-bootstrapping)
2. [Router and Filter: Zuul](http://cloud.spring.io/spring-cloud-static/spring-cloud-netflix/1.4.4.RELEASE/single/spring-cloud-netflix.html#_router_and_filter_zuul)
3. [Declarative REST Client: Feign](http://cloud.spring.io/spring-cloud-static/spring-cloud-netflix/1.4.4.RELEASE/single/spring-cloud-netflix.html#spring-cloud-feign)
4. [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)


## 下一步

在本教程的 [第三部分](part-3.md)，我们将看看如何使用熔断器和服务监控来增进对服务健康状态的了解。

