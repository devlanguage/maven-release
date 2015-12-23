package org.basic.jdk.jdk7.e5_networking.ws.server;

import java.io.Serializable;
import java.util.ArrayList;

class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private int age;
    private String userName;

    public User(String userName) {
        this(userName, 20);
    }

    public User(String userName, int age) {
        this.userName = userName;
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}

public interface HelloService {
//    public User sayHello(String userName, int age);
//    public User[] listUser(String userName);
    // public Future<?> calculateSalary(AsyncHandler<Integer> handler, final int number1, final int number2);

}
