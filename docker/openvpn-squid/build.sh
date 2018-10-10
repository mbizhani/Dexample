#!/bin/bash

docker rmi -f localproxy:1
docker build -t localproxy:1 .