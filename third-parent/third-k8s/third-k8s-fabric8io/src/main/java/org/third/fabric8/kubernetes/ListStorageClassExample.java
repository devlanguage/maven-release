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



    try (final KubernetesClient client = new DefaultKubernetesClient(K8sUtil.getK8sConfig())) {

      StorageClassList storageClassList = client.storage().storageClasses().list();

      logger.info(storageClassList.toString());

    } catch (KubernetesClientException e) {
      logger.error(e.getMessage(), e);
    }

  }
}
