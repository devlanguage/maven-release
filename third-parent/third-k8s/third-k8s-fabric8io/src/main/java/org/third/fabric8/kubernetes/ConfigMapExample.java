package org.third.fabric8.kubernetes;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.fabric8.kubernetes.api.model.ConfigMap;
import io.fabric8.kubernetes.api.model.ConfigMapBuilder;
import io.fabric8.kubernetes.api.model.DoneableConfigMap;
import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.dsl.Resource;

public class ConfigMapExample {
  private static final Logger logger = LoggerFactory.getLogger(ConfigMapExample.class);

  public static void main(String[] args) throws InterruptedException {
    Config config = new ConfigBuilder().build();
    KubernetesClient client = new DefaultKubernetesClient(config);

    String namespace = null;
    if (args.length > 0) {
      namespace = args[0];
    }
    if (namespace == null) {
      namespace = client.getNamespace();
    }
    if (namespace == null) {
      namespace = "default";
    }

    String name = "cheese";
    try {
      Resource<ConfigMap, DoneableConfigMap> configMapResource = client.configMaps().inNamespace(namespace).withName(name);


      ConfigMap configMap = configMapResource.createOrReplace(new ConfigMapBuilder().
          withNewMetadata().withName(name).endMetadata().
          addToData("foo", "" + new Date()).
          addToData("bar", "beer").
          build());

      log("Upserted ConfigMap at " + configMap.getMetadata().getSelfLink() + " data " + configMap.getData());

    } finally {
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
