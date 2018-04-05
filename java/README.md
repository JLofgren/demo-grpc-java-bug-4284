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
`build/install/examples/bin/` directory that run the examples. Each
example requires the server to be running before starting the client.

For example, to try the hello world example first run:

```
$ ./build/install/examples/bin/hello-world-server
```

And in a different terminal window run:

```
$ ./build/install/examples/bin/hello-world-client
```
