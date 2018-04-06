#!/usr/bin/env bash

#Run envoy proxy, as configured in envoy.yaml

docker build -t envoy:v1 .
docker run --rm --name envoy --net="host" envoy:v1
