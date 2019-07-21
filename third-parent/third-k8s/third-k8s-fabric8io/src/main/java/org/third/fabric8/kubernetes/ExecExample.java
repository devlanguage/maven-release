package org.third.fabric8.kubernetes;

import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.dsl.ExecListener;
import io.fabric8.kubernetes.client.dsl.ExecWatch;
import okhttp3.Response;

public class ExecExample {

    public static void main(String[] args) throws InterruptedException {
        if (args.length < 1) {
            System.out.println("Usage: podName [master] [namespace]");
            return;
          }

        String podName = args[0];
        String namespace = "default";
        String master = "https://h9.test1.com:8443/";

        if (args.length > 1) {
            master = args[1];
          }
          if (args.length > 2) {
            namespace = args[2];
          }

        Config config = new ConfigBuilder().withMasterUrl(master).build();
        try (final KubernetesClient client = new DefaultKubernetesClient(config);
             ExecWatch watch = client.pods().inNamespace(namespace).withName(podName)
                .readingInput(System.in)
                .writingOutput(System.out)
                .writingError(System.err)
                .withTTY()
                .usingListener(new SimpleListener())
                .exec()){

            Thread.sleep(10 * 1000);
        }
    }

    private static class SimpleListener implements ExecListener {

        @Override
        public void onOpen(Response response) {
            System.out.println("The shell will remain open for 10 seconds.");
        }

        @Override
        public void onFailure(Throwable t, Response response) {
            System.err.println("shell barfed");
        }

        @Override
        public void onClose(int code, String reason) {
            System.out.println("The shell will now close.");
        }
    }

}
