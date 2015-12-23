/****************************************************************************
 *                 TELLABS PROPRIETARY AND CONFIDENTIAL
 *              UNPUBLISHED WORK COPYRIGHT 2009 TELLABS
 *                          ALL RIGHTS RESERVED
 *      NO PART OF THIS DOCUMENT MAY BE USED OR REPRODUCED WITHOUT
 *                   THE WRITTEN PERMISSION OF TELLABS.
 *  Last modifed on 9:34:39 AM Jun 9, 2014
 *
 *****************************************************************************
 */
package org.basic.grammar.serializable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * <pre>
 * 1. 序列化是的作用和用途 
 * 序列化，就是为了保存对象的状态；而与之对应的反序列化，则可以把保存的对象状态再读出来。 
 * 简言之：序列化/反序列化，是Java提供一种专门用于的保存/恢复对象状态的机制。 一般在以下几种情况下，我们可能会用到序列化： 
 *  a）当你想把的内存中的对象状态保存到一个文件中或者数据库中时候； 
 *  b）当你想用套接字在网络上传送对象的时候；
 *  c）当你想通过RMI传输对象的时候
 * </pre>
 * 
 * Externalizable接口与Serializable接口实现序列化的区别
 * 
 * <table border="1">
 * <tr>
 * <td>区 别</td>
 * <td>Serializable</td>
 * <td>Externalizable</td>
 * </tr>
 * 
 * <tr>
 * <td>实现复杂度</td>
 * <td>实现简单，Java对其 有内建支持</td>
 * <td>实现复杂， 由开发人员自己完成</td>
 * </tr>
 * *
 * <tr>
 * <td>执行效率</td>
 * <td>所有对象由Java统一保存，性能较低</td>
 * <td>开发人员决定哪个对象保存，可能造成速度提升</td>
 * </tr>
 * *
 * <tr>
 * <td>执行效率</td>
 * <td>保存时占用空间大</td>
 * <td>部分存储，可能造成空间减少</td>
 * </tr>
 * </table>
 * Created on Jun 9, 2014, 9:34:39 AM
 */
public class SerDemo03 {
    public static void main(String[] args) throws Exception {
        SerialPerson s1 = new SerialPerson("张三", 30);
        ser(s1); // 序列化
        dser(); // 反序列化
        
        SerialBox s2 = new SerialBox("张三", 30, 20);
        ser(s2); // 序列化
        dser(); // 反序列化
    }

    public static void ser(Object s1) throws Exception { // 序列化操作
        File f = new File("D:" + File.separator + "test.txt");
        ObjectOutputStream oos = null;
        OutputStream out = new FileOutputStream(f); // 文件输出流
        oos = new ObjectOutputStream(out); // 为对象输出流实例化
        oos.writeObject(s1); // 保存对象到文件
        oos.close(); // 关闭输出
    }

    public static void dser() throws Exception { // 反序列化操作
        File f = new File("D:" + File.separator + "test.txt");
        ObjectInputStream ois = null;
        InputStream input = new FileInputStream(f); // 文件输出流
        ois = new ObjectInputStream(input); // 为对象输出流实例化
        Object obj = ois.readObject(); // 读取对象
        ois.close(); // 关闭输出
        System.out.println(obj);
    }
}