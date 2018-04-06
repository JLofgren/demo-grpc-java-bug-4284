# Envoy http/2 proxy

Start an [Envoy](https://www.envoyproxy.io) proxy that will forward GRPC
requests from port 40051 to port 50051.  

This is based on the
[envoy simple and grpc bridge configs](https://www.envoyproxy.io/docs/envoy/latest/start/start).
 
## Prerequisites

1. Install docker
2. (Optional) enable docker administration 
   [from your user account](https://docs.docker.com/install/linux/linux-postinstall/).

## Run

    ./envoy.sh
 