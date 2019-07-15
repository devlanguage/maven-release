
import org.basic.arithmetic.sort.arithmeticlist.Insert_DirectInsertSort;
import org.basic.arithmetic.sort.arithmeticlist.Insert_ShellSort;
import org.basic.arithmetic.sort.arithmeticlist.MergeSort;
import org.basic.arithmetic.sort.arithmeticlist.MergeSortImproved;
import org.basic.arithmetic.sort.arithmeticlist.Select_DirectSelectSort;
import org.basic.arithmetic.sort.arithmeticlist.Select_HeapSort;
import org.basic.arithmetic.sort.arithmeticlist.Swap_BubbleSort;
import org.basic.arithmetic.sort.arithmeticlist.Swap_QuicktSort;
import org.basic.arithmetic.sort.arithmeticlist.Swap_QuicktSortImproved;

public class SortUtil {

    public final static int INSERT = 1;
    public final static int BUBBLE = 2;
    public final static int SELECTION = 3;
    public final static int SHELL = 4;
    public final static int QUICK = 5;
    public final static int IMPROVED_QUICK = 6;
    public final static int MERGE = 7;
    public final static int IMPROVED_MERGE = 8;
    public final static int HEAP = 9;
    public static final int[] TEST_ARRAY = new int[] { 3, 4, 2, 7, 1, 9, 0, 6, 5, 8 };

    public static void sort(int[] data) {

        sort(data, IMPROVED_QUICK);
    }

    private static String[] name = { "insert", "bubble", "selection", "shell", "quick", "improved_quick", "merge",
            "improved_merge", "heap" };

    private static SortIntf[] impl = new SortIntf[] { new Insert_DirectInsertSort(), new Swap_BubbleSort(),
            new Select_DirectSelectSort(), new Insert_ShellSort(), new Swap_QuicktSort(),
            new Swap_QuicktSortImproved(), new MergeSort(), new MergeSortImproved(), new Select_HeapSort() };

    public static String toString(int algorithm) {

        return name[algorithm - 1];
    }

    public static SortIntf sort(int[] data, int algorithm) {

        impl[algorithm - 1].sort(data);
        return impl[algorithm - 1];
    }

    public static void swap(int[] data, int i, int j) {
        // int temp = data[i];
        // data[i] = data[j];
        // data[j] = temp;
        data[i] = data[i] ^ data[j];
        data[j] = data[i] ^ data[j];
        data[i] = data[i] ^ data[j];
    }

    public final static void printArray(int[] numbers) {
        System.out.println("==========================");
        for (int i : numbers) {
            System.out.print(i+" ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int[] data = new int[] { 10, 20 };
        System.out.println("data[0]=" + data[0] + ",data[1]=" + data[1]);
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10E7; i++) {
            swap(data, 0, 1);
        }
        long end = System.currentTimeMillis();
        System.out.println("time:" + (end - start));
        System.out.println("data[0]=" + data[0] + ",data[1]=" + data[1]);
    }
}
