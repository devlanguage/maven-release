package org.third.fabric8.openshift;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.openshift.api.model.ImageStreamTag;
import io.fabric8.openshift.api.model.ImageStreamTagBuilder;
import io.fabric8.openshift.client.DefaultOpenShiftClient;
import io.fabric8.openshift.client.OpenShiftClient;

public class ImageStreamTagExample {

  private static final Logger logger = LoggerFactory.getLogger(ImageStreamTagExample.class);

  public static void main(String[] args) throws InterruptedException {

    String namespace = "myproject";
    String master = "CLUSTER_URL";
    Config config = new ConfigBuilder().withMasterUrl(master).build();
    OpenShiftClient client = new DefaultOpenShiftClient(config);

    try {

      ImageStreamTag istag = new ImageStreamTagBuilder().withNewMetadata().withName("bar1:1.0.12").endMetadata()
        .withNewTag().withNewFrom().withKind("DockerImage").withName("openshift/wildfly-81-centos7:latest").endFrom().endTag()
        .build();

      log("Created istag", client.imageStreamTags().inNamespace(namespace).create(istag));
      
      Thread.sleep(30000);

    }finally {

      log("ImageStreamTags are :");
      log(client.imageStreamTags().inNamespace(namespace).withName("bar1:1.0.12").get().toString());

      log("ImageStreamTags using list are :");
      log(client.imageStreamTags().list().getItems().get(0).toString());
      log("Deleted istag",client.imageStreamTags().withName("bar1:1.0.12").delete());
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
