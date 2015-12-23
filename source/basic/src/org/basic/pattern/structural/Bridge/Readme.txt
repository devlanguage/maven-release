Java设计模式之Bridge2008-05-18 11:39Bridge模式定义 :
将抽象和行为划分开来,各自独立,但能动态的结合。

任何事物对象都有抽象和行为之分，例如人，人是一种抽象，人分男人和女人等；人有行为，行为也有各种具体表现，所以，“人”与“人的行为”两个概念也反映了抽象和行为之分。

在面向对象设计的基本概念中，对象这个概念实际是由属性和行为两个部分组成的，属性我们可以认为是一种静止的，是一种抽象，一般情况下，行为是包含在一个对象中，
但是，在有的情况下，我们需要将这些行为也进行归类，形成一个总的行为接口，这就是桥模式的用处。

为什么使用?
不希望抽象部分和行为有一种固定的绑定关系，而是应该可以动态联系的。

如果一个抽象类或接口有多个具体实现(子类、concrete subclass),这些子类之间关系可能有以下两种情况:
1. 这多个子类之间概念是并列的,如前面举例,打桩,有两个concrete class:方形桩和圆形桩;这两个形状上的桩是并列的,没有概念上的重复。
2.这多个子类之中有内容概念上重叠.那么需要我们把抽象共同部分和行为共同部分各自独立开来,原来是准备放在一个接口里,
    现在需要设计两个接口：抽象接口和行为接口，分别放置抽象和行为.

例如,一杯咖啡为例,子类实现类为四个：中杯加奶、大杯加奶、 中杯不加奶、大杯不加奶。

但是，我们注意到：上面四个子类中有概念重叠，可从另外一个角度进行考虑，这四个类实际是两个角色的组合：抽象 和行为，其中抽象为：中杯和大杯；行为为：加奶 不加奶（如加橙汁 加苹果汁）.

实现四个子类在抽象和行为之间发生了固定的绑定关系，如果以后动态增加加葡萄汁的行为，就必须再增加两个类：中杯加葡萄汁和大杯加葡萄汁。显然混乱,扩展性极差。

那我们从分离抽象和行为的角度，使用Bridge模式来实现。

如何实现?
以上面提到的咖啡 为例. 我们原来打算只设计一个接口(抽象类),使用Bridge模式后,我们需要将抽象和行为分开,加奶和不加奶属于行为,我们将它们抽象成一个专门的行为接口.

先看看抽象部分的接口代码:


public abstract class Coffee {

    CoffeeImp coffeeImp;

    public void setCoffeeImp() {

        this.coffeeImp = CoffeeImpSingleton.getTheCoffeeImp();
    }

    public CoffeeImp getCoffeeImp() {

        return this.coffeeImp;
    }

    public abstract void pourCoffee();
}

其中CoffeeImp 是加不加奶的行为接口,看其代码如下:

public abstract class CoffeeImp {

    public abstract void pourCoffeeImp();
}


现在我们有了两个抽象类,下面我们分别对其进行继承,实现concrete class:


// 中杯
public class MediumCoffee extends Coffee {

    public MediumCoffee() {

        setCoffeeImp();
    }
    public void pourCoffee() {
        CoffeeImp coffeeImp = this.getCoffeeImp();
        // 我们以重复次数来说明是冲中杯还是大杯 ,重复2次是中杯
        for (int i = 0; i < 2; i++) {

            coffeeImp.pourCoffeeImp();
        }
    }
}


// 大杯
public class SuperSizeCoffee extends Coffee {

    public SuperSizeCoffee() {

        setCoffeeImp();
    }
    public void pourCoffee() {
        CoffeeImp coffeeImp = this.getCoffeeImp();
        // 我们以重复次数来说明是冲中杯还是大杯 ,重复5次是大杯
        for (int i = 0; i < 5; i++) {

            coffeeImp.pourCoffeeImp();
        }
    }
}


上面分别是中杯和大杯的具体实现.下面再对行为CoffeeImp进行继承:

// 加奶
public class MilkCoffeeImp extends CoffeeImp {
    MilkCoffeeImp() {
    }
    public void pourCoffeeImp() {
        System.out.println("加了美味的牛奶");
    }
}

// 不加奶
public class FragrantCoffeeImp extends CoffeeImp {
    FragrantCoffeeImp() {
    }
    public void pourCoffeeImp() {

        System.out.println("什么也没加,清香");
    }
}


Bridge模式的基本框架我们已经搭好了,别忘记定义中还有一句:动态结合,我们现在可以喝到至少四种咖啡:
1.中杯加奶
2.中杯不加奶
3.大杯加奶
4.大杯不加奶

看看是如何动态结合的,在使用之前,我们做个准备工作,设计一个单态类(Singleton)用来hold当前的CoffeeImp:

public class CoffeeImpSingleton {

    private static CoffeeImp coffeeImp;

    public CoffeeImpSingleton(CoffeeImp coffeeImpIn) {

        this.coffeeImp = coffeeImpIn;
    }

    public static CoffeeImp getTheCoffeeImp() {

        return coffeeImp;
    }
}


看看中杯加奶 和大杯加奶 是怎么出来的:

//拿出牛奶
CoffeeImpSingleton coffeeImpSingleton = new CoffeeImpSingleton(new MilkCoffeeImp());

//中杯加奶
MediumCoffee mediumCoffee = new MediumCoffee();
mediumCoffee.pourCoffee();

//大杯加奶
SuperSizeCoffee superSizeCoffee = new SuperSizeCoffee();
superSizeCoffee.pourCoffee();

注意: Bridge模式的执行类如CoffeeImp和Coffee是一对一的关系, 正确创建CoffeeImp是该模式的关键,

Bridge模式在EJB中的应用
EJB中有一个Data Access Object (DAO)模式,这是将商业逻辑和具体数据资源分开的,因为不同的数据库有不同的数据库操作.将操作不同数据库的行为独立抽象成一个行为接口DAO.如下:

1.Business Object (类似Coffee)

实现一些抽象的商业操作:如寻找一个用户下所有的订单

涉及数据库操作都使用DAOImplementor.

2.Data Access Object (类似CoffeeImp)

一些抽象的对数据库资源操作

3.DAOImplementor 如OrderDAOCS, OrderDAOOracle, OrderDAOSybase(类似MilkCoffeeImp FragrantCoffeeImp)

具体的数据库操作,如"INSERT INTO "等语句,OrderDAOOracle是Oracle OrderDAOSybase是Sybase数据库.

4.数据库 (Cloudscape, Oracle, or Sybase database via JDBC API)
 
