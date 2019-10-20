package org.third.net.grpc.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.third.net.grpc.stub.common.MapEntry;
import org.third.net.grpc.stub.hello.GetMetadataRequest;
import org.third.net.grpc.stub.hello.GetMetadataResponse;
import org.third.net.grpc.stub.hello.HelloRequest;
import org.third.net.grpc.stub.hello.HelloResponse;
import org.third.net.grpc.stub.hello.HelloServiceGrpc.HelloServiceImplBase;

import io.grpc.stub.StreamObserver;

public class HelloServiceImpl extends HelloServiceImplBase {
	private static final Logger logger = LoggerFactory.getLogger(HelloServiceImpl.class.getName());

	@Override
	public void sayHi(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
		logger.info("Receiving the calling:{}", request.getName());

		HelloResponse reply = HelloResponse.newBuilder().setAge(12).setPostcode(2393912)
				.setMessage(("Hello " + request.getName())).build();
		responseObserver.onNext(reply);
		responseObserver.onCompleted();
	}

	@Override
	public void getMetadata(GetMetadataRequest request, StreamObserver<GetMetadataResponse> responseObserver) {
		logger.info("Receiving the calling:{}", request.getIdList());

		MapEntry entry1 = MapEntry.newBuilder().setKey("test1").setValue("test1-value").build();
		GetMetadataResponse reply = GetMetadataResponse.newBuilder().setEntry(1, entry1).build();
		responseObserver.onNext(reply);
		responseObserver.onCompleted();
	}

	@Override
	public StreamObserver<HelloRequest> sayHiClientStream(StreamObserver<HelloResponse> responseObserver) {
		// 返回observer应对多个请求对象
		return new StreamObserver<HelloRequest>() {
			private HelloResponse.Builder responseBuilder = HelloResponse.newBuilder();

			@Override
			public void onNext(HelloRequest request) {
				responseBuilder.setMessage(responseBuilder.getMessage() + "," + request.getName());
			}

			@Override
			public void onError(Throwable t) {
			}

			@Override
			public void onCompleted() {
				responseBuilder.setMessage("hello" + responseBuilder.getMessage());
				responseObserver.onNext(responseBuilder.build());
				responseObserver.onCompleted();
			}
		};

	}

	@Override
	public void sayHiServerStream(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
		// 返回多个结果
		responseObserver.onNext(HelloResponse.newBuilder().setAge(11).setPostcode(2393912)
				.setMessage(("(Processed/01 " + request.getName()) + ")").build());
		responseObserver.onNext(HelloResponse.newBuilder().setAge(12).setPostcode(2393912)
				.setMessage(("(Processed/02 " + request.getName()) + ")").build());
		responseObserver.onNext(HelloResponse.newBuilder().setAge(13).setPostcode(2393912)
				.setMessage(("(Processed/03 " + request.getName()) + ")").build());
		responseObserver.onCompleted();
	}

	@Override
	public StreamObserver<HelloRequest> sayHiBiStream(StreamObserver<HelloResponse> responseObserver) {
		// 返回observer应对多个请求对象
		return new StreamObserver<HelloRequest>() {

			@Override
			public void onNext(HelloRequest request) {
				responseObserver.onNext(
						HelloResponse.newBuilder().setMessage("(Processed/01:" + request.getName() + ")").build());
				responseObserver.onNext(
						HelloResponse.newBuilder().setMessage("(Processed/02:, " + request.getName() + ")").build());
			}

			@Override
			public void onError(Throwable t) {
			}

			@Override
			public void onCompleted() {
				responseObserver.onCompleted();
			}
		};

	}
}
