# 第一步：做一个注册服务器

我们将学习如何搭建一个服务发现注册的服务器，以及如何将该应用包装成一个 Docker 镜像并在容器中运行。


## 技能点

- 搭建一个 Spring Boot 应用
- 搭建一个用于服务发现与注册的 Eureka 服务器
- 构建一个 Eureka 服务器的 Docker 镜像


## 试试看

1. [安装 Docker](https://docs.docker.com/install/)

2. 选择一个项目目录并克隆教程代码

    ```
    git clone git@github.com:xulinhao/tutorial-spring-cloud.git
    ```

3. 创建一个分支 `B0`

    ```
    cd tutorial-spring-cloud
    git checkout v0.1.0 -b B0
    ```

4. 运行并用浏览器打开注册服务的主页 [http://localhost:8761](http://localhost:8761)

    ```
    mvn spring-boot:run
    ```
    
5. 制作一个 Docker 镜像
    > 先停止已运行的应用，再运行该镜像应用

    ```
    mvn clean install
    docker images
    docker run -p 8761:8761 -t tutorial-spring-cloud/eureka:0.1
    docker ps
    ```


## 深入代码

### 项目结构

这是一个 Maven 多模块项目，项目结构如下：

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

### 做一个注册服务器

1. 添加 Spring Boot 和 Spring Cloud 的依赖：[tutorial-spring-cloud/pom.xml](https://github.com/xulinhao/tutorial-spring-cloud/blob/master/pom.xml#L18-L34)

2. 添加 Eureka 服务依赖 `spring-cloud-starter-eureka-server`：[ops/eureka/pom.xml](https://github.com/xulinhao/tutorial-spring-cloud/blob/master/ops/eureka/pom.xml#L17-L22)

3. 创建带有 `@SpringBootApplication` 和 `@EnableEurekaServer` 标注的 [`Application`](https://github.com/xulinhao/tutorial-spring-cloud/blob/master/ops/eureka/src/main/java/com/hypers/ops/eureka/Application.java) 

4. 添加应用配置 [`application.yml`](https://github.com/xulinhao/tutorial-spring-cloud/blob/master/ops/eureka/src/main/resources/application.yml)

5. 运行你的第一个 Eureka 服务器：`mvn spring-boot:run`

### 容器化应用

1. 添加一个用于构建 Docker 镜像的插件 `dockerfile-maven-plugin`：[ops/eureka/pom.xml](https://github.com/xulinhao/tutorial-spring-cloud/blob/master/ops/eureka/pom.xml#L30-L49)

2. 添加一个镜像构建文件 [`Dockerfile`](https://github.com/xulinhao/tutorial-spring-cloud/blob/master/ops/eureka/Dockerfile)

3. 运行命令来构建镜像：`mvn clean install` 或 `mvn package`

4. 检查容器是否运行成功：`docker images`


## 进一步阅读

1. [Service Discovery: Eureka Server](http://cloud.spring.io/spring-cloud-static/Edgware.SR2/multi/multi_spring-cloud-eureka-server.html)
2. [Spring Boot with Docker](https://spring.io/guides/gs/spring-boot-docker/)


## 下一步

在本教程的[第二部分](part-2-cn.md)中，我们将添加三个基本服务并研究如何通过 Eureka，Ribbon，Feign 和 Zuul 
实现服务的发现、注册与访问。