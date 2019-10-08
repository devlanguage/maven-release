package org.third.fabric8.kubernetes;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.fabric8.kubernetes.api.model.storage.StorageClass;
import io.fabric8.kubernetes.api.model.storage.StorageClassBuilder;
import io.fabric8.kubernetes.api.model.storage.StorageClassList;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientException;

public class StorageClassExamples {

  private static final Logger logger = LoggerFactory.getLogger(StorageClassExamples.class);

  public static void main(String[] args) {

    try (final KubernetesClient client = new DefaultKubernetesClient(K8sUtil.getK8sConfig())) {

      //list all storage classes
      StorageClassList storageClassList = client.storage().storageClasses().list();
      logger.info("List of storage classes: {}", storageClassList.toString());

      //create new storage class

      String name = UUID.randomUUID().toString();
      ObjectMeta metadata = new ObjectMeta();
      metadata.setName(name);

      Map<String, String> parameters = new HashMap<>();
      parameters.put("resturl", "http://192.168.10.100:8080");
      parameters.put("restuser", "");
      parameters.put("secretNamespace", "");
      parameters.put("secretName", "");
      parameters.put("key", "value1");

      StorageClass storageClass = new StorageClassBuilder().withApiVersion("storage.k8s.io/v1")
                                                            .withKind("StorageClass")
                                                            .withMetadata(metadata)
                                                            .withParameters(parameters)
                                                            .withProvisioner("k8s.io/minikube-hostpath")
                                                            .build();

      storageClass = client.storage().storageClasses().create(storageClass);
      logger.info("Newly created storage class details: {}", storageClass.toString());

      //list all storage classes
      storageClassList = client.storage().storageClasses().list();
      logger.info("List of storage classes: {}", storageClassList.toString());

      //update storage class. add label
      storageClass = client.storage().storageClasses().withName(name).edit().editMetadata().addToLabels("testLabel", "testLabelValue").endMetadata().done();

      //list all storage classes
      storageClassList = client.storage().storageClasses().list();
      logger.info("List of storage classes: {}", storageClassList.toString());


      //delete storage class
      boolean isDeleteSuccessful = client.storage().storageClasses().delete(storageClass);
      logger.info("Storage Class resource successfully deleted: {}", isDeleteSuccessful);


    } catch (KubernetesClientException e) {
      logger.error(e.getMessage(), e);
    }

  }
}
