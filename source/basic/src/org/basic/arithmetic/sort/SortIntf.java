package org.basic.arithmetic.sort;

import java.util.concurrent.atomic.AtomicInteger;

public interface SortIntf {
  AtomicInteger count = new AtomicInteger(0);

  public void sort(int[] data);
}
