package org.basic.jdk.core.jdk.jdk8.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Target;

/**
 * <pre>
 * 在Java 8中支持多重注解了，先看个例子来理解一下是什么意思。
 * 首先定义一个包装类Hints注解用来放置一组具体的Hint注解：
 * 
 * Java 8允许我们把同一个类型的注解使用多次，只需要给该注解标注一下@Repeatable即可。
 * 
 * </pre>
 */
@interface Hints {
    Hint[] value();
}

@Repeatable(Hints.class)
@interface Hint {
    String value();
}

// 另外Java 8的注解还增加到两种新的target上了：
@Target({ ElementType.TYPE_PARAMETER, ElementType.TYPE_USE })
@interface MyAnnotation {
}

// 例 1: 使用包装类当容器来存多个注解（老方法）
@Hints({ @Hint("hint1"), @Hint("hint2") })
class Person1 {
}

// 例 2：使用多重注解（新方法）
@Hint("hint1")
@Hint("hint2")
class Person2 {
}

public class AnnotationTest {
    public static void main(String[] args) {
        // 第二个例子里java编译器会隐性的帮你定义好@Hints注解，了解这一点有助于你用反射来获取这些信息：
        Hint hint = Person2.class.getAnnotation(Hint.class);
        System.out.println(hint); // null

        Hints hints1 = Person2.class.getAnnotation(Hints.class);
        System.out.println(hints1.value().length); // 2

        Hint[] hints2 = Person2.class.getAnnotationsByType(Hint.class);
        System.out.println(hints2.length); // 2

        // 即便我们没有在Person类上定义@Hints注解，我们还是可以通过 getAnnotation(Hints.class) 来获取 @Hints注解，更加方便的方法是使用 getAnnotationsByType
        // 可以直接获取到所有的@Hint注解。

    }
}
