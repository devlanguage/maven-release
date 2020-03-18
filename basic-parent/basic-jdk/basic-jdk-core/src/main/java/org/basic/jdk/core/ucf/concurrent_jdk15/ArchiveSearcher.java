package org.basic.jdk.core.ucf.concurrent_jdk15;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.Callable;

public class ArchiveSearcher<T extends Object & Serializable> implements Callable<T> {

    public void test(List<? super Object> t){
        
    }
    public T call() throws Exception {

        Thread.sleep(5000);
        System.out.println("It take the 5 second to search the prodcut");
//        T t = new T();
        System.out.println();

        return null;
    }

}
