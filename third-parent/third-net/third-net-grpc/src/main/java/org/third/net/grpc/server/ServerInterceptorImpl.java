package org.third.net.grpc.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.grpc.ForwardingServerCall;
import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCall.Listener;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;

public class ServerInterceptorImpl implements ServerInterceptor {
	private static final Logger logger = LoggerFactory.getLogger(ServerInterceptorImpl.class.getName());

	// 服务端header的key
	static final Metadata.Key<String> CUSTOM_HEADER_KEY = Metadata.Key.of("serverHeader",
			Metadata.ASCII_STRING_MARSHALLER);

	@Override
	public <ReqT, RespT> Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata headers,
			ServerCallHandler<ReqT, RespT> next) {
		// 输出客户端传递过来的header
		logger.info("header received from client:" + headers);

		return next.startCall(new ForwardingServerCall.SimpleForwardingServerCall<ReqT, RespT>(call) {
			@Override
			public void sendHeaders(Metadata responseHeaders) {
				// 在返回中增加header
				responseHeaders.put(CUSTOM_HEADER_KEY, "response");
				super.sendHeaders(responseHeaders);
			}
		}, headers);
	}

}
