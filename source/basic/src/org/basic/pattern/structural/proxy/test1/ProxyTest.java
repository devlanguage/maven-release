package org.basic.pattern.structural.proxy.test1;

/**
 * 
 * 由下面的代码可以看出，客户实际需要调用的是RealSubject类的request()方法，现在用ProxySubject来代理RealSubject类，同样达到目的，
 * 同时还封装了其他方法(preRequest(),postRequest())，可以处理一些其他问题。
 * 
 * 另外，如果要按照上述的方法使用代理模式，那么真实角色必须是事先已经存在的，并将其作为代理对象的内部属性。 但是实际使用时，一个真实角色必须对应一个
 * 代理角色，如果大量使用会导致类的急剧膨胀；此外，如果事先并不知道<code>真实角色</code>，该如何使用代理呢？这个问题可以通过Java的动态代理类来解决。
 * 
 */
public class ProxyTest {

    public static void main(String[] args) {

        AbstractRole abstractRole = new ProxyRole();
        abstractRole.request();
    }
}
