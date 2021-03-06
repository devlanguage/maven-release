package org.third.fabric8.openshift;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import io.fabric8.kubernetes.client.KubernetesClientException;
import io.fabric8.openshift.api.model.DeploymentConfig;
import io.fabric8.openshift.api.model.DeploymentConfigList;
import io.fabric8.openshift.api.model.DeploymentConfigSpec;
import io.fabric8.openshift.api.model.DoneableDeploymentConfig;
import io.fabric8.openshift.client.DefaultOpenShiftClient;
import io.fabric8.openshift.client.OpenShiftAPIGroups;
import io.fabric8.openshift.client.OpenShiftClient;
import io.fabric8.openshift.client.dsl.DeployableScalableResource;

public class DeploymentConfigScale {
  public static void main(String[] args) {
    if (args.length < 2) {
      System.err.println("Usage nameOfDC replicas");
      return;
    }
    String name = args[0];
    String replicaText = args[1];
    int replicas;
    try {
      replicas = Integer.parseInt(replicaText);
    } catch (NumberFormatException e) {
      System.out.println("Could not parse integer " + replicaText + " due to: " + e);
      return;
    }
    try {
      Integer oldReplicas = 0;

      OpenShiftClient client = new DefaultOpenShiftClient();
      if (!client.supportsOpenShiftAPIGroup(OpenShiftAPIGroups.APPS)) {
        System.out.println("WARNING this cluster does not support the API Group " + OpenShiftAPIGroups.APPS);
        return;
      }
      DeployableScalableResource<DeploymentConfig, DoneableDeploymentConfig> resource = client.deploymentConfigs().withName(name);
      DeploymentConfig deploymentConfig = resource.get();
      if (deploymentConfig == null) {
        System.out.println("Could not find a DeploymentConfig for name: " + name);
        return;
      }
      try {
        DeploymentConfigSpec spec = deploymentConfig.getSpec();
        oldReplicas = spec.getReplicas();
        spec.setReplicas(replicas);
        DeploymentConfig updated = resource.patch(deploymentConfig);
        System.out.println("Updated the DeploymentConfig " + name + " version: " + deploymentConfig.getApiVersion() + " with replicas: " + replicas + " to resourceVersion: " + updated.getMetadata().getResourceVersion());
      } catch (Exception e) {
        System.out.println("Failed to update the DeploymentConfig " + name + " with replicas: " + replicas);
        e.printStackTrace();
      }


      // now lets find the DC via list
      DeploymentConfigList list = client.deploymentConfigs().list();
      assertNotNull("No DeploymentConfigList returned", list);
      List<DeploymentConfig> items = list.getItems();
      assertNotNull("No DeploymentConfigList.getItems() returned", items);

      DeploymentConfig found = null;
      for (DeploymentConfig item : items) {
        if (name.equals(item.getMetadata().getName())) {
          found = item;
          break;
        }
      }
      assertNotNull("Could not find DeploymentConfig in list.getItems() for name: " + name, found);
      found.getSpec().setReplicas(oldReplicas);

      try {
        DeploymentConfig updated = resource.patch(found);
        System.out.println("Updated the list.item DeploymentConfig " + name + " version: " + found.getApiVersion() + " with replicas: " + oldReplicas + " to resourceVersion: " + updated.getMetadata().getResourceVersion());
      } catch (Exception e) {
        System.out.println("Failed to update the list.item DeploymentConfig " + name + " with replicas: " + oldReplicas);
        e.printStackTrace();
      }

    } catch (KubernetesClientException e) {
      System.out.println("Failed: " + e);
      e.printStackTrace();
    }
  }

}
