package org.third.net.grpc.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.third.net.grpc.stub.helloworld.HelloRequest;
import org.third.net.grpc.stub.helloworld.HelloResponse;
import org.third.net.grpc.stub.helloworld.HelloServiceGrpc.HelloServiceImplBase;

import io.grpc.stub.StreamObserver;

public class HelloWorldServiceImpl extends HelloServiceImplBase {
	private static final Logger logger = LoggerFactory.getLogger(HelloWorldServiceImpl.class.getName());

	@Override
	public void sayHello(HelloRequest req, StreamObserver<HelloResponse> responseObserver) {
		logger.info("Receiving the calling:{}", req.getName());

		HelloResponse reply = HelloResponse.newBuilder().setAge(12).setPostcode(2393912).setMessage(("Hello " + req.getName())).build();
		responseObserver.onNext(reply);
		responseObserver.onCompleted();
	}

}
