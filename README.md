# Demo of grpc-java large header bug #4284

This repo provides a minimal demonstration of
[grpc-java issue #4284](https://github.com/grpc/grpc-java/issues/4284) and
[netty issue #7825](https://github.com/netty/netty/issues/7825).

To reproduce the issue, you need to run an Envoy proxy between a grpc server
and a java client.

This demo uses grpc's HelloWorld examples as a base, with these notable
changes:

1. The example server was modified to advertise a large maximum accepted
   header size (100kB).
2. The example client was modified to send a 9kB header - larger than the
   default max size of 8kB, but smaller than the max advertised by the server.
3. An envoy proxy server is set up to act as a middle man between the server
   and client. The envoy server advertises no max header size, which according
   to the HTTP/2 spec means that the server is willing to accept arbitrarily
   long headers.

## Setup

1. Build the java server and client as directed in the java folder's README.

2. Install Docker as directed in the envoy folder's README. 

## To reproduce the bug:

1. Start the envoy proxy server in one terminal. The proxy listens on port
   40051 and redirects to port 50051:

    ```
    envoy/envoy.sh
    ```
    
2. Start the java server in another terminal. The server listens on port 50051:

    ```
    java/build/install/examples/bin/hello-world-server
    ```

3. Run the java client in a third terminal, pointing it to the proxy port:

    ```
    java/build/install/examples/bin/hello-world-client 40051
    ```
    
You should see error:

```
WARNING: RPC failed: Status{code=INTERNAL, description=http2 exception, cause=io.netty.handler.codec.http2.Http2Exception$HeaderListSizeException: Header size exceeded max allowed size (8192)
...
```
    
## To show that the error does not occur on a direct client->server connection:

1. Start the java server in one terminal:

    ```
    java/build/install/examples/bin/hello-world-server
    ```
    
2. Run the java client in another terminal, pointing it to the server port:

    ```
    java/build/install/examples/bin/hello-world-client 50051
    ```

You should see successful INFO logs from both the client and server.

## Python client doesn't have the error:

For good measure you can see that a python client does not have the same error.

1. Run the python client. It should work for either port:

    ```
    python python/greeter_client.py 40051
    python python/greeter_client.py 50051
    ```