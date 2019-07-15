
import java.util.ArrayList;
import java.util.List;

//这次总结了一般树的一般组建内核，下次将对特殊的树二叉树进行学习。一般在程序里面使用树结构进行模型构建使用最多的还是记父节点方式，无论是Java还是JS特效树，都是采用此方式进行树构建的比较多，而记子节点方式相对于记父节点来说思维很不一样。如果一个菜单数据十分巨大，那么一个记子树形菜单所消耗的资源将是巨大的。对不起，因为笔者在本片复习小结中生了一场病。所以学习笔记有些松散，下不为例。祝大家多多锻炼、注意身体。身体真是本钱！身体差却是吃亏啊
public class TreeChildren<E> {

  // 默认数组大小
  private final int DefSize = 150;

  // 记录节点个数
  private int nodeSize;

  // 父节点对象
  private Node<E>[] nodes;

  public TreeChildren() {
    nodes = new Node[DefSize];
  }

  public TreeChildren(E e) {
    nodes = new Node[DefSize];
    nodeSize++;
    nodes[0] = new Node(e, 0);
  }

  /**
   * 为指定节点增加子节点
   * 
   * @param e
   * @param parentNode
   * @return
   */
  @SuppressWarnings("unchecked")
  public boolean addNodeByParent(E e, Node<E> parentNode) {
    for (int i = 0; i < nodes.length; i++) {
      if (nodes[i] == null) {

        SonNode sonNode = new SonNode(e, i, null);

        SonNode lastSonNode = getLastSonNodeByParent(parentNode);

        if (lastSonNode == null) {
          parentNode.firstSonNode = sonNode;
        } else {
          lastSonNode.sonNode = sonNode;
        }
        nodes[i] = sonNode.paseNode();
        nodeSize++;
        return true;
      }
    }
    return false;
  }

  /**
   * 由SonNode到普通Node的转化
   * 
   * @param sonNode
   * @return
   */
  public Node<E> paseNode(SonNode<E> sonNode) {
    for (int i = 0; i < nodeSize; i++) {
      if (nodes[i] != null && nodes[i].data == sonNode.data && nodes[i].index == sonNode.index) {
        return nodes[i];
      }
    }
    return null;
  }

  /**
   * 获得一个父节点的最后子节点对象
   * 
   * @param parentNode
   * @return
   */
  @SuppressWarnings("unchecked")
  public SonNode getLastSonNodeByParent(Node<E> parentNode) {

    if (parentNode.firstSonNode == null) {
      return null;
    }

    SonNode sonNodeNow = parentNode.firstSonNode;
    while (sonNodeNow.sonNode != null) {
      sonNodeNow = sonNodeNow.sonNode;
    }
    return sonNodeNow;
  }

  /**
   * 根据node获得索引
   * 
   * @param node
   * @return
   */
  public int getNodeIndex(Node<E> node) {
    for (int i = 0; i < nodes.length; i++) {
      if (nodes[i].equals(node)) {
        return i;
      }
    }
    return -1;
  }

  /**
   * 判断是否是空树
   * 
   * @return
   */
  public boolean isEmpty() {
    return nodeSize == 0;
  }

  /**
   * 返回树的根节点
   * 
   * @return
   */
  public Node<E> getRootNode() {

    if (nodeSize == 0) {
      return null;
    }
    return nodes[0];
  }

  /**
   * 根据子节点返回父节点对象
   * 
   * @param chileNode
   * @return
   */
  @SuppressWarnings("unchecked")
  public Node<E> getParentNodeByChildNode(Node<E> chileNode) {

    for (int i = 0; i < nodes.length; i++) {

      if (nodes[i] != null && nodes[i].firstSonNode != null) {

        SonNode<E> sonNode = nodes[i].firstSonNode;

        while (sonNode != null) {
          if (sonNode.data == chileNode.data && sonNode.index == chileNode.index) {

            return nodes[i];

          }
          sonNode = sonNode.sonNode;
        }

      }

    }
    return null;

  }

  /**
   * 根据父节点返回父子节点对象集合
   * 
   * @param chileNode
   * @return
   */
  @SuppressWarnings("unchecked")
  public List<SonNode<E>> getChildsNodeByParentNode(Node<E> parentNode) {
    if (parentNode == null) {
      return null;
    }

    SonNode<E> sonNodeNow = parentNode.firstSonNode;
    List<SonNode<E>> list = new ArrayList<SonNode<E>>();
    while (sonNodeNow.sonNode != null) {
      list.add(sonNodeNow);
      sonNodeNow = sonNodeNow.sonNode;
    }
    return list;
  }

  /**
   * 返回指定节点的第index个子节点,第1个代表第一个
   * 
   * @param parentNode
   * @param index
   * @return
   */
  public SonNode<E> getIndexChildByParentNode(Node<E> parentNode, int index) {

    if (index == 0) {
      throw new RuntimeException("没有第0个子节点，从1开始");
    }

    if (parentNode.firstSonNode == null) {
      return null;
    }

    int childCount = 0;

    SonNode<E> sonNode = parentNode.firstSonNode;

    while (sonNode != null) {
      childCount++;
      if (index == childCount) {
        return sonNode;
      }
      sonNode = sonNode.sonNode;
    }

    return null;
  }

  /**
   * 返回节点的深度
   * 
   * @return
   */
  public int returnNodeDeep(Node<E> node) {
    if (node == getRootNode()) {
      return 1;
    } else {
      Node<E> parentNode = getParentNodeByChildNode(node);
      if (parentNode != null) {
        return returnNodeDeep(parentNode) + 1;
      }
      return 0;
    }
  }

  /**
   * 返回树的深度
   * 
   * @return
   */
  public int returnTreeDeep() {
    int max = 0;
    for (int i = 0; i < nodeSize; i++) {
      int nodeDeep = returnNodeDeep(nodes[i]);
      if (max < nodeDeep) {
        max = nodeDeep;
      }
    }
    return max;
  }

  @Override
  public String toString() {

    StringBuffer str = new StringBuffer("[");

    for (int i = 0; i < nodeSize; i++) {

      str.append("[" + nodes[i].data + "],");

    }

    if (nodeSize > 0) {
      return str.substring(0, str.lastIndexOf(",")) + "]";
    }

    return str.append("]").toString();
  }
}
