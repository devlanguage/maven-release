package org.third.net.grpc.server;

import io.grpc.Attributes;
import io.grpc.ServerTransportFilter;

public class ServerTransportFilterImpl extends ServerTransportFilter {
	@Override
	public Attributes transportReady(Attributes transportAttrs) {
		// TODO Auto-generated method stub
		return super.transportReady(transportAttrs);
	}

	@Override
	public void transportTerminated(Attributes transportAttrs) {
		// TODO Auto-generated method stub
		super.transportTerminated(transportAttrs);
	}
}
