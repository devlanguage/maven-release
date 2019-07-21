package org.third.fabric8.kubernetes;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.fabric8.kubernetes.api.model.DoneablePod;
import io.fabric8.kubernetes.api.model.HasMetadata;
import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.api.model.PodList;
import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientException;
import io.fabric8.kubernetes.client.dsl.NonNamespaceOperation;
import io.fabric8.kubernetes.client.dsl.PodResource;

public class CreatePod {

  private static final Logger logger = LoggerFactory.getLogger(CreatePod.class);

  public static void main(String[] args) {
    if (args.length == 0) {
      System.out.println("Usage: podJsonFileName <token> <namespace>");
      return;
    }
    String fileName = args[0];
    String namespace = null;
    if (args.length > 2) {
      namespace = args[2];
    }

    File file = new File(fileName);
    if (!file.exists() || !file.isFile()) {
      System.err.println("File does not exist: " + fileName);
      return;
    }

    ConfigBuilder builder = new ConfigBuilder();
    if (args.length > 1) {
      builder.withOauthToken(args[1]);
    }
    Config config = builder.build();
    try (final KubernetesClient client = new DefaultKubernetesClient(config)) {
      if (namespace == null) {
        namespace = client.getNamespace();
      }

      List<HasMetadata> resources = client.load(new FileInputStream(fileName)).get();
      if (resources.isEmpty()) {
        System.err.println("No resources loaded from file: " +fileName);
        return;
      }
      HasMetadata resource = resources.get(0);
      if (resource instanceof Pod){
        Pod pod = (Pod) resource;
        System.out.println("Creating pod in namespace " + namespace);
        NonNamespaceOperation<Pod, PodList, DoneablePod, PodResource<Pod, DoneablePod>> pods = client.pods().inNamespace(namespace);
        Pod result = pods.create(pod);
        System.out.println("Created pod " + result.getMetadata().getName());
      } else {
        System.err.println("Loaded resource is not a Pod! " + resource);
      }
    } catch (KubernetesClientException e) {
      logger.error(e.getMessage(), e);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
  }

}
