package org.third.net.grpc;

import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.third.net.grpc.stub.user.GetUsersRequest;
import org.third.net.grpc.stub.user.User;
import org.third.net.grpc.stub.user.UserList;
import org.third.net.grpc.stub.user.UserServiceGrpc;
import org.third.net.grpc.stub.user.UserServiceGrpc.UserServiceBlockingStub;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class UserServiceClient {

	private final ManagedChannel channel;
	private final UserServiceBlockingStub blockingStub;
	private static final Logger logger = LoggerFactory.getLogger(UserServiceClient.class.getName());

	public UserServiceClient(String host, int port) {
		channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext(true).build();

		blockingStub = UserServiceGrpc.newBlockingStub(channel);
	}

	public void shutdown() throws InterruptedException {
		channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
	}

	public static void main(String[] args) throws InterruptedException {
		UserServiceClient client = new UserServiceClient("127.0.0.1", 50051);
		try {
			client.test();

		} finally {
			client.shutdown();
		}
	}

	private void test() {
		int userId = new SecureRandom().nextInt(10000);
		User u1 = User.newBuilder().setId(userId).setName("test-"+userId).build();
		User u2 = blockingStub.createUser(u1);
		logger.info(u2.getName());
		
		GetUsersRequest request = GetUsersRequest.newBuilder().addUserName("test1").build();
		UserList users = blockingStub.getUsers(request);
		logger.info(users.getUsersList().toString());
	}
}