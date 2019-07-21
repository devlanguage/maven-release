package org.third.fabric8.kubernetes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.fabric8.kubernetes.api.model.NamespaceBuilder;
import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientException;

public class DeleteExamples {

  private static final Logger logger = LoggerFactory.getLogger(DeleteExamples.class);

  public static void main(String[] args) {
    String master = "https://h9.test1.com:8443/";
    if (args.length == 1) {
      master = args[0];
    }

    Config config = new ConfigBuilder().withMasterUrl(master).build();
    KubernetesClient client = new DefaultKubernetesClient(config);
    try {
      log("Create namespace:", client.namespaces().create(new NamespaceBuilder().withNewMetadata().withName("thisisatest").endMetadata().build()));
      log("Deleted namespace:", client.namespaces().withName("test").delete());
      log("Deleted testPod:", client.pods().inNamespace("thisisatest").withName("testpod").delete());
      log("Deleted pod by label:", client.pods().withLabel("this", "works").delete());
    } catch (KubernetesClientException e) {
      logger.error(e.getMessage(), e);
    } finally {
      client.namespaces().withName("thisisatest").delete();
      client.close();
    }
  }

  private static void log(String action, Object obj) {
    logger.info("{}: {}", action, obj);
  }

  private static void log(String action) {
    logger.info(action);
  }

}
