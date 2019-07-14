package org.basic.grammar.pattern.creational.Singleton.test1;

/**
 * A new Singleton use registry
 */
import java.util.HashMap;
import java.util.Map;

// 饿汉式、懒汉式都不能被继承
// 而登记式可以被继承。
public class Singleton3_Register {

    static private Map<String, Singleton3_Register> m_registry = new HashMap<String, Singleton3_Register>();
    static {
        Singleton3_Register x = new Singleton3_Register();
        m_registry.put(Singleton3_Register.class.getName(), x);
    }

    protected Singleton3_Register() {
    }

    public synchronized static final Singleton3_Register getInstance(String name) {

        if (name == null) {
            name = Singleton3_Register.class.getName();
        }
        if (m_registry.get(name) == null) {
            try {
                m_registry.put(name, (Singleton3_Register) Class.forName(name).newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return m_registry.get(name);
    }
}