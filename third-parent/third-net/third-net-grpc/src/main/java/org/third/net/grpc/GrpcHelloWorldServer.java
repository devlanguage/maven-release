package org.third.net.grpc;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.third.net.grpc.stub.helloworld.GreeterGrpc;
import org.third.net.grpc.stub.helloworld.HelloReply;
import org.third.net.grpc.stub.helloworld.HelloRequest;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

public class GrpcHelloWorldServer {
	private static final Logger logger = LoggerFactory.getLogger(GrpcHelloWorldServer.class.getName());

	private int port = 50051;
	private Server server;

	private void start() throws IOException {
		server = ServerBuilder.forPort(port).addService(new GreeterImpl()).build().start();
		logger.info("Server started, listening on " + port);

		Runtime.getRuntime().addShutdownHook(new Thread() {

			@Override
			public void run() {

				System.err.println("*** shutting down gRPC server since JVM is shutting down");
				GrpcHelloWorldServer.this.stop();
				System.err.println("*** server shut down");
			}
		});
	}

	private void stop() {
		if (server != null) {
			server.shutdown();
		}
	}

	// block 一直到退出程序
	private void blockUntilShutdown() throws InterruptedException {
		if (server != null) {
			server.awaitTermination();
		}
	}

	public static void main(String[] args) throws IOException, InterruptedException {

		final GrpcHelloWorldServer server = new GrpcHelloWorldServer();
		server.start();
		server.blockUntilShutdown();
	}

	// 实现 定义一个实现服务接口的类
	private class GreeterImpl extends GreeterGrpc.GreeterImplBase {

		@Override
		public void sayHello(HelloRequest req, StreamObserver<HelloReply> responseObserver) {
			logger.info("Receiving the calling:{}", req.getName());
			
			HelloReply reply = HelloReply.newBuilder().setMessage(("Hello " + req.getName())).build();
			responseObserver.onNext(reply);
			responseObserver.onCompleted();
		}
	}

}
