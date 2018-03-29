#!/bin/bash

# Remove all <none> tag images
docker rmi $(docker images | grep '^<none>' | awk '{print $3}')

# Remove all images whose name prefix is "tutorial-spring-cloud"
docker rmi $(docker images | grep 'tutorial-spring-cloud*' | awk '{print $3}')

# Build all images
mvn clean install