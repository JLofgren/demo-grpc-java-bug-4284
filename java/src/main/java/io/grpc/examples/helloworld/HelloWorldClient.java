/*
 * Copyright 2015, gRPC Authors All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.grpc.examples.helloworld;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Metadata;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.MetadataUtils;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A simple client that requests a greeting from the {@link HelloWorldServer}.
 */
public class HelloWorldClient {
  private static final Logger logger = Logger.getLogger(HelloWorldClient.class.getName());

  private static final String HOST = "localhost";

  /* DEMO: The connection will work if talking directly to the server at port
  50051, but will fail if connecting through envoy proxy at port 40051.
  Envoy sets NO VALUE for SETTINGS_MAX_HEADER_LIST_SIZE. */
  private static final int DEFAULT_PORT = 50051;
//  private static final int DEFAULT_PORT = 40051;

  private final ManagedChannel channel;
  private final GreeterGrpc.GreeterBlockingStub blockingStub;

  /** Construct client connecting to HelloWorld server at {@code host:port}. */
  public HelloWorldClient(String host, int port) {
    this(ManagedChannelBuilder.forAddress(host, port)
        // Channels are secure by default (via SSL/TLS). For the example we disable TLS to avoid
        // needing certificates.
        .usePlaintext()
        .build());
  }

  /** Construct client for accessing RouteGuide server using the existing channel. */
  HelloWorldClient(ManagedChannel channel) {
    this.channel = channel;

    /* DEMO: Add a header larger than 8k, but still smaller than the limit of
    100k that the server has explicitly set. This demonstrates how the client
    will enforce an arbitrary header size limit even when the server does not
    set the SETTINGS_MAX_HEADER_LIST_SIZE. */
    Metadata extraHeaders = new Metadata();
    String bigHeaderVal = new String(new char[1024*9]).replace('\0', 'X');
    extraHeaders.put(Metadata.Key.of(
            "x-big-header", Metadata.ASCII_STRING_MARSHALLER),
            bigHeaderVal);

    blockingStub = MetadataUtils.attachHeaders(
            GreeterGrpc.newBlockingStub(channel), extraHeaders);
  }

  public void shutdown() throws InterruptedException {
    channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
  }

  /** Say hello to server. */
  public void greet(String name) {
    logger.info("Will try to greet the server as " + name + " ...");
    HelloRequest request = HelloRequest.newBuilder().setName(name).build();
    HelloReply response;
    try {
      response = blockingStub.sayHello(request);
    } catch (StatusRuntimeException e) {
      logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
      return;
    }
    logger.info("Got this response from the server: " + response.getMessage());
  }

  /**
   * Greet server. If provided, the first element of {@code args} is the name to use in the
   * greeting.
   */
  public static void main(String[] args) throws Exception {
    HelloWorldClient client = null;
    try {
      /* Access a service running on the HOST on port DEFAULT_PORT */
      String user = "Java client";
      int port = DEFAULT_PORT;
      if (args.length > 0) {
        port = Integer.parseInt(args[0]); /* Use the arg as the port to attach to if provided */
      }
      client = new HelloWorldClient(HOST, port);
      client.greet(user);
    } finally {
      if (client != null) {
        client.shutdown();
      }
    }
  }
}
