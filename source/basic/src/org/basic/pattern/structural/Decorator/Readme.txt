装饰（Decorator）模式是动态给一个对象添加一些额外的职责，或者说改变这个对象的一些行为。 这就类似于使用油漆为某个东西刷上油漆，在原来的对象表面增加了一层外衣。
 在装饰模式中，有两个主要角色：
     1. 被刷油漆的对象（decoratee）；
     2. 给decoratee刷油漆的对象（decorator）。
 这两个对象都继承同一个接口。

     下面就通过一个简单的例子演示装饰模式的实现:

1, 为实现打桩操作先定义一个接口

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2006-6-29
 * Time: 15:03:20
 * 类功能说明:
 *     为打桩定义一个接口类
 */
public interface Work {
    /** 打桩工作的抽象接口 */
    public void insert();
}


2. 实现表示方形木桩的插入的类

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2006-6-29
 * Time: 15:05:22
 * 类功能说明:
 *     打方形桩实现类
 */
public class SquarePeg implements Work {

    public void insert(){
        System.out.println("打入方形木桩");
    }
}


3. 用SquarePeg 类也许就可以满足打桩的工作需要，但是有可能土质很硬，在插入方形桩之前先要打一个洞，那么又将如何实现？也就是说在执行打桩操作之前还需要额外的工作,我们已经有了一个实现打桩的类SquarePeg,如何在不改变此类的基础上实现额外的功能呢?我们可以采用装饰模式,可以编制一个Decorator类，同样继承Work接口，但是在实现insert方法时有些特别,需要添加额外的功能.

/**
 * 用SquarePeg 类也许就可以满足打桩的工作需要，但是有可能土质很硬，在插入方形桩之前先要打一个洞，那么又将如何实现？也就是说在执行打桩操作之前还需要额外的工作,我们已经有了一个实现打桩的类SquarePeg,如何在不改变此类的基础上实现额外的功能呢?我们可以采用装饰模式,可以编制一个Decorator类，同样继承Work接口，但是在实现insert方法时有些特别,需要添加额外的功能.
 *
 * ---------------------------------------------------------------
 * 装饰（Decorator）模式是动态给一个对象添加一些额外的职责，或者说改变这个对象的一些行为。
 * 这就类似于使用油漆为某个东西刷上油漆，在原来的对象表面增加了一层外衣。
 * 在装饰模式中，有两个主要角色：
 *     1. 被刷油漆的对象（decoratee）；
 *     2. 给decoratee刷油漆的对象（decorator）。
 * 这两个对象都继承同一个接口。
 *
 * 装饰模式关键点:
 *     1.decoratee和decorator实现同一个接口
 *     2.在decorator类的构造方法带有一个接口decoratee的参数,且本地保存一个decoratee接口的实例
 *     3.在insert方法中添加额外的功能
 * ---------------------------------------------------------------
 */

import java.util.List;
import java.util.ArrayList;
import java.util.ListIterator;

public class Decorator implements Work {

    private Work work;

    //额外的功能放在这个list中
    private List others = new ArrayList();

    public Decorator(Work work){
        this.work = work;
        //增加打洞的额外功能
        others.add("打洞");
    }

    public void insert(){
        //在打桩之前需要加入额外的功能才能再进行操作
        otherMethod();

        work.insert();
    }

    public void otherMethod(){
        ListIterator listIterator = others.listIterator();

        while(listIterator.hasNext()){
            System.out.println(listIterator.next() + " 正在进行");
        }
    }
}

    在Decorator的方法insert中先执行otherMethod()方法，然后才实现SquarePeg的insert方法。油漆工Decorator给被油漆者SquarePeg添加了新的行为——打洞。

4. 测试装饰模式的实现效果

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2006-6-29
 * Time: 15:17:06
 * 类功能说明:
 *     装饰模式测试类.
 */
public class Test {

    public static void main(String[] args){
        Work squarePeg = new SquarePeg();
        Work decorator = new Decorator(squarePeg);
        decorator.insert();
    }

}


    本例中只添加了一个新的行为（打洞），如果还有很多类似的行为，那么使用装饰模式的优点就体现出来了。因为可以通过另外一个角度（如组织新的油漆工实现子类）来对这些行为进行混合和匹配，这样就不必为每个行为创建一个类，从而减少了系统的复杂性。

    使用装饰模式可以避免在被油漆对象decoratee中包装很多动态的，可能需要也可能不需要的功能，只要在系统真正运行时，通过油漆工decorator来检查那些需要加载的功能，实行动态加载。

    装饰模式也很好的表现出java的设计原则:优先组合对象而不是继承.

