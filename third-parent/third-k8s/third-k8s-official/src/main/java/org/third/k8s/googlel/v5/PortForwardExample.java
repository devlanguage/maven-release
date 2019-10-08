package org.third.k8s.googlel.v5;

import com.google.common.io.ByteStreams;
import io.kubernetes.client.ApiClient;
import io.kubernetes.client.ApiException;
import io.kubernetes.client.Configuration;
import io.kubernetes.client.PortForward;
import io.kubernetes.client.util.Config;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple example of how to use the Java API
 *
 * <p>
 * Easiest way to run this: mvn exec:java
 * -Dexec.mainClass="io.kubernetes.client.examples.PortForwardExample" from
 * inside $REPO_DIR/examples
 *
 * <p>
 * Then: curl localhost:8080 from a different terminal (but be quick about it,
 * the socket times out pretty fast...)
 */
public class PortForwardExample {
	public static void main(String[] args) throws IOException, ApiException, InterruptedException {
		ApiClient client = Config.defaultClient();
		Configuration.setDefaultApiClient(client);

		PortForward forward = new PortForward();
		List<Integer> ports = new ArrayList<>();
		ports.add(8080);
		ports.add(80);
		final PortForward.PortForwardResult result = forward.forward("default", "nginx-d5dc44cf7-x7475", ports);

		ServerSocket ss = new ServerSocket(8080);

		final Socket s = ss.accept();
		System.out.println("Connected!");

		new Thread(new Runnable() {
			public void run() {
				try {
					ByteStreams.copy(result.getInputStream(80), s.getOutputStream());
				} catch (IOException ex) {
					ex.printStackTrace();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}).start();

		new Thread(new Runnable() {
			public void run() {
				try {
					ByteStreams.copy(s.getInputStream(), result.getOutboundStream(80));
				} catch (IOException ex) {
					ex.printStackTrace();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}).start();

		Thread.sleep(10 * 1000);

		System.exit(0);
	}
}