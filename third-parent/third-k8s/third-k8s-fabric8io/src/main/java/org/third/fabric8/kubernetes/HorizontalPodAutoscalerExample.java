package org.third.fabric8.kubernetes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.fabric8.kubernetes.api.model.HorizontalPodAutoscaler;
import io.fabric8.kubernetes.api.model.HorizontalPodAutoscalerBuilder;
import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientException;

public class HorizontalPodAutoscalerExample {
  private static final Logger logger = LoggerFactory.getLogger(HorizontalPodAutoscalerExample.class);

  public static void main(String[] args) {
    String master = "https://192.168.42.193:8443/";
    if (args.length == 1) {
      master = args[0];
    }

    Config config = new ConfigBuilder().withMasterUrl(master).build();
    try (final KubernetesClient client = new DefaultKubernetesClient(config)) {
      HorizontalPodAutoscaler horizontalPodAutoscaler = new HorizontalPodAutoscalerBuilder()
        .withNewMetadata().withName("the-hpa").withNamespace("default").endMetadata()
        .withNewSpec()
        .withNewScaleTargetRef()
        .withApiVersion("apps/v1")
        .withKind("Deployment")
        .withName("the-deployment")
        .endScaleTargetRef()
        .withMinReplicas(1)
        .withMaxReplicas(10)
        .withTargetCPUUtilizationPercentage(50)
        .endSpec()
        .build();

      client.autoscaling().horizontalPodAutoscalers().inNamespace("default").create(horizontalPodAutoscaler);
    } catch (KubernetesClientException e) {
      logger.error(e.getMessage(), e);
    }
  }
}
