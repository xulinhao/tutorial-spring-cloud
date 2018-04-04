#!/bin/bash

# Stop all containers
docker stop $(docker ps -a -q)

# Remove all exited containers
docker rm $(docker ps -a -f status=exited -q)