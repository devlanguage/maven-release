package org.third.net.grpc;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.third.net.grpc.impl.HelloServiceImpl;
import org.third.net.grpc.impl.UserServiceImpl;
import org.third.net.grpc.server.ServerInterceptorImpl;
import org.third.net.grpc.server.ServerTransportFilterImpl;

import io.grpc.Server;
import io.grpc.ServerBuilder;

/**
 * <pre>
 * 
 *  protoc --java_out=./test1 --proto_path=./ --proto_path=./common --proto_path=./hello/ ./hello/HelloService.proto
    
    protoc --proto_path=./
        --proto_path=../target/protoc-dependencies\google
        --plugin=protoc-gen-grpc-java=/C/Windows/System32/protoc-gen-grpc-java.exe   --grpc-java_out=./grpc-java  ./hello/*.proto 
    protoc --proto_path=./ 
        --plugin=protoc-gen-grpc-java=/C/Windows/System32/protoc-gen-grpc-java.exe 
        --grpc-java_out=./grpc-java ./hello/*.proto
 * 
 * </pre>
 *
 */

public class GrpcServer {
	private static final Logger logger = LoggerFactory.getLogger(GrpcServer.class.getName());

	public static final int port = 50051;
	private Server server;

	private void start() throws IOException {

		server = ServerBuilder.forPort(port)//
				.addTransportFilter(new ServerTransportFilterImpl())
				.intercept(new ServerInterceptorImpl())
				.addService(new HelloServiceImpl())//
				.addService(new UserServiceImpl())//
				.build().start();
		logger.info("Server started, listening on " + port);

		Runtime.getRuntime().addShutdownHook(new Thread() {

			@Override
			public void run() {

				System.err.println("*** shutting down gRPC server since JVM is shutting down");
				GrpcServer.this.stop();
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

		final GrpcServer server = new GrpcServer();
		server.start();
		server.blockUntilShutdown();
	}

}
