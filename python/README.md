# Python hello world example

This is a slight modification of
[grpc's python helloworld example](https://github.com/grpc/grpc/tree/master/examples/python/helloworld).

## Setup

Create a virtualenv. (I like [pew](https://github.com/berdario/pew) for this.)

    pew new python-grpc-example

Install the requirements in the virtualenv:

    pip install -r requirements.txt

## Run

To run the server:

    python greeter_server.py
    
To run the client (against port 50051):

    python greeter_client.py
    
An alternate port number can be given as the first argument to the client:

    python greeter_client.py 40051
