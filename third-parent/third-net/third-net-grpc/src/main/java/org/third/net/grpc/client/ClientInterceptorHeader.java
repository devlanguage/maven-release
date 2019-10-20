package org.third.net.grpc.client;

import io.grpc.CallOptions;
import io.grpc.Channel;
import io.grpc.ClientCall;
import io.grpc.ClientInterceptor;
import io.grpc.ForwardingClientCall.SimpleForwardingClientCall;
import io.grpc.ForwardingClientCallListener;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;

public class ClientInterceptorHeader implements ClientInterceptor {
	static final Metadata.Key<String> CUSTOM_HEADER_KEY = Metadata.Key.of("clientHeader",
			Metadata.ASCII_STRING_MARSHALLER);

	@Override
	public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> method,
			CallOptions callOptions, Channel next) {
		// return next.newCall(method, callOptions);
		SimpleForwardingClientCall<ReqT, RespT> clientCall = new SimpleForwardingClientCall<ReqT, RespT>(
				next.newCall(method, callOptions)) {
			@Override
			public void start(Listener<RespT> responseListener, Metadata headers) {
				// 放入客户端的header
				headers.put(CUSTOM_HEADER_KEY, "request");
				super.start(
						new ForwardingClientCallListener.SimpleForwardingClientCallListener<RespT>(responseListener) {
							@Override
							public void onHeaders(Metadata headers) {
								// 输出服务端传递回来的header
								System.out.println("header received from server:" + headers);
								super.onHeaders(headers);
							}
						}, headers);
			}
		};
		return clientCall;
	}

}
