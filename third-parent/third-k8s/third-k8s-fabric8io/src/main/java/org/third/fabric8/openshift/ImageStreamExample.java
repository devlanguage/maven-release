package org.third.fabric8.openshift;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.fabric8.kubernetes.api.model.ObjectReferenceBuilder;
import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.openshift.api.model.ImageStream;
import io.fabric8.openshift.api.model.ImageStreamBuilder;
import io.fabric8.openshift.api.model.TagReferenceBuilder;
import io.fabric8.openshift.client.DefaultOpenShiftClient;
import io.fabric8.openshift.client.OpenShiftClient;

public class ImageStreamExample {

  private static final Logger logger = LoggerFactory.getLogger(ImageStreamExample.class);

  public static void main(String[] args) throws InterruptedException {

    String namespace = "myproject";
    String master = "CLUSTER_URL";
    Config config = new ConfigBuilder().withMasterUrl(master).build();
    OpenShiftClient client = new DefaultOpenShiftClient(config);

    HashMap annotations = new HashMap();
    annotations.put("role", "jenkins-slave");
    annotations.put("slave-label", "jenkins-slave");

    try {

      ImageStream imageStream = new ImageStreamBuilder()
        .withNewMetadata()
          .withName("slave-jenkins")
        .endMetadata()
        .withNewSpec()
          .addToTags(0, new TagReferenceBuilder()
            .withName("base")
            .withFrom(new ObjectReferenceBuilder()
              .withKind("DockerImage")
              .withName("docker.io/openshift/jenkins-slave-maven-centos7:latest")
              .build()
            )
            .build()
          )
          .addToTags(1, new TagReferenceBuilder()
            .withAnnotations(annotations)
            .withName("latest")
            .withFrom(new ObjectReferenceBuilder()
              .withKind("ImageStreamTag")
              .withName("base")
              .build()
            )
            .build()
          )
        .endSpec()
        .build();

      log("Created ImageStream", client.imageStreams().inNamespace(namespace).create(imageStream));

    }finally {

      log("Tags in ImageStream are");
      log("First Tag is " + client.imageStreams().inNamespace(namespace).withName("slave-jenkins").get().getSpec().getTags().get(0).getName());
      log("Second Tag is " + client.imageStreams().inNamespace(namespace).withName("slave-jenkins").get().getSpec().getTags().get(1).getName());

      client.imageStreams().inNamespace(namespace).withName("slave-jenkins").delete();
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

