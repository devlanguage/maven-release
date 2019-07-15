
class A {

  // public A() {
  // System.out.println("A");
  // }

  public A(String id) {
    System.out.println("A(String id)");
  }

}

class B extends A {

  public B() {
    super("d");
    System.out.println("B");
  }

  public B(String id) {
    super(id);
    System.out.println("B(String id)");
  }

}

public class SortTester {

  public static void main(String[] args) {

    int[] intArray = SortUtil.TEST_ARRAY;
    SortUtil.printArray(intArray);
    // SortUtil.sort(intArray, SortUtil.BUBBLE);
    SortIntf intf = SortUtil.sort(intArray, SortUtil.QUICK);
    // SortUtil.sort(intArray, SortUtil.INSERT);
    SortUtil.printArray(intArray);
    System.out.println("count=" + intf.count);

    B b1 = new B();
    // B b2 = new B("23");

  }
}
