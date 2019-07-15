package org.basic.grammar.jdk.jdk7.e11_method_handler_jsr292;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.time.StopWatch;

/**
 * <pre>
 * 方法句柄（method handle）是JSR 292中引入的一个重要概念， 它是对Java中方法、构造方法和域的一个强类型的可执行的引用。
 * 
 * 这也是句柄这个词的含义所在。通过方法句柄可以直接调用该句柄所引用的底层方法。
 * 
 * 从作用上来说，方法句柄的作用类似于2.2节中提到的反射API中的Method类，但是方法句柄的功能更强大、使用更灵活、性能也更好
 * 
 * 获取方法句柄最直接的做法是从一个类中已有的方法中转换而来，得到的方法句柄直接引用这个底层方法。在之前的示例中都是通过这种方式来获取方法句柄的。方法句柄可以按照与反射API类似的做法，从已有的类中根据一定的条件进行查找。
 * 与反射API不同的是，方法句柄并不区分构造方法、方法和域，而是统一转换成MethodHandle对象。对于域来说，获取到的是用来获取和设置该域的值的方法句柄。
 * 方法句柄的查找是通过java.lang.invoke.MethodHandles
 * .Lookup类来完成的。在查找之前，需要通过调用MethodHandles.lookup方法获取到一个MethodHandles.Lookup类的对象。MethodHandles
 * .Lookup类提供了一些方法以根据不同的条件进行查找。
 * 代码清单2-44以String类为例说明了查找构造方法和一般方法的示例。方法findConstructor用来查找类中的构造方法，需要指定返回值和参数类型，即MethodType对象
 * 。而findVirtual和findStatic则用来查找一般方法和静态方法，除了表示方法的返回值和参数类型的MethodType对象之外，还需要指定方法的名称。
 * </pre>
 */
public class MethodHandlerTest {
    public void generateMethodTypes() {
        // String.length()
        MethodType mt1 = MethodType.methodType(int.class);
        // String.concat(String str)
        MethodType mt2 = MethodType.methodType(String.class, String.class);
        // String.getChars(int srcBegin, int srcEnd, char[] dst, int dstBegin)
        MethodType mt3 = MethodType.methodType(void.class, int.class, int.class, char[].class, int.class);
        // String.startsWith(String prefix)
        MethodType mt4 = MethodType.methodType(boolean.class, mt2);
    }

    public void generateMethodTypesFromDescriptor() {
        ClassLoader cl = this.getClass().getClassLoader();
        String descriptor = "(Ljava/lang/String;)Ljava/lang/String;";
        MethodType mt1 = MethodType.fromMethodDescriptorString(descriptor, cl);

    }

    public void changeMethodType() {
        MethodType mtmt;
        // (int,int)String
        MethodType mt = MethodType.methodType(String.class, int.class, int.class);
        // (int,int,float)String
        mtmt = mt.appendParameterTypes(float.class);
        // (int,double,long,int,float)String
        mtmt = mt.insertParameterTypes(1, double.class, long.class);
        // (int,double,int,float)String
        mtmt = mt.dropParameterTypes(2, 3);
        // (int,double,String,float)String
        mtmt = mt.changeParameterType(2, String.class);
        // (int,double,String,float)void
        mtmt = mt.changeReturnType(void.class);
    }

    public void wrapAndGeneric() {
        // (int,double)Integer
        MethodType mt = MethodType.methodType(Integer.class, int.class, double.class);
        // (Integer,Double)Integer
        MethodType wrapped = mt.wrap();
        // (int,double)int
        MethodType unwrapped = mt.unwrap();
        // (Object,Object)Object
        MethodType generic = mt.generic();
        // (int,double)Object
        MethodType erased = mt.erase();
    }

    public void invokeExact() throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodType type = MethodType.methodType(String.class, int.class, int.class);
        MethodHandle mh = lookup.findVirtual(String.class, "substring", type);
        String str = (String) mh.invokeExact("Hello World", 1, 3);
        System.out.println(str);
    }

    public void lookupMethod() throws NoSuchMethodException, IllegalAccessException {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        // 构造方法
        MethodHandle stringConstruct1 = lookup.findConstructor(StringClass.class, MethodType.methodType(void.class, byte[].class));
        // String.substring
        MethodHandle stringSubstring = lookup.findVirtual(StringClass.class, "substring",
                MethodType.methodType(StringClass.class, int.class, int.class));
        // String.format
        MethodHandle stringFormat = lookup.findStatic(StringClass.class, "format",
                MethodType.methodType(String.class, String.class, Object[].class));

        //
        MethodHandle mh = lookup.findSpecial(StringClass.class, "checkBounds",
                MethodType.methodType(void.class, byte[].class, int.class, int.class), StringClass.class);

    }

    // public static void main(String[] args) {
    // //
    // // 直接调用 4455571 1x
    // // 反射Method 22086703 5x
    // // BeanUtils 566250584 100x
    // // MethodHandle 958052619 200x
    // MethodHandlerTest t = new MethodHandlerTest();
    // try {
    // t.lookupMethod();
    // } catch (NoSuchMethodException | IllegalAccessException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    // }
    private static void hello() {  
        System.out.println("Hello world!");  
    }  
      
    public static void main(String[] args) throws Throwable {
//        MethodType type = MethodType.methodType(void.class);  
//        MethodHandle method1 = MethodHandles.lookup()  
//            .findStatic(MethodHandlerTest.class, "hello", type);  
//        method1.invoke(); 
//        
        int times = 1_000_000;
        StopWatch watch = new StopWatch();
        User user = new User("a");

        System.out.println(user.getName());
        watch.start();
        for (int i = 0; i < times; ++i) {
            user.getName();
        }
        watch.stop();
        System.out.println("Direct call: " + watch.getTime());

        java.lang.reflect.Method method = User.class.getDeclaredMethod("getName", new Class[] {});
        Object[] emptyParams = new Object[] {};
        System.out.println(method.invoke(user, emptyParams));
        watch.reset();
        watch.start();
        for (int i = 0; i < times; ++i) {
            method.invoke(user, emptyParams);
        }
        watch.stop();
        System.out.println("Reflective call: " + watch.getTime());

        System.out.println(PropertyUtils.getProperty(user, "name"));
        watch.reset();
        watch.start();
        for (int i = 0; i < times; ++i) {
            PropertyUtils.getProperty(user, "name");
        }
        watch.stop();
        System.out.println("BeanUtil call: " + watch.getTime());

        MethodHandle methodHandle = MethodHandles.publicLookup().findVirtual(User.class, "getName",
                MethodType.methodType(String.class, new Class[] {}));
        System.out.println(methodHandle.invoke(user));
        watch.reset();
        watch.start();
        for (int i = 0; i < times; ++i) {
            methodHandle.invoke(user);
        }
        watch.stop();
        System.out.println("MethodHandle call: " + watch.getTime());
    }

    public static class User {
        private String name;

        public User(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
