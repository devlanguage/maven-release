
import org.basic.arithmetic.sort.SortUtil;

public class ReverseArray {

    public static void main(String[] args) {

        int[] numbers = new int[] { 1, 2, 3, 4, 5, 6, 9 };
        SortUtil.printArray(numbers);
        reverseArray(numbers);
        System.out.println("----Reverse");
        SortUtil.printArray(numbers);
    }

    public final static void reverseArray(int[] intArray) {
        for (int i = 0; i < intArray.length / 2; i++) {
            SortUtil.swap(intArray, i, intArray.length - i - 1);
        }
    }
}
