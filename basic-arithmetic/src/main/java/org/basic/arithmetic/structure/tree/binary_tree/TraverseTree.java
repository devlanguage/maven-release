
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * 7. 二叉树的遍历 遍历二叉树实际上就是将一个非线性的二维结构给排列呈线性的过程。如果是顺序实现了二叉树的结构，自然底层就是线性的，无需转化。如果是纯链表实现呢，就需要将离散的节点重新组织组织了。
 * 遍历也分为深度优先遍历、广度优先遍历。而对于深度优先遍历又分为三种模式：先根遍历、中根遍历、后根遍历。 深度优先遍历：就是优先访问树中最深层次的节点 广度优先遍历：就是从根往下一层一层遍历访问
 * 先根遍历：先遍历根节点，之后处理其他子节点 中根遍历：先遍历根节点的左子树，之后遍历根节点，最后遍历右子树 后根遍历：先遍历根节点的左子树，之后遍历右子树，最后遍历根节点
 * 
 * 因为树本身就是具有递归性质的结构。
 * 
 * @author ygong
 * 
 */
public class TraverseTree<T> {
  private TwoLinkNode<T>[] datas;

  /**
   * 先根遍历算法如下
   * 
   * @param twoLinkNode
   * @return
   */
  public List<TwoLinkNode<T>> firstRoot(TwoLinkNode<T> twoLinkNode) {
    if (twoLinkNode == null) {
      return null;
    }
    List<TwoLinkNode<T>> list = new ArrayList<TwoLinkNode<T>>();
    list.add(twoLinkNode);
    if (twoLinkNode.leftChildIndex > 0) {
      list.addAll(firstRoot(datas[twoLinkNode.leftChildIndex]));
    }

    if (twoLinkNode.rightChildIndex > 0) {
      list.addAll(firstRoot(datas[twoLinkNode.rightChildIndex]));
    }

    return list;
  }

  /**
   * 中根遍历
   * 
   * @param twoLinkNode
   * @return
   */
  public List<TwoLinkNode<T>> minRoot(TwoLinkNode<T> twoLinkNode) {
    if (twoLinkNode == null) {
      return null;
    }
    List<TwoLinkNode<T>> list = new ArrayList<TwoLinkNode<T>>();

    if (twoLinkNode.leftChildIndex > 0) {
      list.addAll(minRoot(datas[twoLinkNode.leftChildIndex]));
    }

    list.add(twoLinkNode);

    if (twoLinkNode.rightChildIndex > 0) {
      list.addAll(minRoot(datas[twoLinkNode.rightChildIndex]));
    }

    return list;
  }

  /**
   * 后根遍历
   * 
   * @param twoLinkNode
   * @return
   */
  public List<TwoLinkNode<T>> afterRoot(TwoLinkNode<T> twoLinkNode) {
    if (twoLinkNode == null) {
      return null;
    }
    List<TwoLinkNode<T>> list = new ArrayList<TwoLinkNode<T>>();

    if (twoLinkNode.leftChildIndex > 0) {
      list.addAll(afterRoot(datas[twoLinkNode.leftChildIndex]));
    }

    if (twoLinkNode.rightChildIndex > 0) {
      list.addAll(afterRoot(datas[twoLinkNode.rightChildIndex]));
    }

    list.add(twoLinkNode);

    return list;
  }

  /**
   * 广度优先遍历 后根遍历
   * 
   * @param twoLinkNode
   * @return
   */
  public List<TwoLinkNode<T>> deepFirst() {
    List<TwoLinkNode<T>> list = new ArrayList<TwoLinkNode<T>>();
    Queue<TwoLinkNode<T>> queue = new ArrayDeque<TwoLinkNode<T>>();
    queue.add(datas[0]);
    while (!queue.isEmpty()) {
      list.add(queue.peek());
      TwoLinkNode<T> twoLinkNode = queue.poll();
      if (twoLinkNode.leftChildIndex > 0) {
        queue.add(datas[twoLinkNode.leftChildIndex]);
      }
      if (twoLinkNode.rightChildIndex > 0) {
        queue.add(datas[twoLinkNode.rightChildIndex]);
      }
    }
    return list;
  }

}
