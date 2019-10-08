package org.third.fabric8.kubernetes;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.third.fabric8.kubernetes.crds.DoneableDummy;
import org.third.fabric8.kubernetes.crds.Dummy;
import org.third.fabric8.kubernetes.crds.DummyList;
import org.third.fabric8.kubernetes.crds.DummySpec;

import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.fabric8.kubernetes.api.model.RootPaths;
import io.fabric8.kubernetes.api.model.apiextensions.CustomResourceDefinition;
import io.fabric8.kubernetes.api.model.apiextensions.CustomResourceDefinitionBuilder;
import io.fabric8.kubernetes.api.model.apiextensions.CustomResourceDefinitionList;
import io.fabric8.kubernetes.client.CustomResourceList;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientException;
import io.fabric8.kubernetes.client.Watcher;
import io.fabric8.kubernetes.client.dsl.MixedOperation;
import io.fabric8.kubernetes.client.dsl.NonNamespaceOperation;
import io.fabric8.kubernetes.client.dsl.Resource;
import io.fabric8.kubernetes.internal.KubernetesDeserializer;

public class CRDExample {

  private static final Logger logger = LoggerFactory.getLogger(CRDExample.class);

  public static String DUMMY_CRD_GROUP = "demo.fabric8.io";
  public static String DUMMY_CRD_NAME = "dummies." +  DUMMY_CRD_GROUP;

  private static boolean logRootPaths = false;

  private static String resourceScope(boolean resourceNamespaced) {
    if (resourceNamespaced) {
      return "Namespaced";
    }
    return "Cluster";
  }

  /**
   * Example of Cluster and Namespaced scoped K8S Custom Resources.
   * To test Cluster scoped resource use "--cluster" as first argument.
   * To test Namespaced resource provide namespace as first argument (namespace must exists in K8S).
   *
   * @param args Either "--cluster" or namespace name.
   */
  public static void main(String[] args) {
    boolean resourceNamespaced = true;
    String namespace = null;
    if (args.length > 0) {
      if ("--cluster".equals(args[0])) {
        resourceNamespaced = false;
      } else {
        namespace = args[0];
      }
    }
    try (final KubernetesClient client = new DefaultKubernetesClient()) {
      if (resourceNamespaced) {
        if (namespace == null) {
          namespace = client.getNamespace();
        }
        if (namespace == null) {
          System.err.println("No namespace specified and no default defined!");
          return;
        }

        System.out.println("Using namespace: " + namespace);
      } else {
        System.out.println("Creating cluster scoped resource");
      }

      if (logRootPaths) {
        RootPaths rootPaths = client.rootPaths();
        if (rootPaths != null) {
          List<String> paths = rootPaths.getPaths();
          if (paths != null) {
            System.out.println("Supported API Paths:");
            for (String path : paths) {
              System.out.println("    " + path);
            }
            System.out.println();
          }
        }
      }

      CustomResourceDefinitionList crds = client.customResourceDefinitions().list();
      List<CustomResourceDefinition> crdsItems = crds.getItems();
      System.out.println("Found " + crdsItems.size() + " CRD(s)");
      CustomResourceDefinition dummyCRD = null;
      for (CustomResourceDefinition crd : crdsItems) {
        ObjectMeta metadata = crd.getMetadata();
        if (metadata != null) {
          String name = metadata.getName();
          System.out.println("    " + name + " => " + metadata.getSelfLink());
          if (DUMMY_CRD_NAME.equals(name)) {
            dummyCRD = crd;
          }
        }
      }
      if (dummyCRD != null) {
        System.out.println("Found CRD: " + dummyCRD.getMetadata().getSelfLink());
      } else {
        dummyCRD = new CustomResourceDefinitionBuilder().
            withApiVersion("apiextensions.k8s.io/v1beta1").
            withNewMetadata().withName(DUMMY_CRD_NAME).endMetadata().
            withNewSpec().withGroup(DUMMY_CRD_GROUP).withVersion("v1").withScope(resourceScope(resourceNamespaced)).
              withNewNames().withKind("Dummy").withShortNames("dummy").withPlural("dummies").endNames().endSpec().
            build();

        client.customResourceDefinitions().create(dummyCRD);
        System.out.println("Created CRD " + dummyCRD.getMetadata().getName());
      }

      KubernetesDeserializer.registerCustomKind(DUMMY_CRD_GROUP + "/v1", "Dummy", Dummy.class);

      // lets create a client for the CRD
      NonNamespaceOperation<Dummy, DummyList, DoneableDummy, Resource<Dummy, DoneableDummy>> dummyClient = client.customResources(dummyCRD, Dummy.class, DummyList.class, DoneableDummy.class);
      if (resourceNamespaced) {
        dummyClient = ((MixedOperation<Dummy, DummyList, DoneableDummy, Resource<Dummy, DoneableDummy>>) dummyClient).inNamespace(namespace);
      }
      CustomResourceList<Dummy> dummyList = dummyClient.list();
      List<Dummy> items = dummyList.getItems();
      System.out.println("  found " + items.size() + " dummies");
      for (Dummy item : items) {
        System.out.println("    " + item);
      }

      Dummy dummy = new Dummy();
      ObjectMeta metadata = new ObjectMeta();
      metadata.setName("foo");
      dummy.setMetadata(metadata);
      DummySpec dummySpec = new DummySpec();
      Date now = new Date();
      dummySpec.setBar("beer: " + now);
      dummySpec.setFoo("cheese: " + now);
      dummy.setSpec(dummySpec);

      Dummy created = dummyClient.createOrReplace(dummy);

      System.out.println("Upserted " + dummy);

      created.getSpec().setBar("otherBar");

      dummyClient.createOrReplace(created);

      System.out.println("Watching for changes to Dummies");
      dummyClient.withResourceVersion(created.getMetadata().getResourceVersion()).watch(new Watcher<Dummy>() {
        @Override
        public void eventReceived(Action action, Dummy resource) {
          System.out.println("==> " + action + " for " + resource);
          if (resource.getSpec() == null) {
            logger.error("No Spec for resource " + resource);
          }
        }

        @Override
        public void onClose(KubernetesClientException cause) {
        }
      });

      System.in.read();
      
    } catch (KubernetesClientException e) {
      logger.error(e.getMessage(), e);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
  }

}