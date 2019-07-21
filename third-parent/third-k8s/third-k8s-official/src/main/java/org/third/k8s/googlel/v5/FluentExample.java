package org.third.k8s.googlel.v5;

import io.kubernetes.client.ApiClient;
import io.kubernetes.client.ApiException;
import io.kubernetes.client.Configuration;
import io.kubernetes.client.apis.CoreV1Api;
import io.kubernetes.client.models.V1Container;
import io.kubernetes.client.models.V1ObjectMeta;
import io.kubernetes.client.models.V1Pod;
import io.kubernetes.client.models.V1PodBuilder;
import io.kubernetes.client.models.V1PodList;
import io.kubernetes.client.models.V1PodSpec;
import io.kubernetes.client.util.Config;
import java.io.IOException;
import java.util.Arrays;

/**
 * A simple example of how to use the Java API
 *
 * <p>Easiest way to run this: mvn exec:java
 * -Dexec.mainClass="io.kubernetes.client.examples.FluentExample"
 *
 * <p>From inside $REPO_DIR/examples
 */
public class FluentExample {
  public static void main(String[] args) throws IOException, ApiException {
    ApiClient client = Config.defaultClient();
    Configuration.setDefaultApiClient(client);

    CoreV1Api api = new CoreV1Api();

    V1Pod pod =
        new V1PodBuilder()
            .withNewMetadata()
            .withName("apod")
            .endMetadata()
            .withNewSpec()
            .addNewContainer()
            .withName("www")
            .withImage("nginx")
            .endContainer()
            .endSpec()
            .build();

    api.createNamespacedPod("default", pod, null, null, null);

    V1Pod pod2 =
        new V1Pod()
            .metadata(new V1ObjectMeta().name("anotherpod"))
            .spec(
                new V1PodSpec()
                    .containers(Arrays.asList(new V1Container().name("www").image("nginx"))));

    api.createNamespacedPod("default", pod2, null, null, null);

    V1PodList list =
        api.listNamespacedPod("default", null, null, null, null, null, null, null, null, null);
    for (V1Pod item : list.getItems()) {
      System.out.println(item.getMetadata().getName());
    }
  }
}
