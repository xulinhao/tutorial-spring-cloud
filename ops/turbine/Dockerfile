FROM openjdk:8-jdk-alpine
MAINTAINER XU Linhao <xulinhao1976@qq.com>

VOLUME /tmp

ARG JAR_FILE
ADD ${JAR_FILE} /opt/turbine.jar

ENTRYPOINT ["/usr/bin/java", "-jar", "/opt/turbine.jar"]
