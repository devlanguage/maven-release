package org.basic.jdk.jdk6.generictype;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Element {
}

class Box<T> {
}

class Pair<KEY, VALUE> {
}

/**
 * <pre>
 * 查阅JDK的文档，Class.getTypeParameters(): 方法返回一个TypeVariable对象的数组，数组中每个TypeVariable对象描述了泛型中声明的类型。
 * 这似乎意味着，我们可以从TypeVariable对象中找出泛型实例化时真正的类型。但是，从程序运行的输出结果，我们可以看，
 * Class.getTypeParameters() 返回的一系列TypeVariable 对象，仅仅表征了泛型声明时的参数化类型占位符，真正实例化时的类型都被抛弃了。
 * 因此，Java 泛型的真相是： 在泛型代码中，根本就没有参数化类型的信息。
 * 产生这样一个事实的原因在于，JDK 对泛型的内部实现，采用了擦除(erasure)的方式，具体擦除的方式如下：
 * 1) ArrayList&lt;String&gt;、ArrayList&lt;Integer&gt;、ArrayList&lt;?&gt;都被擦除成ArrayList
 * 2) ArrayList&lt;T extends BaseClass&gt;、ArrayList&lt;? extends BaseClass&gt;都被擦除成ArrayList&lt;BaseClass&gt;
 * 3) ArrayList&lt;? super BaseClass&gt;被擦除成ArrayList&lt;BaseClass&gt;
 * 
 * </pre>
 * 
 * 
 */
public class GenericClass_Advanced {

    public static void main(String[] args) {

        // 这个例子说明，泛型ArrayList<String>、ArrayList<Integer>和没有使用泛型的ArrayList 其实是同一个类。就如同没使用泛型一样。
        Class c1 = new ArrayList<String>().getClass();
        Class c2 = new ArrayList<Integer>().getClass();
        Class c3 = new ArrayList().getClass();
        System.out.println(c1 == c2);
        System.out.println(c1 == c3);

        List<Element> list = new ArrayList<Element>();
        Map<String, Element> map = new HashMap<String, Element>();
        Box<Element> box = new Box<Element>();
        Pair<Integer, String> p = new Pair<Integer, String>();

        System.out.println(Arrays.toString(list.getClass().getTypeParameters()));
        System.out.println(Arrays.toString(map.getClass().getTypeParameters()));
        System.out.println(Arrays.toString(box.getClass().getTypeParameters()));
        System.out.println(Arrays.toString(p.getClass().getTypeParameters()));
    }

}
