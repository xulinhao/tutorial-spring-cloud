#!/bin/bash

# Remove all images whose name prefix is "tutorial-spring-cloud"
docker images -a | grep "tutorial-spring-cloud*" | awk '{print $3}' | xargs docker rmi

# Build all images
mvn package