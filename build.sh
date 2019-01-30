#!/bin/bash

mvn clean install

mkdir -p /tmp/logs/rest_logs /tmp/logs/webapp_logs

sudo docker-compose up