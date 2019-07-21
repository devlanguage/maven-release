package org.third.fabric8.kubernetes.crds;

import io.fabric8.kubernetes.client.CustomResource;

/**
 */
public class Dummy extends CustomResource {
  private DummySpec spec;


  @Override
  public String toString() {
    return "Dummy{" +
        "apiVersion='" + getApiVersion() + '\'' +
        ", metadata=" + getMetadata() +
        ", spec=" + spec +
        '}';
  }

  public DummySpec getSpec() {
    return spec;
  }

  public void setSpec(DummySpec spec) {
    this.spec = spec;
  }
}
