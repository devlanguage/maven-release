package org.basic.arithmetic.structure.tree.keep_child;


/**
 * 记录子节点对象
 * 
 * @author liuyan
 */
public class SonNode<E> {
  E data;
  int index;
  SonNode<E> sonNode;

  public SonNode(E data, int index, SonNode sonNode) {
    this.data = data;
    this.index = index;
    this.sonNode = sonNode;
  }

  @Override
  public String toString() {
    return (String) data;
  }

  public Node<E> paseNode() {
    return new Node<E>(data, index);
  }
}