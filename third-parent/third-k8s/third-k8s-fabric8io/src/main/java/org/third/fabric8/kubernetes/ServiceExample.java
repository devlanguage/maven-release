package org.third.fabric8.kubernetes;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.fabric8.kubernetes.api.model.IntOrString;
import io.fabric8.kubernetes.api.model.Service;
import io.fabric8.kubernetes.api.model.ServiceBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;

public class ServiceExample {
  private static final Logger logger = LoggerFactory.getLogger(ServiceExample.class);

  public static void main(String args[]) {
    try (final KubernetesClient client = new DefaultKubernetesClient(K8sUtil.getK8sConfig())) {
      Service service = new ServiceBuilder()
        .withNewMetadata()
        .withName("my-service")
        .endMetadata()
        .withNewSpec()
        .withSelector(Collections.singletonMap("app", "MyApp"))
        .addNewPort()
        .withName("test-port")
        .withProtocol("TCP")
        .withPort(80)
        .withTargetPort(new IntOrString(9376))
        .endPort()
        .withType("LoadBalancer")
        .endSpec()
        .withNewStatus()
        .withNewLoadBalancer()
        .addNewIngress()
        .withIp("146.148.47.155")
        .endIngress()
        .endLoadBalancer()
        .endStatus()
        .build();

      service = client.services().inNamespace(client.getNamespace()).create(service);
      log("Created service with name ", service.getMetadata().getName());

      String serviceURL = client.services().inNamespace(client.getNamespace()).withName(service.getMetadata().getName()).getURL("test-port");
      log("Service URL", serviceURL);

    }
  }

  private static void log(String action, Object obj) {
    logger.info("{}: {}", action, obj);
  }

  private static void log(String action) {
    logger.info(action);
  }
}
