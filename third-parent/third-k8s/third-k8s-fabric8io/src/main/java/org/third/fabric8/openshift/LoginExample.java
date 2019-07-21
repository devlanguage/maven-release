package org.third.fabric8.openshift;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.openshift.client.OpenShiftClient;

public class LoginExample {
  private static final Logger logger = LoggerFactory.getLogger(LoginExample.class);

  public static void main(String[] args) {

    try (DefaultKubernetesClient kubernetesClient = new DefaultKubernetesClient(new ConfigBuilder()
      .withMasterUrl("cluster_url")
      .withUsername("my_username")
      .withPassword("my_password")
      .build())) {

      final OpenShiftClient openShiftClient = kubernetesClient.adapt(OpenShiftClient.class);
      logger.info(openShiftClient.projects().list().toString());
    }
  }
}
