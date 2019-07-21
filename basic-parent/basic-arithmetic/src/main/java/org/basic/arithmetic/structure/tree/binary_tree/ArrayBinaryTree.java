package org.basic.arithmetic.structure.tree.binary_tree;

/**
 * 顺序二叉树
 * 
 * @author liuyan
 */
public class ArrayBinaryTree<T> {

  // 树的默认深度
  private static final int DefTreeDeep = 4;

  // 节点数组
  private Object[] datas;

  // 指定的树的深度
  private int treeDeep;

  // 实际的数组个数
  private int arraySize;

  /**
   * 默认构造函数
   */
  public ArrayBinaryTree() {
    // 设置默认的树深度
    treeDeep = DefTreeDeep;
    // 2的DefTreeDeep次方-1个数组元素
    arraySize = (int) Math.pow(2, DefTreeDeep) - 1;
    datas = new Object[arraySize];
  }

  /**
   * 指定深度构建二叉树
   * 
   * @param deep
   */
  public ArrayBinaryTree(int deep) {
    // 按指定深度
    treeDeep = deep;
    arraySize = (int) Math.pow(2, treeDeep) - 1;
    datas = new Object[arraySize];
  }

  /**
   * 指定深度和指定根节点构建二叉树
   * 
   * @param deep
   * @param data
   */
  public ArrayBinaryTree(int deep, T data) {
    // 按指定深度
    treeDeep = deep;
    arraySize = (int) Math.pow(2, treeDeep) - 1;
    datas = new Object[arraySize];
    datas[0] = data;
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
    if (index * 2 + 2 > arraySize || datas[index] == null) {
      throw new RuntimeException("标记无效");
    }
    if (isLeft) {
      datas[index * 2 + 1] = data;
    } else {
      datas[index * 2 + 2] = data;
    }
    return true;
  }

  /**
   * 判断二叉树是否为空
   * 
   * @return
   */
  public boolean isEmpty() {
    return arraySize == 0;
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
  @SuppressWarnings("unchecked")
  public T getParent(int index) {
    if (index > arraySize || datas[index] == null) {
      throw new RuntimeException("标记无效");
    }
    if (datas[(index - 1) / 2] == null) {
      throw new RuntimeException("无父节点");
    }
    return (T) datas[(index - 1) / 2];
  }

  /**
   * 返回左子节点
   * 
   * @return
   */
  @SuppressWarnings("unchecked")
  public T getLeftNode(int index) {
    if (index * 2 + 2 > arraySize || datas[index] == null) {
      throw new RuntimeException("标记无效");
    }
    return (T) datas[index * 2 + 1];
  }

  /**
   * 返回右子节点
   * 
   * @return
   */
  @SuppressWarnings("unchecked")
  public T getRightNode(int index) {
    if (index * 2 + 2 > arraySize || datas[index] == null) {
      throw new RuntimeException("标记无效");
    }
    return (T) datas[index * 2 + 2];
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
    for (int i = 0; i < datas.length; i++) {
      str.append("[" + datas[i] + "],");
    }

    if (datas.length > 0) {
      return str.substring(0, str.lastIndexOf(",")) + "]";
    }
    return str.append("]").toString();
  }

  // 测试代码如下
  public static void main(String[] args) {
    ArrayBinaryTree<String> arrayBinaryTree = new ArrayBinaryTree<String>(4, "汉献帝");
    System.out.println(arrayBinaryTree);
    arrayBinaryTree.addNode(0, "刘备", true);
    arrayBinaryTree.addNode(0, "曹操", false);
    arrayBinaryTree.addNode(1, "关羽", true);
    arrayBinaryTree.addNode(1, "张飞", false);
    arrayBinaryTree.addNode(2, "张辽", true);
    arrayBinaryTree.addNode(2, "许褚", false);
    System.out.println(arrayBinaryTree);
    System.out.println(arrayBinaryTree.getLeftNode(1));
    System.out.println(arrayBinaryTree.getRightNode(0));
    System.out.println(arrayBinaryTree.isEmpty());
    System.out.println(arrayBinaryTree.getParent(4));
  }
}
