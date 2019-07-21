package org.third.fabric8.kubernetes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.fabric8.kubernetes.api.model.storage.StorageClassList;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientException;

public class ListStorageClassExample {

  private static final Logger logger = LoggerFactory.getLogger(ListStorageClassExample.class);

  public static void main(String[] args) {

    String master = "http://h9.test1.com:8080/";

    if (args.length == 1) {
      master = args[0];
    }

    io.fabric8.kubernetes.client.Config config = new io.fabric8.kubernetes.client.ConfigBuilder().withMasterUrl(master).build();

    try (final KubernetesClient client = new DefaultKubernetesClient(config)) {

      StorageClassList storageClassList = client.storage().storageClasses().list();

      logger.info(storageClassList.toString());

    } catch (KubernetesClientException e) {
      logger.error(e.getMessage(), e);
    }

  }
}
