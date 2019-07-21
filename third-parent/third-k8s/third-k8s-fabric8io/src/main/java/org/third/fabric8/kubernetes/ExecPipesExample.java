package org.third.fabric8.kubernetes;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.fabric8.kubernetes.client.Callback;
import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientException;
import io.fabric8.kubernetes.client.dsl.ExecWatch;
import io.fabric8.kubernetes.client.utils.InputStreamPumper;

public class ExecPipesExample {

    public static void main(String[] args) throws InterruptedException, IOException {
        String master = "https://h9.test1.com:8443/";
        String podName = null;

        if (args.length == 2) {
            master = args[0];
            podName = args[1];
        }
        if (args.length == 1) {
            podName = args[0];
        }

        Config config = new ConfigBuilder().withMasterUrl(master).build();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        try (
                KubernetesClient client = new DefaultKubernetesClient(config);
                ExecWatch watch = client.pods().withName(podName)
                        .redirectingInput()
                        .redirectingOutput()
                        .redirectingError()
                        .redirectingErrorChannel()
                        .exec();
                InputStreamPumper pump = new InputStreamPumper(watch.getOutput(), new SystemOutCallback()))
        {

            executorService.submit(pump);
            watch.getInput().write("ls -al\n".getBytes());
            Thread.sleep(5 * 1000);
        } catch (Exception e) {
            throw KubernetesClientException.launderThrowable(e);
        } finally {
            executorService.shutdownNow();
        }
    }

    private static class SystemOutCallback implements Callback<byte[]> {
        @Override
        public void call(byte[] data) {
            System.out.print(new String(data));
        }
    }
}
