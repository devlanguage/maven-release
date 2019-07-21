package org.third.fabric8.openshift;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.fabric8.kubernetes.api.model.ServiceAccount;
import io.fabric8.kubernetes.api.model.ServiceAccountBuilder;
import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.openshift.api.model.ProjectRequest;
import io.fabric8.openshift.api.model.ProjectRequestBuilder;
import io.fabric8.openshift.client.OpenShiftClient;

public class DeploymentConfigExamples {
  private static final Logger logger = LoggerFactory.getLogger(DeploymentConfigExamples.class);

  public static void main(String[] args) throws InterruptedException {
    Config config = new ConfigBuilder().build();
    KubernetesClient kubernetesClient = new DefaultKubernetesClient(config);
    OpenShiftClient client = kubernetesClient.adapt(OpenShiftClient.class);

    try {
      ProjectRequest  projectRequest = new ProjectRequestBuilder()
          .withNewMetadata()
            .withName("thisisatest")
            .addToLabels("project", "thisisatest")
          .endMetadata()
          .build();


      log("Created project", client.projectrequests().create(projectRequest));

      ServiceAccount fabric8 = new ServiceAccountBuilder().withNewMetadata().withName("fabric8").endMetadata().build();

      client.serviceAccounts().inNamespace("thisisatest").createOrReplace(fabric8);

      log("Created deployment", client.deploymentConfigs().inNamespace("thisisatest").createOrReplaceWithNew()
        .withNewMetadata()
          .withName("nginx")
        .endMetadata()
        .withNewSpec()
          .withReplicas(1)
          .addNewTrigger()
            .withType("ConfigChange")
          .endTrigger()
          .addToSelector("app", "nginx")
          .withNewTemplate()
            .withNewMetadata()
              .addToLabels("app", "nginx")
            .endMetadata()
            .withNewSpec()
              .addNewContainer()
                .withName("nginx")
                .withImage("nginx")
                .addNewPort()
                  .withContainerPort(80)
                .endPort()
              .endContainer()
            .endSpec()
          .endTemplate()
        .endSpec()
        .done());


      client.deploymentConfigs().inNamespace("thisisatest").withName("nginx").scale(2, true);
      log("Created pods:", client.pods().inNamespace("thisisatest").list().getItems());
      client.deploymentConfigs().inNamespace("thisisatest").withName("nginx").delete();
      log("Pods:", client.pods().inNamespace("thisisatest").list().getItems());
      log("Replication Controllers:", client.replicationControllers().inNamespace("thisisatest").list().getItems());

      log("Done.");
    }finally {
     // client.projects().withName("thisisatest").delete();
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
