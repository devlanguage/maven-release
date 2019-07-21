package org.third.fabric8.kubernetes;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.fabric8.kubernetes.api.model.apiextensions.CustomResourceDefinition;
import io.fabric8.kubernetes.api.model.apiextensions.CustomResourceDefinitionList;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;

public class CRDLoadExample {
  private static Logger logger = LoggerFactory.getLogger(CRDLoadExample.class);

  public static void main(String args[]) throws IOException {
    try (final KubernetesClient client = new DefaultKubernetesClient()) {
      // List all Custom resources.
      log("Listing all current Custom Resource Definitions :");
      CustomResourceDefinitionList crdList = client.customResourceDefinitions().list();
      crdList.getItems().forEach(crd -> log(crd.getMetadata().getName()));

      // Creating a custom resource from yaml
      CustomResourceDefinition aCustomResourceDefinition = client.customResourceDefinitions().load(CRDLoadExample.class.getResourceAsStream("/crd.yml")).get();
      log("Creating CRD...");
      client.customResourceDefinitions().create(aCustomResourceDefinition);

      log("Updated Custom Resource Definitions: ");
      client.customResourceDefinitions().list().getItems().forEach(crd -> log(crd.getMetadata().getName()));

    }
  }

  private static void log(String action, Object obj) {
    logger.info("{}: {}", action, obj);
  }

  private static void log(String action) {
    logger.info(action);
  }
}
