package org.basic.arithmetic.structure.tree.binary_tree;

/**
 * 二叉链表二叉树
 * 
 * @author liuyan
 */
public class TwoLinkedBinaryTree<T> {

  // 树的默认深度
  private static final int DefTreeDeep = 4;

  // 节点数组
  private TwoLinkNode<T>[] datas;

  // 指定的树的深度
  private int treeDeep;

  // 实际的数组个数
  private int arraySize;

  // 节点个数
  private int nodeSize;

  @SuppressWarnings("unchecked")
  public TwoLinkedBinaryTree() {
    treeDeep = DefTreeDeep;

    arraySize = (int) Math.pow(2, treeDeep) - 1;

    datas = new TwoLinkNode[arraySize];
  }

  @SuppressWarnings("unchecked")
  public TwoLinkedBinaryTree(int deep, T data) {
    treeDeep = DefTreeDeep;

    arraySize = (int) Math.pow(2, treeDeep) - 1;

    datas = new TwoLinkNode[arraySize];

    TwoLinkNode<T> twoLinkNode = new TwoLinkNode<T>();
    twoLinkNode.data = data;
    twoLinkNode.leftChildIndex = 1;
    twoLinkNode.rightChildIndex = 2;
    twoLinkNode.index = 0;

    datas[0] = twoLinkNode;
    nodeSize = 1;
  }

  /**
   * 为指定节点索引增加子节点
   * 
   * @param index
   * @param data
   * @param isLeft
   * @return
   */
  public boolean addNode(int index, T data, boolean isLeft) {
    if (index + 1 > arraySize || datas[index] == null) {
      throw new RuntimeException("标记无效");
    }

    for (int i = index + 1; i < arraySize; i++) {
      if (datas[i] == null) {
        TwoLinkNode<T> twoLinkNode = new TwoLinkNode<T>();
        twoLinkNode.data = data;
        twoLinkNode.index = i;
        datas[i] = twoLinkNode;
        if (isLeft) {
          datas[index].leftChildIndex = i;
        } else {
          datas[index].rightChildIndex = i;
        }
        nodeSize++;
        return true;
      }
    }
    return false;
  }

  /**
   * 判断二叉树是否为空
   * 
   * @return
   */
  public boolean isEmpty() {
    return nodeSize == 0;
  }

  /**
   * 返回根节点
   * 
   * @return
   */
  @SuppressWarnings("unchecked")
  public T getRoot() {
    return (T) datas[0];
  }

  /**
   * 返回指定节点的父节点
   * 
   * @return
   */
  public T getParent(int index) {
    if (index > arraySize || datas[index] == null) {
      throw new RuntimeException("标记无效");
    }
    for (int i = 0; i < arraySize; i++) {
      if (datas[i] != null) {
        if (datas[i].leftChildIndex == index || datas[i].rightChildIndex == index) {
          return datas[i].data;
        }
      }
    }
    return null;
  }

  /**
   * 返回左子节点
   * 
   * @return
   */
  public T getLeftNode(int index) {
    if (index + 2 > arraySize || datas[index] == null) {
      throw new RuntimeException("标记无效");
    }
    return (T) datas[datas[index].leftChildIndex].data;
  }

  /**
   * 返回右子节点
   * 
   * @return
   */
  public T getRightNode(int index) {
    if (index + 2 > arraySize || datas[index] == null) {
      throw new RuntimeException("标记无效");
    }
    return (T) datas[datas[index].rightChildIndex].data;
  }

  /**
   * 返回树的深度
   * 
   * @return
   */
  public int getTreeDeep() {
    return treeDeep;
  }

  /**
   * 返回指定节点的索引位置
   * 
   * @param data
   * @return
   */
  public int getNodeIndex(T data) {

    for (int i = 0; i < arraySize; i++) {
      if (data == datas[i]) {
        return i;
      }
    }
    return -1;
  }

  @Override
  public String toString() {

    StringBuffer str = new StringBuffer("[");

    for (int i = 0; i < nodeSize; i++) {
      if (datas[i].data != null) {
        str.append("[" + datas[i].data + "],");
      }
    }

    if (datas.length > 0) {
      return str.substring(0, str.lastIndexOf(",")) + "]";
    }

    return str.append("]").toString();
  }

}
