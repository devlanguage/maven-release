package org.third.fabric8.openshift;

import io.fabric8.kubernetes.client.KubernetesClientException;
import io.fabric8.kubernetes.client.Watch;
import io.fabric8.kubernetes.client.Watcher;
import io.fabric8.openshift.api.model.BuildConfig;
import io.fabric8.openshift.client.DefaultOpenShiftClient;
import io.fabric8.openshift.client.OpenShiftClient;

public class WatchBuildConfigs {
  public static void main(String[] args) {
    try {
      OpenShiftClient client = new DefaultOpenShiftClient();
      String namespace = client.getNamespace();
      System.out.println("Watching BuildConfigs in namespace " + namespace);
      try (Watch watchable = client.buildConfigs().inNamespace(namespace).watch(new Watcher<BuildConfig>() {
        @Override
        public void eventReceived(Action action, BuildConfig resource) {
          System.out.println(">> Action: " + action + " on BuildConfig " + resource.getMetadata().getName() + " with version: " + resource.getApiVersion());
        }

        @Override
        public void onClose(KubernetesClientException cause) {
          System.out.println("Watch Closed: " + cause);
          if (cause != null) {
            cause.printStackTrace();
          }
        }
      })) {
        System.out.println("Created watchable " + watchable);
      }
    } catch (KubernetesClientException e) {
      System.out.println("Failed: " + e);
      e.printStackTrace();
    }
  }

}
