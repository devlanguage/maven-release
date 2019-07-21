package org.third.fabric8.openshift;

import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.openshift.client.OpenShiftClient;

public class AdaptClient {
  public static void main(String[] args) {
    KubernetesClient client = new DefaultKubernetesClient();
    OpenShiftClient oclient = client.adapt(OpenShiftClient.class);
    System.out.println("Adapted to an openshift client: " + oclient);
  }
}
