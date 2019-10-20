package org.third.net.grpc;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.third.net.grpc.client.ClientInterceptorHeader;
import org.third.net.grpc.stub.hello.HelloRequest;
import org.third.net.grpc.stub.hello.HelloResponse;
import org.third.net.grpc.stub.hello.HelloServiceGrpc;

import io.grpc.Channel;
import io.grpc.ClientInterceptors;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;

public class HelloServiceClient {

	private final ManagedChannel originalChannel;
	private final HelloServiceGrpc.HelloServiceBlockingStub blockingStub;
	private final HelloServiceGrpc.HelloServiceStub asyncStub;
	private final HelloServiceGrpc.HelloServiceFutureStub futrueStub;
	private static final Logger logger = LoggerFactory.getLogger(HelloServiceClient.class.getName());

	public HelloServiceClient(String host, int port) {
		originalChannel = ManagedChannelBuilder.forAddress(host, port).usePlaintext(true).build();

		Channel channel = ClientInterceptors.intercept(originalChannel, new ClientInterceptorHeader());
		blockingStub = HelloServiceGrpc.newBlockingStub(channel);
		// Creates a new async stub that supports all call types for the service
		asyncStub = HelloServiceGrpc.newStub(channel);
		futrueStub = HelloServiceGrpc.newFutureStub(channel);
	}

	public void shutdown() throws InterruptedException {
		originalChannel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
	}

	public void sayHi(String name) {
		HelloRequest request = HelloRequest.newBuilder().setName(name).build();
		HelloResponse response;
		try {
			response = blockingStub.sayHi(request);
		} catch (StatusRuntimeException e) {
			logger.error("RPC failed: {0}", e.getStatus());
			return;
		}
		logger.info("sayHi: " + response.getMessage());
	}

	public void sayHiServerStream(String name) {
		HelloRequest request = HelloRequest.newBuilder().setName(name).build();
		Iterator<HelloResponse> responserIterator = null;
		try {
			responserIterator = blockingStub.sayHiServerStream(request);
		} catch (StatusRuntimeException e) {
			logger.error("RPC failed: {0}", e.getStatus());
			return;
		}
		while (responserIterator.hasNext()) {
			HelloResponse response = responserIterator.next();
			logger.info("sayHiServerStream: " + response.getMessage());
		}
	}
	public void sayHiClientStream(String name) {
		try {
			StreamObserver<HelloResponse> responseObserver = new StreamObserver<HelloResponse>() {

				@Override
				public void onNext(HelloResponse value) {
					logger.info("client stream--" + value.getMessage());

				}

				@Override
				public void onError(Throwable t) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onCompleted() {
					// channel.shutdown();
				}
			};
			StreamObserver<HelloRequest> clientStreamObserver = asyncStub.sayHiClientStream(responseObserver);
			clientStreamObserver.onNext(HelloRequest.newBuilder().setName("sayHiClientStream01-").build());
			clientStreamObserver.onNext(HelloRequest.newBuilder().setName("sayHiClientStream02-").build());
			clientStreamObserver.onNext(HelloRequest.newBuilder().setName("sayHiClientStream03-").build());

			clientStreamObserver.onCompleted();

		    //由于是异步获得结果，所以sleep一秒
//		    Thread.sleep(1000);

		} catch (StatusRuntimeException e) {
			logger.error("RPC failed: {0}", e.getStatus());
			return;
		}
	}
	public void sayHiBiStream(String name) {
		try {
			StreamObserver<HelloResponse> responseObserver = new StreamObserver<HelloResponse>() {

				@Override
				public void onNext(HelloResponse value) {
					logger.info("Bidirection stream--" + value.getMessage());

				}

				@Override
				public void onError(Throwable t) {

				}

				@Override
				public void onCompleted() {
					// channel.shutdown();
				}
			};
			StreamObserver<HelloRequest> biStreamObserver = asyncStub.sayHiBiStream(responseObserver);
			biStreamObserver.onNext(HelloRequest.newBuilder().setName("sayHiBiStream01-").build());	    Thread.sleep(1000);

			biStreamObserver.onNext(HelloRequest.newBuilder().setName("sayHiBiStream02-").build()); 	    Thread.sleep(2000);
			biStreamObserver.onNext(HelloRequest.newBuilder().setName("sayHiBiStream03-").build());       Thread.sleep(3000);
			
		    biStreamObserver.onCompleted();

		    //由于是异步获得结果，所以sleep一秒
//		    Thread.sleep(1000);

		} catch (Exception e) {
			logger.error("RPC failed: {0}", e.getMessage(), e);
			return;
		}
	}
	public static void main(String[] args) throws InterruptedException {
		HelloServiceClient client = new HelloServiceClient("127.0.0.1", GrpcServer.port);
		try {
			String user = "world";
			if (args.length > 0) {
				user = args[0];
			}
			client.sayHi(user);
			client.sayHiServerStream(user);
			client.sayHiClientStream(user);
			client.sayHiBiStream(user);

		} finally {
			client.shutdown();
		}
	}
}