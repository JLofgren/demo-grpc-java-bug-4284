# Demo of grpc-java large header bug #4284

This repo provides a minimal demonstration of
[grpc-java issue #4284](https://github.com/grpc/grpc-java/issues/4284).

This demo uses grpc's HelloWorld examples as a base. To reproduce the issue,
you need to run a non-java server (I use python) with a java client. The
java example client was modified to send a large header along with its request.
This is sufficient to reproduce the error as long as the client is talking to
a non-java server.

## To reproduce

1. Start the python example server as directed in the python folder's README.

2. Build and run the java client as directed in the java folder's README.
