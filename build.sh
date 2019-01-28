#!/bin/bash

mvn clean install

docker build -f Dockerfile1 -t rest_server .
docker build -f Dockerfile2 -t rest_client .

docker-compose up