package org.basic.arithmetic.structure.tree.keep_parent;

public class Node<E> { // 节点结构
  E data; // 真正的数据域
  int parentIndex; // 记录父节点的索引位置

  public Node() {

  }

  public Node(E data, int parentIndex) {
    this.data = data;
    this.parentIndex = parentIndex;
  }

  @Override
  public boolean equals(Object obj) {

    if (((Node) obj).data == this.data && ((Node) obj).parentIndex == this.parentIndex)
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