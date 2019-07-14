package org.basic.arithmetic.recursion;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Stack;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;

class DirectoryPrinterInRecursion {

    public void printDirectoryInRecursion(File root) {

        if (root.isDirectory()) {
            for (File f : root.listFiles()) {
                printDirectoryInRecursion(f);
            }
        } else {
            print(root.getPath());
        }

    }

    private <T> void print(T... path) {

        for (T t : path) {
//            System.out.print(" " + t);
        }
//        System.out.println();
    }
}

class DirectoryPrinterInStack {

    final Stack<File> fileStack = new Stack<File>();

    public void printDirectoryInStack(File root) {

        fileStack.push(root);
        while (!fileStack.isEmpty()) {
            File f1 = fileStack.pop();
            if (f1.isDirectory()) {
                for (File f2 : f1.listFiles()) {
                    if (f2.isDirectory()) {
                        fileStack.push(f2);
                    } else {
                        print(f2.getPath());
                    }
                }
            } else {
                print(f1.getPath());
            }

        }

    }

    private <T> void print(T... path) {

        for (T t : path) {
//            System.out.print(" " + t);
        }
//        System.out.println();
    }
}

class RecursionMethodIntercepor implements net.sf.cglib.proxy.MethodInterceptor {

    public Object intercept(Object obj, Method method, Object[] args, net.sf.cglib.proxy.MethodProxy proxy)
            throws Throwable {

        // long start = System.currentTimeMillis();
        Object returnObject = proxy.invokeSuper(obj, args);
        // long end = System.currentTimeMillis();
        // System.out.println(method.getName() + " take " + (end - start) + " milliseconds");
        return returnObject;
    }
}

class RecursionCallbackFilter implements net.sf.cglib.proxy.CallbackFilter {

    @Override
    public int accept(Method arg0) {

        return 1;
    }
}

public class RecursionTest {

    public static void main(String[] args) {

        File root = new File("C:/software/Java_Dev/eclipse_proj/JavaBasic/java/basic/src");

        RecursionMethodIntercepor interceptor1 = new RecursionMethodIntercepor();
        RecursionCallbackFilter callBackFilter = new RecursionCallbackFilter();

        Enhancer enhancer1 = new Enhancer();
        enhancer1.setSuperclass(DirectoryPrinterInStack.class);
        enhancer1.setCallbacks(new Callback[] { net.sf.cglib.proxy.NoOp.INSTANCE, interceptor1 });
        enhancer1.setCallbackFilter(callBackFilter);

        DirectoryPrinterInStack rt1 = (DirectoryPrinterInStack) enhancer1.create();

        long start = System.currentTimeMillis();
        rt1.printDirectoryInStack(root);
        long end = System.currentTimeMillis();
        System.out.println("Cglib in stack take " + (end - start) + " milliseconds");

        DirectoryPrinterInStack rt2 = new DirectoryPrinterInStack();
        start = System.currentTimeMillis();
        rt2.printDirectoryInStack(root);
        end = System.currentTimeMillis();
        System.out.println("DirectoryPrinterInStack take " + (end - start) + " milliseconds");

        DirectoryPrinterInRecursion rt3 = new DirectoryPrinterInRecursion();
        start = System.currentTimeMillis();
        rt3.printDirectoryInRecursion(root);
        end = System.currentTimeMillis();
        System.out.println("DirectoryPrinterInRecursion take " + (end - start) + " milliseconds");

        //
        // File directory = new File("C:/windows/system32/drivers");
        //
        // print(directory.isAbsolute(), directory.isDirectory(), directory.isFile(), directory.isHidden());
        // // if file doesn't exist "true false false false"
        // // printDirectoryInRecursion(directory);
        // printDirectoryInStack(directory);
    }
}
