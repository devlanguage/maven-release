 Prototype原型模式是一种创建型设计模式，它主要面对的问题是："某些结构复杂的对象"的创建工作；由于需求的变化，这些对象经常面临着剧烈的变化，但是他们却拥有比较稳定一致的接口。感觉好像和前几篇所说的设计模式有点分不清，下面我们先来回顾一下以前的几种设计模式，予以区分，再来说说原型模式。

       Singleton单件模式解决的问题是：实体对象个数问题（这个现在还不太容易混）
       FactoryMethod工厂方法模式解决的问题是：某个对象的创建工作
       AbstractFactory抽象工厂模式解决的问题是："一系列互相依赖的对象"的创建工作
       Builder生成器模式解决的问题是："一些复杂对象"的创建工作，子对象变化较频繁，对算法相对稳定
      

      再回来看看今天的Prototype原型模式，它是用来解决"某些结构复杂的对象"的创建工作。现在看看，好象还是差不多。这个问题先放在这，我们先往下看Prototype原型模式。

  《设计模式》中说道：使用原型实例指定创建对象的种类，然后通过拷贝这些原型来创建新的对象。

  此时注意：原型模式是通过拷贝自身来创建新的对象，这一点和其他创建型模式不相同。好，我们再来看看原型模式的结构
  原型模式：
    1、定义：原型模式就是通过一个原型对象来表明要创建的对象类型，然后用复制这个对象的方法来创建更痛类型的对象。
    2、原理：有两部分组成，抽象原型和具体原型。
    3、使用时机：系统需要 创建吃的对象是动态加载的，而且产品具有一定层次时，可以考虑使用原型模式。
        1>当要实例化的类是在运行时刻指定时，例如，通过动态装载；
        2>或者为了避免创建一个与产品类层次平行的工厂类层次时；
        3>或者当一个类的实例只能有几个不同状态组合中的一种时。
        4>建立相应数目的原型并克隆它们可能比每次用合适的状态手工实例化该类更方便一些。
    4、效果：
            1>可以再运行时刻增加和删除产品。
            2>可以通过改变值来指定产品。
            3>可以通过改变结构来指定新对象。
            4>减少子类的构造
            5>可以用类动态配置应用。
    5、实现：
            1>使用一个原型管理器
            2>实现克隆操作（浅拷贝和深拷贝）
            3>初始化克隆对象。  
    6、使用原型模式的意图：用原型实例指定创建对象的种类，并且通过拷贝这些原型创建新的对象。
    7、解决的问题：
            比如有一个对象，在某一时刻该对象中已经包含了一些有效的值，此时可能会需要一个和该对象完全相同的新对象，并且此后对新对象的任何改动都不会影响到原来对象中的值，也就是说新对象与原来的对象是两个独立的对象，但新对象的初始值是由原来的对象确定的。
Clone：
    赋值创建对象：
        1>java中赋值创建对象是可以实现对象的重用的，但是新对象和原对象是同一个引用；如果修改其中的一个对象的值，则另外的一个对象也会发生改变。
        2>使用clone方法会返回对象的一个拷贝，这样一来，如果修改一个对象的值，则另外的对象不会发生改变的。
  
    这个结构说明原型模式的客户端程序（ClientApp）是依赖于抽象（Prototype），而对象的具体实现也是依赖于抽象（Prototype）。符合设计模式原则中的依赖倒置原则——抽象不应依赖于具体实现，具体实现应依赖于抽象。

我们现在回来看看原型模式的实现，我定义了一个场景，
   一个人开这一辆车在一条公路上。现在这件事是确定的，但不确定的有几点：
    1、人：姓名，性别，年龄；
    2、车：什么牌子的；
    3、公路：公路名字，长度，类型（柏油还是土路）。现在我们一个个实现。
    
 先来实现人，定义一个抽象类，AbstractDriver，具体实现男性（Man）和女性（Women） 
public abstract class AbstractDriver {

    public AbstractDriver() {
        //
        // TODO: 在此处添加构造函数逻辑
        //
    }

    public String name;
    public String sex;
    public int age;
    public abstract String drive();
    public abstract AbstractDriver Clone();
}


 

    public class Man:AbstractDriver
    {
        public Man(string strName,int intAge)      {

            sex = "Male";
            name = strName;
            age = intAge;
        }

        public override string drive()
        {
            return name + " is drive";
        }

 

        public override AbstractDriver Clone()

        {
            return (AbstractDriver)this.MemberwiseClone();

        }

    }

 

   

    注意：抽象代码中有一个Clone的方法，个人认为这个方法是原型模式的一个基础，因为前面讲了原型模式是通过拷贝自身来创建新的对象。

      下面我们再来实现公路和汽车

      公路：

public abstract class AbstractRoad

    {

        public AbstractRoad()

        {

            //

            // TODO: 在此处添加构造函数逻辑

            //

        }

 

        public string Type;

        public string RoadName;

        public int RoadLong;

 

        public abstract AbstractRoad Clone();

    }

 

    public class Bituminous:AbstractRoad    //柏油路

    {

        public Bituminous(string strName,int intLong)

        {

            RoadName = strName;

            RoadLong = intLong;

            Type = "Bituminous";

        }

 

        public override AbstractRoad Clone()

        {

            return (AbstractRoad)this.MemberwiseClone();

        }

    }

 

    public class Cement:AbstractRoad        //水泥路

    {

        public Cement(string strName,int intLong)

        {

            RoadName = strName;

            RoadLong = intLong;

            Type = "Cement";

        }

 

        public override AbstractRoad Clone()

        {

            return (AbstractRoad)this.MemberwiseClone();

        }

    }

    

    汽车：

    public abstract class AbstractCar

    {

        public AbstractCar()

        {

            //

            // TODO: 在此处添加构造函数逻辑

            //

        }

 

        public string OilBox;

        public string Wheel;

        public string Body;

 

        public abstract string Run();

        public abstract string Stop();

 

        public abstract AbstractCar Clone();

    }

 

    public class BMWCar:AbstractCar

    {

        public BMWCar()

        {

            OilBox = "BMW's OilBox";

            Wheel = "BMW's Wheel";

            Body = "BMW's body";

        }

 

        public override string Run()

        {

            return "BMW is running";

        }

 

        public override string Stop()

        {

            return "BMW is stoped";

        }

 

        public override AbstractCar Clone()

        {

            return (AbstractCar)this.MemberwiseClone();

        }

    }

 

    public class BORACar:AbstractCar

    {

        public BORACar()

        {

            OilBox = "BORA's OilBox";

            Wheel = "BORA's Wheel";

            Body = "BORA's Body";

        }

 

        public override string Run()

        {

            return "BORA is running";

        }

 

        public override string Stop()

        {

            return "BORA is stoped";

        }

 

        public override AbstractCar Clone()

        {

            return (AbstractCar)this.MemberwiseClone();

        }

    }

 

    public class VolvoCar:AbstractCar

    {

        public VolvoCar()

        {

            OilBox = "Volvo's OilBox";

            Wheel = "Volvo's Wheel";

            Body = "Volvo's Body";

        }

 

        public override string Run()

        {

            return "Volvo is running";

        }

 

        public override string Stop()

        {

            return "Volvo is stoped";

        }

 

        public override AbstractCar Clone()

        {

            return (AbstractCar)this.MemberwiseClone();

        }

    }

    然后我们再来看看场景，我们定义一个Manage类，在这个场景中有一个人，一辆车和一条公路，代码实现如下：

class Manage

    {

        public AbstractCar Car;

        public AbstractDriver Driver;

        public AbstractRoad Road;

 

        public void Run(AbstractCar car,AbstractDriver driver,AbstractRoad road)

        {

            Car = car.Clone();

            Driver = driver.Clone();

            Road = road.Clone();

        }

    }

    可以看到，在这个代码中，场景只是依赖于那几个抽象的类来实现的。最后我们再来实现一下客户代码，比如我现在要一辆Volvo车，一个叫"Anli"的女司机，在一条叫"Road1"、长1000的柏油路上。

        static void Main(string[] args)

        {

            Manage game = new Manage();

            game.Run(new VolvoCar(),new Women("Anli",18),new Bituminous("Road1",1000));

            Console.Write("CarRun:" + game.Car.Run() + "\n");

            Console.Write("DriverName:" + game.Driver.name + "\n");

            Console.Write("DriverSex:" + game.Driver.sex + "\n");

            Console.Write("RoadName:" + game.Road.RoadName + "\n");

            Console.Write("RoadType:" + game.Road.Type + "\n");

            Console.Write("CarStop:" + game.Car.Stop() + "\n");

            Console.Read();

        }

    运行的结果是：

    CarRun:Volvo is running

DriverName:Anli

DriverSex:Female

RoadName:Road1

RoadType:Bituminous

CarStop:Volvo is stoped

 

如果我现在想换成BORA车，让我（kid-li）开，在一个水泥马路上，我们只要更改Main函数中Run的实参。

game.Run(new BORACar(),new Man("kid-li",24),new Cement("Road1",1000));

运行结果是：

CarRun:BORA is running

DriverName:kid-li

DriverSex:Male

RoadName:Road1

RoadType:Cement

CarStop:BORA is stoped

这样，经过简单的更改，可以实现实现细节的变化。

现在我们再来看看原型模式的几个要点：

1、Prototype模式同样用于隔离类对象的使用者和具体类型（易变类）之间的耦合关系，它同样要求这些"易变类"拥有"稳定的接口"。

2、Prototype模式对于"如何创建易变类的实体对象"采用"原型克隆"的方法来实现，它使得我们可以非常灵活地动态创建"拥有某些稳定接口"的新对象——所需工作仅仅是注册一个新类的对象（即原型），然后在任何需要的地方不断地Clone。

3、Prototype模式中的Clone方法可以利用Object类的MemberwiseClone（）或者序列化来实现深拷贝。

这里面我们再来说说浅拷贝和深拷贝。我想对于Prototype模式是很重要的。我觉得浅拷贝和深拷贝的关键区别是对于引用对象的拷贝。例如我们有一个类Class1，

public class Class1

{

    int a;

    int[] b;

}

我们用浅拷贝实现了两个对象c1和c2，对于c1.a和c2.a，他们所有的内存空间是不一样的，但是c1.b和c2.b，由于它们是引用类型，在浅拷贝时只是拷贝了一个地址给b成员，实际上c1.b和c2.b指向同一块内存。

但如果我们用深拷贝，c1.b和c2.b指向的是不同的内存地址。那又如何实现深拷贝呢？我们可以利用序列化和反序列化来实现。
