
public class LinkedList<T> {
  class LinkNode<T> {
    private T element;
    private LinkNode<T> next;
    private LinkNode<T> previous;

    LinkNode(LinkNode<T> previous, T element, LinkNode<T> next) {
      this.next = next;
      this.element = element;
      this.previous = previous;
    }
  }

  private int size;
  private LinkNode<T> first;
  private LinkNode<T> last;

  public boolean addNode(T element) {
    return addLast(element);
  }

  private boolean addLast(T element) {
    LinkNode<T> oldLast = last;

    LinkNode<T> newNode = new LinkNode<>(oldLast, element, null);
    last = newNode;

    if (oldLast == null) {
      first = newNode;
    } else {
      oldLast.next = newNode;
    }
    size++;
    return true;
  }

  private boolean addFirst(T element) {
    LinkNode<T> oldFirst = first;

    LinkNode<T> newNode = new LinkNode<>(null, element, oldFirst);
    first = newNode;

    if (oldFirst == null) {
      last = newNode;
    } else {
      oldFirst.previous = newNode;
    }
    size++;
    return true;
  }

  public static void main(String[] args) {
    LinkedList<String> studuents = new LinkedList<>();
    studuents.addFirst("001-zhangsan");
    studuents.addFirst("002-liSi");
    studuents.addFirst("003-WangWu");
    System.out.println(studuents);
  }
}
