FROM openjdk:8-jdk-alpine
MAINTAINER XU Linhao <xulinhao1976@qq.com>

RUN apk add --no-cache curl

VOLUME /tmp

ARG JAR_FILE
ADD ${JAR_FILE} /opt/eureka.jar

ENTRYPOINT ["/usr/bin/java", "-jar", "/opt/eureka.jar"]
