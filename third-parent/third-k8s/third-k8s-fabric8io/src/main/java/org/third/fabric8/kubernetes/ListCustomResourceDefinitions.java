package org.third.fabric8.kubernetes;

import java.util.List;

import io.fabric8.kubernetes.api.model.apiextensions.CustomResourceDefinition;
import io.fabric8.kubernetes.api.model.apiextensions.CustomResourceDefinitionList;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientException;

/**
 */
public class ListCustomResourceDefinitions {
  public static void main(String[] args) {
    try {
      KubernetesClient client = new DefaultKubernetesClient();
      if (!client.supportsApiPath("/apis/apiextensions.k8s.io/v1beta1") && !client.supportsApiPath("/apis/apiextensions.k8s.io/v1")) {
        System.out.println("WARNING this cluster does not support the API Group apiextensions.k8s.io");
        return;
      }
      CustomResourceDefinitionList list = client.customResourceDefinitions().list();
      if (list == null) {
        System.out.println("ERROR no list returned!");
        return;
      }
      List<CustomResourceDefinition> items = list.getItems();
      for (CustomResourceDefinition item : items) {
        System.out.println("CustomResourceDefinition " + item.getMetadata().getName() + " has version: " + item.getApiVersion());
      }
    } catch (KubernetesClientException e) {
      System.out.println("Failed: " + e);
      e.printStackTrace();
    }
  }}
