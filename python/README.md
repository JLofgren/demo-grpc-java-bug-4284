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
    
To run the client:

    python greeter_client.py
