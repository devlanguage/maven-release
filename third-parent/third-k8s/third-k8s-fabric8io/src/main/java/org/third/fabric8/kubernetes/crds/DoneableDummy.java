package org.third.fabric8.kubernetes.crds;

import io.fabric8.kubernetes.api.builder.Function;
import io.fabric8.kubernetes.client.CustomResourceDoneable;

/**
 */
public class DoneableDummy extends CustomResourceDoneable<Dummy> {
  public DoneableDummy(Dummy resource, Function function) {
    super(resource, function);
  }
}
