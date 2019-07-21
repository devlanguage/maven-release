package org.basic.grammar.jdk.jdk8.lambda;

/**
 * <pre>
 * Method reference in Java 8 is the ability to use a method as an argument for
 * a matching functional interface. :: (double colon) is the operator used for
 * method reference in Java. An interface with only one method is called a
 * functional interface. For example, Comparable, Runnable, AutoCloseable are
 * some functional interfaces in Java.
 * 
 * Its there all through the Internet saying Java does not have the scope
 * resolution operator (::), in comparison with C++. Its on the way and it will
 * be here with Java 8. Between Java and C++, only the operator :: (double
 * colon) is same, the way it is implemented is different. So we cannot call it
 * as scope resolution operator in Java. It is used for method referencing and
 * not for scope resolution.
 * 
 * Method reference using :: is a convenience operator. Method reference is one
 * of the features belonging to Java lambda expressions. Method reference can be
 * expressed using the usual lambda expression syntax format using –> In order
 * to make it more simple :: operator can be used.
 * 
 * In the last tutorial, I gave a list of examples for Java lambda expressions.
 * Which will give detail on different ways of using lambda expressions and I
 * recommend you to go through it using JDK 8. In that article refer example 5
 * (Lambda Expression Sorting), there we can see method reference used.
 * 
 * Syntax: <classname or instancename>::<methodname>
 *  *
 * </pre>
 * 
 * @author ygong
 *
 */
public class MethodReferenceExample {
    void close() {
        System.out.println("Close.");
    }

    public static void main(String[] args) throws Exception {
        MethodReferenceExample referenceObj = new MethodReferenceExample();
        try (AutoCloseable ac = referenceObj::close) {
            System.out.println("test");
        }
    }
    
    void testConstructorReferences()
    {
        
    }


}
