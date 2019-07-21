package org.third.fabric8.kubernetes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.fabric8.kubernetes.api.model.Namespace;
import io.fabric8.kubernetes.api.model.NamespaceBuilder;
import io.fabric8.kubernetes.api.model.Quantity;
import io.fabric8.kubernetes.api.model.ResourceQuota;
import io.fabric8.kubernetes.api.model.ResourceQuotaBuilder;
import io.fabric8.kubernetes.client.APIGroupNotAvailableException;
import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;

public class NamespaceQuotaExample {
  private static final Logger logger = LoggerFactory.getLogger(NamespaceQuotaExample.class);

  public static void main(String[] args) throws InterruptedException {
    String master = "https://h9.test1.com:8443";

    if (args.length == 1) {
      master = args[0];
    }

    Config config = new ConfigBuilder().withMasterUrl(master).build();
    try (final KubernetesClient client = new DefaultKubernetesClient(config)) {
      String namespace = "namespacetest";
      if (args.length > 2) {
        namespace = args[2];
      }
      try  {
        // Creating namespace
        Namespace ns = new NamespaceBuilder().withNewMetadata().withName(namespace).addToLabels("hello", "world").endMetadata().build();
        log("Created namespace", client.namespaces().create(ns));

        // Get namespace by name
        log("Get namespace by name", client.namespaces().withName(namespace).get());
        // Get namespace by label
        log("Get namespace by label", client.namespaces().withLabel("hello", "world").list());

        ResourceQuota quota = new ResourceQuotaBuilder().withNewMetadata().withName("quota-example").endMetadata().withNewSpec().addToHard("pods", new Quantity("5")).endSpec().build();
        log("Create resource quota", client.resourceQuotas().inNamespace(namespace).create(quota));

        try {
          log("Get jobs in namespace", client.batch().jobs().inNamespace(namespace).list());
        } catch (APIGroupNotAvailableException e) {
          log("Skipping jobs example - extensions API group not available");
        }
      } finally {
        // Delete namespace
        log("Deleted namespace", client.namespaces().withName(namespace).delete());
      }
    } catch (Exception e) {
      e.printStackTrace();
      logger.error(e.getMessage(), e);

      Throwable[] suppressed = e.getSuppressed();
      if (suppressed != null) {
        for (Throwable t : suppressed) {
          logger.error(t.getMessage(), t);
        }
      }
    }
  }

  private static void log(String action, Object obj) {
    logger.info("{}: {}", action, obj);
  }

  private static void log(String action) {
    logger.info(action);
  }
}
