package org.third.net.grpc.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.third.net.grpc.stub.user.GetUsersRequest;
import org.third.net.grpc.stub.user.User;
import org.third.net.grpc.stub.user.UserList;
import org.third.net.grpc.stub.user.UserServiceGrpc.UserServiceImplBase;

import io.grpc.stub.StreamObserver;

public class UserServiceImpl extends UserServiceImplBase {
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class.getName());
	private static final Map<String, User> USER_CACHE = new ConcurrentHashMap<String, User>();

	@Override
	public void getUsers(GetUsersRequest request, StreamObserver<UserList> responseObserver) {
		logger.info("Receiving the getUsers:{}", request.getUserNameList());
		UserList userList = UserList.newBuilder().addAllUsers(USER_CACHE.values()).build();
		responseObserver.onNext(userList);
		responseObserver.onCompleted();
	}

	@Override
	public void getUser(User request, StreamObserver<User> responseObserver) {
		logger.info("Receiving the getUser:{}", request.getName());
		
		super.getUser(request, responseObserver);
	}

	@Override
	public void createUser(User request, StreamObserver<User> responseObserver) {
		logger.info("Receiving the createUser:{}", request);
		USER_CACHE.put(request.getName(), request);
		responseObserver.onNext(request);
		responseObserver.onCompleted();
	}

}
