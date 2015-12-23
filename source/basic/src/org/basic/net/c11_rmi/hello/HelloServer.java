package org.basic.net.c11_rmi.hello;

public class HelloServer {
    public final static int rmiregistry_port = 1099;

    /**
     * 启动 RMI 注册服务并进行对象注册
     */
    public static void main(String[] argv) {
        try {
            // 启动RMI注册服务，指定端口为1099　（1099为默认端口）
            // 也可以通过命令 ＄java_home/bin/rmiregistry 1099启动
            // 这里用这种方式避免了再打开一个DOS窗口
            // 而且用命令rmiregistry启动注册服务还必须事先用RMIC生成一个stub类为它所用
            java.rmi.registry.LocateRegistry.createRegistry(rmiregistry_port);

            // 创建远程对象的一个或多个实例，下面是hello对象
            // 可以用不同名字注册不同的实例
            UserInfoImpl userInfoImpl = new UserInfoImpl();

            // If UserInfoImpl not inherit the java.rmi.UniCastRemoteObject, line below should be called maunnly
            // UserInfoInterface userInfoInterface = (UserInfoInterface)
            // UnicastRemoteObject.exportObject(userInfoImpl,rmiregistry_port);

            // 绑定的URL标准格式为：rmi://host:port/name(其中协议名可以省略，下面两种写法都是正确的）
            java.rmi.Naming.rebind("Hello", userInfoImpl);

            // 如果要把hello实例注册到另一台启动了RMI注册服务的机器上
            // Naming.rebind("//192.168.1.105:1099/Hello",hello);

            System.out.println("Hello Server is ready.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Hello Server failed: " + e);
        }
    }
}