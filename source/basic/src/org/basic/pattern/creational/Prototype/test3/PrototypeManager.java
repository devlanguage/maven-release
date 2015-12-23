/**
 * Copyright reserved by Tellabs Communication Corp. LTD. The file
 * org.basic.pattern.创建型.Prototype.test3.PrototypeManager.java is created on 2008-8-21
 */
package org.basic.pattern.creational.Prototype.test3;

/**
 * * *
 * <p>
 * Title: 原型管理器
 * </p> * *
 * <p>
 * Description:实现了自动注册原型的功能
 * </p> *
 * <p>
 * 原型管理器运行时只要一个实例，因此能够用singleton模式来实现.
 * </p> *
 * <p>
 * 有关singleton的参照其他blog.
 * </p> *
 * <p>
 * Copyright: Copyright (c) 2005
 * </p> * *
 * <p>
 * Company:www.hhzskj.com
 * </p> * *
 * 
 * @author meconsea *
 * @version 1.0
 */
import java.util.HashMap;

public class PrototypeManager {

    /** * 关于HashMap和HashTable的区别参考其他blog */
    private HashMap hm = null;

    private static PrototypeManager prototypeManager = null;

    private PrototypeManager() {

        hm = new HashMap();
    }

    public static synchronized PrototypeManager getPrototypeManager() {

        if (prototypeManager == null) {
            prototypeManager = new PrototypeManager();
        }
        return prototypeManager;
    }

    /**
     * * 注册 *
     * 
     * @param name
     *            String *
     * @param prototype
     *            Object
     */
    public void register(String name, Object prototype) {

        hm.put(name, prototype);
    }

    /**
     * * 解除注册 *
     * 
     * @param name
     *            String
     */
    public void unRegister(String name) {

        hm.remove(name);
    }

    /**
     * * 获得原型实例 *
     * 
     * @param name
     *            String *
     * @return Object
     */
    public Object getPrototype(String name) {

        Object o = null;
        if (hm.containsKey(name)) {
            o = hm.get(name);
        } else {
            try {
                /** * 自动查找原型管理器里不存在的类，并动态生成他 */
                o = Class.forName(name).newInstance();
                this.register(name, o);
            } catch (Exception e) {
                System.out.println("class " + name + " don't define " + e.getMessage());
                e.printStackTrace();
            }
        }
        return o;
    }
}
