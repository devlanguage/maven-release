Java内存模型happens-before法则  

The rules for happens-before are: 
    Program order rule. Each action in a thread happens-before every action in that thread that comes later in the program order. 
    Monitor lock rule. An unlock on a monitor lock happens-before every subsequent lock on that same monitor lock. 
    Volatile variable rule. A write to a volatile field happens-before every subsequent read of that same field. 
    Thread start rule. A call to Thread.start on a thread happens-before every action in the started thread. 
    Thread termination rule. Any action in a thread happens-before any other thread detects that thread has terminated, either by successfully return from Thread.join or by Thread.isAlive returning false. 
    Interruption rule. A thread calling interrupt on another thread happens-before the interrupted thread detects the interrupt (either by having InterruptedException tHRown, or invoking isInterrupted or interrupted). 
    Finalizer rule. The end of a constructor for an object happens-before the start of the finalizer for that object. 
    Transitivity. If A happens-before B, and B happens-before C, then A happens-before C. 

appens-before完整规则：
（1）同一个线程中的每个Action都happens-before于出现在其后的任何一个Action。
（2）对一个监视器的解锁happens-before于每一个后续对同一个监视器的加锁。
（3）对volatile字段的写入操作happens-before于每一个后续的同一个字段的读操作。
（4）Thread.start()的调用会happens-before于启动线程里面的动作。
（5）Thread中的所有动作都happens-before于其他线程检查到此线程结束或者Thread.join（）中返回或者Thread.isAlive()==false。
（6）一个线程A调用另一个另一个线程B的interrupt（）都happens-before于线程A发现B被A中断（B抛出异常或者A检测到B的isInterrupted（）或者interrupted()）。
（7）一个对象构造函数的结束happens-before与该对象的finalizer的开始
（8）如果A动作happens-before于B动作，而B动作happens-before与C动作，那么A动作happens-before于C动作。



什么是happens-before？ 
happens-before就是“什么什么一定在什么什么之前运行”，也就是保证顺序性。 
因为CPU是可以不按我们写代码的顺序执行内存的存取过程的，也就是指令会乱序或并行运行， 
只有上面的happens-before所规定的情况下，才保证顺序性。 
如： 
Java代码 
public class Test {  
  
    private int a = 0;  
  
    private long b = 0;  
  
    public void set() {  
        a = 1;  
        b = -1;  
    }  
  
    public void check() {  
        if (! ((b == 0) || (b == -1 && a == 1))  
            throw new Exception("check Error!");  
    }  
}  

对于set()方法的执行： 
1. 编译器可以重新安排语句的执行顺序，这样b就可以在a之前赋值。如果方法是内嵌的(inline)，编译器还可以把其它语句重新排序。 
2. 处理器可以改变这些语句的机器指令的执行顺序，甚到同时执行这些语句。 
3. 存储系统(由于被缓存控制单元控制)也可以重新安排对应存储单元的写操作顺序，这些写操作可能与其他计算和存储操作同时发生。 
4. 编译器，处理器和存储系统都可以把这两条语句的机器指令交叉执行。 
例如：在一台32位的机器上，可以先写b的高位，然后写a，最后写b的低位，(注：b为long类型，在32位的机器上分高低位存储) 
5. 编译器，处理器和存储系统都可以使对应于变量的存储单元一直保留着原来的值， 
以某种方式维护相应的值(例如，在CPU的寄存器中)以保证代码正常运行，直到下一个check调用才更新。 
... 
在单线程(或同步)的情况下，上面的check()永远不会报错， 
但非同步多线程运行时却很有可能。 


并且，多个CPU之间的缓存也不保证实时同步， 
也就是说你刚给一个变量赋值，另一个线程立即获取它的值，可能拿到的却是旧值(或null)， 
因为两个线程在不同的CPU执行，它们看到的缓存值不一样， 
只有在synchronized或volatile或final的性况下才能保证正确性， 
很多人用synchronized时只记得有lock的功能，而忘记了线程间的可见性问题。 
如： 
Java代码 
public class Test {  
  
    private int n;  
  
    public void set(int n) {  
        this.n = n;  
    }  
  
    public void check() {  
        if (n != n)  
            throw new Exception("check Error!");  
    }  
}  

check()中的 n != n 好像永远不会成立，因为他们指向同一个值，但非同步时却很有可能发生。 

另外，JMM不保证创建过程的原子性，读写并发时，可能看到不完整的对象， 
这也是为什么单例模式中著名的"双重检查成例"方法，在Java中行不通。(但.Net的内存模型保证这一点) 
当然，在Java中单例的延迟加载可以用另一种方案实现(方案四)： 

方案一：非延迟加载单例类 
Java代码 
public class Singleton {  
  
　　private Singleton(){}  
  
　　private static final Singleton instance = new Singleton();  
  
　　public static Singleton getInstance() {  
　　　　return instance; 　　  
　　}   
}  


方案二：简单的同步延迟加载 
Java代码 
public class Singleton {   
  
　　private static Singleton instance = null;  
  
　　public static synchronized Singleton getInstance() {  
　　　　if (instance == null)  
　　　　　　instance ＝ new Singleton();  
　　　　return instance; 　　  
　　}   
  
}   


方案三：双重检查成例延迟加载 
目的是避开过多的同步， 
但在Java中行不通，因为同步块外面的if (instance == null)可能看到已存在，但不完整的实例。 
JDK5.0以后版本若instance为volatile则可行 
Java代码 
public class Singleton {   
  
　　private static Singleton instance = null;  
  
　　public static Singleton getInstance() {  
　　　　if (instance == null) {  
　　　　　　　　synchronized (Singleton.class) {  
　　　　　　　　　　　　if (instance == null) {  
　　　　　　　　　　　　　　　　instance ＝ new Singleton();  
　　　　　　　　　　　　}  
　　　　　　　　}  
　　　　}  
　　　　return instance; 　　  
　　}   
  
}   


方案四：类加载器延迟加载 
Java代码 
public class Singleton {   
  
　　private static class Holder {  
　　  static final Singleton instance = new Singleton();  
　　}  
  