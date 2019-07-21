package org.third.fabric8.openshift;

import io.fabric8.kubernetes.client.KubernetesClientException;
import io.fabric8.openshift.api.model.ImageStream;
import io.fabric8.openshift.api.model.ImageStreamList;
import io.fabric8.openshift.client.DefaultOpenShiftClient;
import io.fabric8.openshift.client.OpenShiftAPIGroups;
import io.fabric8.openshift.client.OpenShiftClient;

import java.util.List;

public class ListImageStreams {
  public static void main(String[] args) {
    try {
      OpenShiftClient client = new DefaultOpenShiftClient();
      if (!client.supportsOpenShiftAPIGroup(OpenShiftAPIGroups.IMAGE)) {
        System.out.println("WARNING this cluster does not support the API Group " + OpenShiftAPIGroups.IMAGE);
        return;
      }
      ImageStreamList list = client.imageStreams().list();
      if (list == null) {
        System.out.println("ERROR no list returned!");
        return;
      }
      List<ImageStream> items = list.getItems();
      for (ImageStream item : items) {
        System.out.println("ImageStream " + item.getMetadata().getName() + " has version: " + item.getApiVersion());
      }
      System.out.println("Found " + items.size() + " ImageStream(s)");
    } catch (KubernetesClientException e) {
      System.out.println("Failed: " + e);
      e.printStackTrace();
    }
  }

}
