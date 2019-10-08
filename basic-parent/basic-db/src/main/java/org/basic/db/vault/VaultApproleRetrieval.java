package org.basic.db.vault;

import java.io.IOException;
import java.util.concurrent.Callable;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

public class VaultApproleRetrieval implements Callable<String> {
	CloseableHttpClient httpclient;

	public VaultApproleRetrieval(CloseableHttpClient httpclient2) {
		httpclient = httpclient2;
	}

	int limit = 1000000;

	@Override
	public String call() throws Exception {
		int i = 0;
		while (i++ < limit) {
			try {
				HttpGet httpget = new HttpGet("https://localhost:8200/v1/sys/health");

				System.out.println("Executing request " + httpget.getRequestLine());

				// Create a custom response handler
				ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

					@Override
					public String handleResponse(final HttpResponse response)
							throws ClientProtocolException, IOException {
						int status = response.getStatusLine().getStatusCode();
						if (status >= 200 && status < 300) {
							HttpEntity entity = response.getEntity();
							return entity != null ? EntityUtils.toString(entity) : null;
						} else {
							throw new ClientProtocolException("Unexpected response status: " + status);
						}
					}

				};
				String responseBody = httpclient.execute(httpget, responseHandler);
				System.out.println(responseBody);
			} finally {
//				httpclient.close();
			}
		}
		return null;

	}

}
