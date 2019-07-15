
public class TreeParent<E> {

  // 默认数组大小
  private final int DefSize = 150;

  // 记录节点个数
  private int nodeSize;

  // 父节点对象
  private Node<E>[] nodes;

  public TreeParent() {
    nodes = new Node[DefSize];
  }

  public TreeParent(E e) {
    nodes = new Node[DefSize];
    nodeSize++;
    nodes[0] = new Node(e, -1);
  }

  /**
   * 为指定节点增加子节点
   * 
   * @param e
   * @param parentNode
   * @return
   */
  public boolean addNodeByParent(E e, Node<E> parentNode) {
    for (int i = 0; i < nodes.length; i++) {
      if (nodes[i] == null) {
        int parentNodeIndex = getNodeIndex(parentNode);
        nodes[i] = new Node<E>(e, parentNodeIndex);
        nodeSize++;
        return true;
      }
    }
    return false;
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
  public Node<E> getParentNodeByChildNode(Node<E> chileNode) {
    for (int i = 0; i < nodeSize; i++) {

      if (chileNode == null) {
        return null;
      }

      if (i == chileNode.parentIndex) {
        return nodes[i];
      }
    }
    return null;
  }

  /**
   * 根据父节点返回子节点对象集合
   * 
   * @param node
   * @return
   */
  public Node<E> getChildNodesByParentNode(Node<E> node) {
    if (node == null) {
      return null;
    }
    return nodes[node.parentIndex];
  }

  /**
   * 返回指定节点的第index个子节点,第1个代表第一个
   * 
   * @param parentNode
   * @param index
   * @return
   */
  public Node<E> getIndexChildByParentNode(Node<E> parentNode, int index) {

    if (index == 0) {
      throw new RuntimeException("没有第0个子节点，从1开始");
    }

    int childCount = 0;

    int parentIndex = getNodeIndex(parentNode);
    for (int i = 0; i < nodeSize; i++) {
      if (nodes[i].parentIndex == parentIndex) {
        ++childCount;
        if (childCount == index) {
          return nodes[i];
        }
      }
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

  public static void main(String[] args) {
    TreeParent<String> tree = new TreeParent<String>("根菜单");
    Node<String> root = tree.getRootNode();
    tree.addNodeByParent("子菜单1", root);
    tree.addNodeByParent("子菜单2", root);
    Node<String> node1 = tree.getIndexChildByParentNode(root, 1);
    tree.addNodeByParent("子菜单11", node1);
    System.out.println("树的数据：" + tree);
    System.out.println("节点的深度：" + tree.returnNodeDeep(node1));
    System.out.println("节点的深度：" + tree.returnNodeDeep(root));
    Node<String> node11 = tree.getIndexChildByParentNode(node1, 1);
    System.out.println("节点的深度：" + tree.returnNodeDeep(node11));
    System.out.println("树的深度：" + tree.returnTreeDeep());
  }

}
