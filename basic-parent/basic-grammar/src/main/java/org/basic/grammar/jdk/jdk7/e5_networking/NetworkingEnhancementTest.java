package org.basic.grammar.jdk.jdk7.e5_networking;

/**
 * <pre>
 * 新增URLClassLoader.close()方法，可以及时关闭资源，后续重新加载class文件时不会导致资源被占用或者无法释放问题
 * URLClassLoader.newInstance(new URL[]{}).close();
 * 新增Sockets Direct Protocol
 *   绕过操作系统的数据拷贝，将数据从一台机器的内存数据通过网络直接传输到另外一台机器的内存中
 * </pre>
 * 
 * @author ygong
 *
 */
public class NetworkingEnhancementTest {

}
