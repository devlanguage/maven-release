package org.third.fabric8.kubernetes;

import java.io.IOException;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.api.model.PodBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.dsl.LogWatch;

public class WaitUntilReadyExample {
  private static Logger logger = LoggerFactory.getLogger(WaitUntilReadyExample.class);

  public static void main(String args[]) throws IOException, InterruptedException {
    try (final KubernetesClient client = new DefaultKubernetesClient()) {
      Pod pod = new PodBuilder()
        .withNewMetadata().withName("myapp-pod").withLabels(Collections.singletonMap("app", "myapp-pod")).endMetadata()
        .withNewSpec()
        .addNewContainer()
        .withName("myapp-container")
        .withImage("busybox:1.28")
        .withCommand("sh", "-c", "echo The app is running!; sleep 10")
        .endContainer()
        .addNewInitContainer()
        .withName("init-myservice")
        .withImage("busybox:1.28")
        .withCommand("sh", "-c", "echo inititalizing...; sleep 5")
        .endInitContainer()
        .endSpec()
        .build();

      String namespace = "default";
      pod = client.pods().inNamespace(namespace).create(pod);
      log("Pod created, waiting for it to get ready");
      client.resource(pod).inNamespace(namespace).waitUntilReady(10, TimeUnit.SECONDS);
      log("Pod is ready now.");

      LogWatch watch = client.pods().inNamespace(namespace).withName(pod.getMetadata().getName()).watchLog(System.out);
      watch.wait(10);
    }
  }

  private static void log(String action, Object obj) {
    logger.info("{}: {}", action, obj);
  }

  private static void log(String action) {
    logger.info(action);
  }
}
