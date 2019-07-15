
import java.math.BigDecimal;

public class FibonacciTest {
    public static void main(String[] args) {
        FibonacciTest tester = new FibonacciTest();
        // tester.testDistributedQueue();
//        -XshowSettings:vm -XshowSettings:locale -Xdiag
        long start = 0, end = 0, startNumber = 40;
        // start = System.currentTimeMillis();
        // System.out.println("fibonacciBigDecimal: " + tester.fibonacciBigDecimal(startNumber));
        // end = System.currentTimeMillis();
        // System.out.println(end - start);
        //
        // start = System.currentTimeMillis();
        // System.out.println("fibonacciInt: " + tester.fibonacciInt((int) startNumber));
        // end = System.currentTimeMillis();
        // System.out.println(end - start);

        start = System.currentTimeMillis();
        System.out.println("fibonacciLong: " + tester.fibonacciLong(startNumber));
        end = System.currentTimeMillis();
        System.out.println(end - start);

        start = System.currentTimeMillis();
        System.out.println("fibonacciIterate: " + tester.fibonacciIterate(startNumber));
        end = System.currentTimeMillis();
        System.out.println(end - start);

    }

    private long fibonacciIterate(long i) {
        // 0、1、1、2、3、5、8、13、21
        if (i > 1) {
            long x = fibonacciIterate(i - 2) + fibonacciIterate(i - 1);
            return x;
        } else if (i == 1) {
            return 1;
        } else if (i == 0) {
            return 0;
        } else {
            throw new RuntimeException("Invalid input for fibonacci");
        }
    }

    private BigDecimal fibonacciBigDecimal(long i) {
        BigDecimal sum = new BigDecimal(0);
        while (i > 0) {
            sum = sum.add(new BigDecimal(i--));
        }
        return sum;
    }

    private long fibonacciLong(long f) {
        long sum = 0; // f(n)=f(n-1) + f(n-2)
        long fn1 = 0;
        long fn2 = 1;
        for (int i = 2; i <= f; ++i) {
            sum = fn1 + fn2;
            fn1 = fn2;
            fn2 = sum;
        }
        return sum;
    }

    private BigDecimal fibonacciInt(long i) {
        long smallSum = 0;
        BigDecimal bigSum = null;
        while (i > 0) {
            if (smallSum >= 0) {
                smallSum = (smallSum + i--);
            } else {
                if (bigSum == null) {
                    bigSum = new BigDecimal(smallSum - (i + 1));
                    bigSum = bigSum.add(new BigDecimal(i + 1 + i--));
                } else {
                    bigSum = bigSum.add(new BigDecimal(i--));
                }

            }
        }
        return smallSum > 0 ? new BigDecimal(smallSum) : bigSum;
    }
}
