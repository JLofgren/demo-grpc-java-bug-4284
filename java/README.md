grpc hello world example
==============================================

This is a stripped down and slightly modified version of the
[java grpc helloworld example](https://github.com/grpc/grpc-java/tree/master/examples).

To build the examples, run in this directory:

```
$ ./gradlew installDist
```

This creates the scripts `hello-world-server` and
`hello-world-client` in the
`build/install/examples/bin/` directory that run the examples. The
example requires the server to be running before starting the client.

First start the server:

```
$ ./build/install/examples/bin/hello-world-server
```

And in a different terminal window run:

```
$ ./build/install/examples/bin/hello-world-client
```

The client script takes an optional argument of the port number to connect to.
The default port is 50051. To connect to a different port:

```
$ ./build/install/examples/bin/hello-world-client 40051
```
