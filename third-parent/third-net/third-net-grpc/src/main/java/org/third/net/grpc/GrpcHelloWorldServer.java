package org.third.net.grpc;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.third.net.grpc.impl.HelloWorldServiceImpl;
import org.third.net.grpc.impl.UserServiceImpl;

import io.grpc.Server;
import io.grpc.ServerBuilder;

public class GrpcHelloWorldServer {
	private static final Logger logger = LoggerFactory.getLogger(GrpcHelloWorldServer.class.getName());

	private int port = 50051;
	private Server server;

	private void start() throws IOException {
		server = ServerBuilder.forPort(port)//
				.addService(new HelloWorldServiceImpl())//
				.addService(new UserServiceImpl())//
				.build().start();
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

}
