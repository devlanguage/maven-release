package org.third.fabric8.kubernetes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.fabric8.kubernetes.api.model.NamespaceBuilder;
import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientException;

public class ReplaceExamples {

  private static final Logger logger = LoggerFactory.getLogger(ReplaceExamples.class);

  public static void main(String[] args) {
    String master = "https://h9.test1.com:8443/";
    if (args.length == 1) {
      master = args[0];
    }

    Config config = new ConfigBuilder().withMasterUrl(master).build();
    try (KubernetesClient client = new DefaultKubernetesClient(config)) {
      try {
        log("Create namespace:", client.namespaces().create(new NamespaceBuilder().withNewMetadata().withName("thisisatest").endMetadata().build()));

        Pod createdPod = client.pods().inNamespace("thisisatest").createNew()
          .withNewMetadata()
          .withName("testpod")
          .addToLabels("server", "nginx")
          .endMetadata()
          .withNewSpec()
          .addNewContainer().withName("nginx").withImage("nginx")
          .addNewPort().withContainerPort(80).endPort()
          .endContainer()
          .endSpec().done();
        log("Created testPod:", createdPod);

        Pod updatedPod = client.pods().inNamespace("thisisatest").withName("testpod").edit()
          .editMetadata()
          .addToLabels("server2", "nginx2")
          .and().done();
        log("Replaced testPod:", updatedPod);

      } catch (KubernetesClientException e) {
        logger.error(e.getMessage(), e);
      } finally {
        client.namespaces().withName("thisisatest").delete();
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
