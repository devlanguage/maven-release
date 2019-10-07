package org.third.net.grpc;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.third.net.grpc.stub.helloworld.HelloRequest;
import org.third.net.grpc.stub.helloworld.HelloResponse;
import org.third.net.grpc.stub.helloworld.HelloServiceGrpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

public class HelloWorldClient {

	private final ManagedChannel channel;
	private final HelloServiceGrpc.HelloServiceBlockingStub blockingStub;
	private static final Logger logger = LoggerFactory.getLogger(HelloWorldClient.class.getName());

	public HelloWorldClient(String host, int port) {
		channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext(true).build();

		blockingStub = HelloServiceGrpc.newBlockingStub(channel);
	}

	public void shutdown() throws InterruptedException {
		channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
	}

	public void greet(String name) {
		HelloRequest request = HelloRequest.newBuilder().setName(name).build();
		HelloResponse response;
		try {
			response = blockingStub.sayHello(request);
		} catch (StatusRuntimeException e) {
			logger.error("RPC failed: {0}", e.getStatus());
			return;
		}
		logger.info("Greeting: " + response.getMessage());
	}

	public static void main(String[] args) throws InterruptedException {
		HelloWorldClient client = new HelloWorldClient("127.0.0.1", 50051);
		try {
			String user = "world";
			if (args.length > 0) {
				user = args[0];
			}
			client.greet(user);
			
		} finally {
			client.shutdown();
		}
	}
}