package org.third.fabric8.kubernetes;

import java.util.List;
import java.util.Map;

import io.fabric8.kubernetes.api.builder.Visitor;
import io.fabric8.kubernetes.api.model.HasMetadata;
import io.fabric8.kubernetes.api.model.ObjectMetaBuilder;
import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.utils.Utils;

public class LoadMultipleDocumentsFromFileExample {

  public static void main(String[] args) throws InterruptedException {
    String master = "https://h9.test1.com:8443/";
    if (args.length == 1) {
      master = args[0];
    }

    Config config = new ConfigBuilder().build();
    KubernetesClient client = new DefaultKubernetesClient(config);

    List<HasMetadata> list = client.load(LoadMultipleDocumentsFromFileExample.class.getResourceAsStream("/multiple-document-template.yml")).get();
    System.out.println("Found in file:" + list.size() + " items.");
    for (HasMetadata meta : list) {
      System.out.println(display(meta));
    }

    list = client.load(LoadMultipleDocumentsFromFileExample.class.getResourceAsStream("/multiple-document-template.yml")).accept((Visitor<ObjectMetaBuilder>) item -> item.addToLabels("visitorkey", "visitorvalue")).get();

    System.out.println("Visited:" + list.size() + " items.");
    for (HasMetadata meta : list) {
      System.out.println(display(meta));
    }
  }

  private static String display(HasMetadata item) {
    StringBuilder sb = new StringBuilder();
    sb.append("[ ");
    if (Utils.isNotNullOrEmpty(item.getKind())) {
      sb.append(" Kind:").append(item.getKind());
    }
    if (Utils.isNotNullOrEmpty(item.getMetadata().getName())) {
      sb.append(" Name:").append(item.getMetadata().getName());
    }

    if (item.getMetadata().getLabels() != null && !item.getMetadata().getLabels().isEmpty()) {
      sb.append(" Lables: [ ");
      for (Map.Entry<String, String> entry : item.getMetadata().getLabels().entrySet()) {
        sb.append(entry.getKey()).append(":").append(entry.getValue()).append(" ");
      }
      sb.append("]");
    }
    sb.append(" ]");
    return sb.toString();
  }
}
