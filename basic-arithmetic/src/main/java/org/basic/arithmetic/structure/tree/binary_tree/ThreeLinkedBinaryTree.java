
/**
 * 三叉链表的实现
 * 
 * @author liuyan
 */
public class ThreeLinkedBinaryTree<T> {

  // 树的默认深度
  private static final int DefTreeDeep = 4;

  // 节点数组
  private ThreeLinkNode<T>[] datas;

  // 指定的树的深度
  private int treeDeep;

  // 实际的数组个数
  private int arraySize;

  // 节点个数
  private int nodeSize;

  @SuppressWarnings("unchecked")
  public ThreeLinkedBinaryTree() {
    treeDeep = DefTreeDeep;

    arraySize = (int) Math.pow(2, treeDeep) - 1;

    datas = new ThreeLinkNode[arraySize];
  }

  @SuppressWarnings("unchecked")
  public ThreeLinkedBinaryTree(int deep, T data) {
    treeDeep = DefTreeDeep;

    arraySize = (int) Math.pow(2, treeDeep) - 1;

    datas = new ThreeLinkNode[arraySize];

    ThreeLinkNode<T> threeLinkNode = new ThreeLinkNode<T>();
    threeLinkNode.data = data;
    threeLinkNode.leftChildIndex = 1;
    threeLinkNode.rightChildIndex = 2;
    threeLinkNode.index = 0;
    threeLinkNode.parentIndex = -1;

    datas[0] = threeLinkNode;
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
        ThreeLinkNode<T> threeLinkNode = new ThreeLinkNode<T>();
        threeLinkNode.data = data;
        threeLinkNode.index = i;
        threeLinkNode.parentIndex = index;
        datas[i] = threeLinkNode;
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
  @SuppressWarnings("unchecked")
  public T getParent(int index) {
    if (index > arraySize || datas[index] == null) {
      throw new RuntimeException("标记无效");
    }

    return (T) datas[datas[index].parentIndex];
  }

  /**
   * 返回左子节点
   * 
   * @return
   */
  public T getLeftNode(int index) {
    if (index + 2 > arraySize || datas[index] == null || datas[datas[index].leftChildIndex] == null) {
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
    if (index + 2 > arraySize || datas[index] == null || datas[datas[index].rightChildIndex] == null) {
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
