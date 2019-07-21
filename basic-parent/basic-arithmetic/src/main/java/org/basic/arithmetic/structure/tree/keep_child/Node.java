package org.basic.arithmetic.structure.tree.keep_child;

/**
 * 实际应用的Node对象
 * 
 * @author liuyan
 */
public class Node<E> {
  E data;
  int index;
  SonNode firstSonNode;

  public Node(E data, int index) {
    this.data = data;
    this.index = index;
  }

  public Node(E data, int index, SonNode sonNode) {
    this.data = data;
    this.index = index;
    this.firstSonNode = sonNode;
  }

  @Override
  public boolean equals(Object obj) {

    if (((Node) obj).data == this.data && ((Node) obj).index == this.index)
      return true;

    return false;
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  @Override
  public String toString() {
    return (String) data;
  }
}
