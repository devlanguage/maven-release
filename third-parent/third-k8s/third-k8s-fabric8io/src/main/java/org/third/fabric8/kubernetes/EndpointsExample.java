package org.third.fabric8.kubernetes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.fabric8.kubernetes.api.model.Endpoints;
import io.fabric8.kubernetes.api.model.EndpointsBuilder;
import io.fabric8.kubernetes.api.model.Service;
import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;

public class EndpointsExample {
  private static final Logger logger = LoggerFactory.getLogger(EndpointsExample.class);

  public static void main(String[] args) {
    String master = "https://h9.test1.com:8443";

    Config config = new ConfigBuilder().withMasterUrl(master).build();
    try (final KubernetesClient client = new DefaultKubernetesClient(config)) {
      try {
        String namespace = "default";
        log("namespace", namespace);
        Deployment deployment = client.apps().deployments().inNamespace(namespace).load(EndpointsExample.class.getResourceAsStream("/endpoints-deployment.yml")).get();
        log("Deployment created");
        client.apps().deployments().inNamespace(namespace).create(deployment);

        Service service = client.services().inNamespace(namespace).load(EndpointsExample.class.getResourceAsStream("/endpoints-service.yml")).get();
        log("Service created");
        client.services().inNamespace(namespace).create(service);

        Endpoints endpoints = new EndpointsBuilder()
          .withNewMetadata().withName("external-web").withNamespace(namespace).endMetadata()
          .withSubsets().addNewSubset().addNewAddress().withIp("10.10.50.53").endAddress()
          .addNewPort().withPort(80).withName("apache").endPort()
          .endSubset()
          .build();
        log("Endpoint created");
        client.endpoints().inNamespace(namespace).create(endpoints);
        log("Endpoint url");
        endpoints = client.endpoints().inNamespace(namespace).withName("external-web").get();
        log("Endpoint Port", endpoints.getSubsets().get(0).getPorts().get(0).getName());
      } finally {
        // clear resources
        client.apps().deployments().inNamespace("default").withName("endpoints-deployment").delete();
        client.services().inNamespace("default").withName("endpoints-nginx").delete();
        client.endpoints().inNamespace("default").withName("external-web").delete();
      }
    } catch (Exception e) {
      log("Exception occurred: ", e.getMessage());
      e.printStackTrace();
    }
  }

  private static void log(String action, Object obj) {
    logger.info("{}: {}", action, obj);
  }

  private static void log(String action) {
    logger.info(action);
  }

}
