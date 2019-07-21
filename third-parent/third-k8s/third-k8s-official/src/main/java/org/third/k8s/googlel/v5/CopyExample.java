package org.third.k8s.googlel.v5;

import com.google.common.io.ByteStreams;
import io.kubernetes.client.ApiClient;
import io.kubernetes.client.ApiException;
import io.kubernetes.client.Configuration;
import io.kubernetes.client.Copy;
import io.kubernetes.client.util.Config;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;

/**
 * A simple example of how to use the Java API
 *
 * <p>Easiest way to run this: mvn exec:java
 * -Dexec.mainClass="io.kubernetes.client.examples.CopyExample"
 *
 * <p>From inside $REPO_DIR/examples
 */
public class CopyExample {
  public static void main(String[] args) throws IOException, ApiException, InterruptedException {
    String podName = "kube-addon-manager-minikube";
    String namespace = "kube-system";

    ApiClient client = Config.defaultClient();
    Configuration.setDefaultApiClient(client);

    Copy copy = new Copy();
    InputStream dataStream = copy.copyFileFromPod(namespace, podName, "/etc/motd");
    ByteStreams.copy(dataStream, System.out);

    copy.copyDirectoryFromPod(namespace, podName, null, "/etc", Paths.get("/tmp/etc"));

    System.out.println("Done!");
  }
}
