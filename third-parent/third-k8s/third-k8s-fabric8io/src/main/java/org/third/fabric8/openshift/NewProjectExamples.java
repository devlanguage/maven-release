package org.third.fabric8.openshift;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.openshift.api.model.ProjectRequest;
import io.fabric8.openshift.client.DefaultOpenShiftClient;
import io.fabric8.openshift.client.OpenShiftClient;

public class NewProjectExamples {
  private static final Logger logger = LoggerFactory.getLogger(NewProjectExamples.class);

  public static void main(String[] args) throws InterruptedException {
    String master = "https://h9.test1.com:8443/";
    if (args.length == 1) {
      master = args[0];
    }

    Config config = new ConfigBuilder().withMasterUrl(master).build();

    try (OpenShiftClient client = new DefaultOpenShiftClient(config)) {
      ProjectRequest request = null;
      try {
        request = client.projectrequests().createNew().withNewMetadata().withName("thisisatest").endMetadata().withDescription("Jimmi").withDisplayName("Jimmi").done();
      } finally {
        if (request != null) {
          client.projects().withName(request.getMetadata().getName()).delete();
        }
      }
    }
  }


  private static void log(String action, Object obj) {
    logger.info("{}: {}", action, obj);
  }

  private static void log(String action) {
    logger.info(action);
  }
}
