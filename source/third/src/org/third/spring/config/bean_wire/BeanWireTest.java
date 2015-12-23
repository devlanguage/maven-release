package org.third.spring.config.bean_wire;

import org.third.spring.SpringTest;

public class BeanWireTest extends SpringTest {

    public BeanWireTest(String configFile) {
        super(configFile);
    }

    public static void main(String[] args) {

        new BeanWireTest("spring_bean.xml").test();
    }

    public void test() {
        // 如果一个singleton bean要引用另外一个singleton bean，或者一个非singleton
        // bean要引用另外一个非singleton bean时，通常情况下将一个bean定义为另一个bean的property值就可以了
        System.out.println("================== Singleton Bean ==========================");
        StudentManager studentManager = (StudentManager) ctx.getBean("studentManager");
        System.out.println(studentManager);

        Student stu1 = (Student) ctx.getBean("stu1");
        System.out.println(stu1);
        Student stu2 = (Student) ctx.getBean("stu2");
        System.out.println(stu2);

        // 不过对于具有不同生命周期的bean来说这样做就会有问题了，比如在调用一个singleton类型bean
        // A的某个方法时，需要引用另一个非singleton（prototype）类型的bean B，对于bean
        // A来说，容器只会创建一次，这样就没法在需要的时候每次让容器为bean A提供一个新的的bean B实例
        System.out.println("==================Non-Singleton Bean ==========================");
        System.out
                .println("==放弃控制反转。通过实现BeanFactoryAware接口（见这里）让bean A能够感知bean 容器，并且在需要的时候通过使用getBean(\"B\")方式（见这里）向容器请求一个新的bean B实例");
        CommandService_BeanFactoryAware cm = (CommandService_BeanFactoryAware) ctx
                .getBean("cmdService_BeanFactoryAware");
        System.out.println(cm.createCommand());
        System.out.println(cm.createCommand());

        System.out.println(ctx.getBean("command"));
        System.out.println(ctx.getBean("command"));

        System.out.println("==Lookup Method Test");
        // CommandManager_Lookup cmLookUp = (CommandManager_Lookup)
        // ctx.getBean("commandManager_lookup");
        Command cmdLookup1 = (Command) ctx.getBean("command_lookup");
        Command cmdLookup2 = (Command) ctx.getBean("command_lookup");
        System.out.println("command from Lookup: " + cmdLookup1);
        System.out.println("command from Lookup: " + cmdLookup2);

        System.out.println("==Replaced Method Test");
        CmdRepService_Original cmdRepService_Original = (CmdRepService_Original) ctx.getBean("cmdRepService_Original");
        cmdRepService_Original.businessMethod("commandStateTest parameter");
    }
}