package org.basic.jdk.core.serializable;

/**
 * Created on Jun 9, 2014, 9:34:52 AM
 */
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class SerialPerson implements Externalizable {// 此类的对象可以被序列化
    private transient String name; // 声明name属性
    private int age; // 声明age属性

    public SerialPerson() {
    } // 必须定义无参构造

    public SerialPerson(String name, int age) { // 通过构造方法设置属性内容
        this.name = name;
        this.age = age;
    }

    public String toString() { // 覆写toString()方法
        return "姓名：" + this.name + "；年龄：" + this.age;
    }

    // 覆写此方法，根据需要读取内容，反序列化时使用
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.name = (String) in.readObject(); // 读取姓名属性
        this.age = in.readInt(); // 读取年龄
    }

    // 覆写此方法，根据需要可以保存属性或具体内容， 序列化时使用
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(this.name); // 保存姓名属性
        out.writeInt(this.age); // 保存年龄属性
    }
}