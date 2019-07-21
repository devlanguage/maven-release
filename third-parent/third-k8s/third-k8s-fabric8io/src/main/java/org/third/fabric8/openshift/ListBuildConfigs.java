package org.third.fabric8.openshift;

import java.util.List;

import io.fabric8.kubernetes.client.KubernetesClientException;
import io.fabric8.openshift.api.model.BuildConfig;
import io.fabric8.openshift.api.model.BuildConfigList;
import io.fabric8.openshift.client.DefaultOpenShiftClient;
import io.fabric8.openshift.client.OpenShiftAPIGroups;
import io.fabric8.openshift.client.OpenShiftClient;

public class ListBuildConfigs {
  public static void main(String[] args) {
    try {
      OpenShiftClient client = new DefaultOpenShiftClient();
      if (!client.supportsOpenShiftAPIGroup(OpenShiftAPIGroups.BUILD)) {
        System.out.println("WARNING this cluster does not support the API Group " + OpenShiftAPIGroups.BUILD);
        return;
      }
      BuildConfigList list = client.buildConfigs().list();
      if (list == null) {
        System.out.println("ERROR no list returned!");
        return;
      }
      List<BuildConfig> items = list.getItems();
      for (BuildConfig item : items) {
        System.out.println("BuildConfig " + item.getMetadata().getName() + " has version: " + item.getApiVersion());
      }
    } catch (KubernetesClientException e) {
      System.out.println("Failed: " + e);
      e.printStackTrace();
    }
  }

}
