#!/bin/bash

docker rmi -f fortiproxy:1
docker build -t fortiproxy:1 .
