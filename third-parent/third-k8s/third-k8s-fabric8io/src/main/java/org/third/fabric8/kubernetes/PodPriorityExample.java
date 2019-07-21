package org.third.fabric8.kubernetes;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.fabric8.kubernetes.api.model.ContainerBuilder;
import io.fabric8.kubernetes.api.model.PodBuilder;
import io.fabric8.kubernetes.api.model.scheduling.PriorityClass;
import io.fabric8.kubernetes.api.model.scheduling.PriorityClassBuilder;
import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientException;

public class PodPriorityExample {
  private static final Logger logger = LoggerFactory.getLogger(PodDisruptionBudgetExample.class);

  public static void main(String args[]) throws InterruptedException {
    String master = "https://192.168.99.100:8443/";
    if (args.length == 1) {
      master = args[0];
    }

    log("Using master with url ", master);
    Config config = new ConfigBuilder().withMasterUrl(master).build();
    try (final KubernetesClient client = new DefaultKubernetesClient(config)) {
      PriorityClass priorityClass = new PriorityClassBuilder()
        .withNewMetadata().withName("high-priority").endMetadata()
        .withValue(new Integer(100000))
        .withGlobalDefault(false)
        .withDescription("This priority class should be used for XYZ service pods only.")
        .build();
      client.scheduling().priorityClass().create(priorityClass);

      client.pods().inNamespace("default").create(new PodBuilder()
        .withNewMetadata().withName("nginx").withLabels(Collections.singletonMap("env", "test")).endMetadata()
        .withNewSpec()
        .addToContainers(new ContainerBuilder().withName("nginx").withImage("nginx").withImagePullPolicy("IfNotPresent").build())
        .withPriorityClassName("high-priority")
        .endSpec()
        .build()
      );
    } catch (KubernetesClientException e) {
      e.printStackTrace();
      log("Could not create resource", e.getMessage());
    }
  }

  private static void log(String action, Object obj) {
    logger.info("{}: {}", action, obj);
  }

  private static void log(String action) {
    logger.info(action);
  }
}
