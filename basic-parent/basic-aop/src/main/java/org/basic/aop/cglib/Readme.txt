反编译tips

大家都知道cglib是进行bytecode操作，会动态生成class，最快最直接的学习就是结合他生成的class，对照代码进行学习，效果会好很多。
Java代码  
system.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "指定输出目录");　  
 可参见 cores/DebuggingClassWriter代码。说明：这样cglib会将动态生成的每个class都输出到文件中，然后我们可以通过decomp进行反编译查看源码。


4.1 Cglib代理基本原理
    通过动态的生成一个子类覆盖所要代理类的非final的方法，并设置好回调接口（callback），则原有类的每个方法调用就会转变成调用用户定义的拦截方法（interceptors）。
从obj.getClass().getInterfaces()可以看出，如果直接用JDK的反射需要创建接口，接口是用来搞架构的，但是对于非常非常小的项目去写接口有点麻烦。
而CgLib就可以不用接口，它底层调用asm动态生成一个代理类去覆盖父类中非final的方法，然后实现MethodInterceptor接口的intercept方法，这样以后直接调用重写的方法，比JDK要快。
但是加载cglib消耗时间比直接jdk反射时间长，开发的过程中，如果是反复动态生成新的代理类推荐用jdk自身的反射，反之用cglib.
    cglib（Code Generation Library）是一个强大的,高性能,高质量的Code生成类库。它可以在运行期扩展Java类与实现Java接口。
    cglib封装了asm，可以在运行期动态生成新的class。
    cglib用于AOP，jdk中的proxy必须基于接口，cglib却没有这个限制。
4.2 Cglib代理常用类介绍

生成代理的类：net.sf.cglib.proxy.Enhancer，该类最常用的两个属性为：
    private Class superclass;//被代理的类
    private Callback[] callbacks;//回调接口 
最常用的回调接口：net.sf.cglib.proxy.MethodInterceptor，该接口只定义了一个方法：
    public Object intercept(Object object, Method method,Object[] args, MethodProxy proxy) throws Throwable;
对被代理类的每个方法调用都转变成调用该方法。对原始方法的调用可以通过Method反射调用，也可通过MethodProxy调用，后者更快。 

4.3 Cglib创建代理简单应用示例
用Cglib创建代理实例只需下面4步
    Enhancer enhancer = new Enhancer();//①实例化
    enhancer.setSuperclass(targetClass);//②设置被代理的类
    enhancer.setCallback(callBack);//③设置回调
    return enhancer.create();//④用无参构造器创建代理实例
//enhancer.create(argumentTypes, arguments)//用含参构造器创建代理实例

4.4 实现的自动日志代理类

有了上面的知识，用cglib实现一个自动日志代理类就轻而易举了，下面是部分代码：

public class LogProxy {
    private String methodNameExpression;// 要打印日志的方法名正则表达式
    private boolean logInvokeParams = false;// 默认不打印入参
    private boolean logInvokeResult = true;// 默认打印调用结果
    // 构造器
    public LogProxy(String methodNameExpression) {
        this.methodNameExpression = methodNameExpression;
    }
    // builder方式设置是否需要打印入参
    public LogProxy logInvokeParams(boolean needed) {
        logInvokeParams = needed;
        return this;
    }

    // builder方式设置是否需要打印结果
    public LogProxy logInvokeResult(boolean needed) {

        logInvokeResult = needed;
        return this;
    }

    // 创建代理实例
    public Object createProxy(Class targetClass, Object... arguments) {

        Class[] argumentTypes = new Class[arguments.length];
        for (int i = 0; i < arguments.length; i++) {
            argumentTypes[i] = arguments[i].getClass();
        }
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(targetClass);
        enhancer.setCallback(new LogMethodInterceptor(methodNameExpression, logInvokeParams,
                logInvokeResult));
        return enhancer.create(argumentTypes, arguments);
    }

    // 回调实现
    private class LogMethodInterceptor implements MethodInterceptor {
        public LogMethodInterceptor(String methodNameExpression, boolean logInvokeParams,
                boolean logInvokeResult) {
        }
        public Object intercept(Object object, Method method, Object[] args, MethodProxy methodProxy)
                throws Throwable {
            return null;
        }
    }
}

详细代码请见svn：http://svn.test.taobao.net/repos/test-svnrepos/testhouse/top-test/test-support/src/main/java/com/taobao/testsupport/util/LogProxy.java
用该工具创建的实例调用前后打印出的日志示例如下：

UserGetTest.testUserGet (69) |TaobaoDirectJsonRestClient.userGet invoked with params:
param 1: com.taobao.api.model.UserGetRequest@d12eea[
nick=tiptest1
fields=user_id,nick,sex
]
UserGetTest. testUserGet (69) |TaobaoDirectJsonRestClient.userGet invoked result is:
com.taobao.api.model.UserGetResponse@272961[
user=com.taobao.api.model.User@1584807
errorCode=
msg=
redirectUrl=
body={"rsp":{"users":[{"nick":"tiptest1","sex":"f","user_id":176104893}]}}
headers=
]]

最后要说明的一点是，是否需要代理及打印日志都是通过log4j来配置的，这样可以灵活控制。详见svn：http://svn.test.taobao.net/repos/test-svnrepos/testhouse/top-test/topApi-test-v2/src/test/java/com/taobao/top/ClientFactory.java

5 小结

本文主要介绍了如何使用Cglib来实现切面编程，并通过一个自动日志代理类来展示了在不改变原有类代码的情况下增强功能。当然，非特殊情况下的切面实现，还是推荐使用Spring Aop。

