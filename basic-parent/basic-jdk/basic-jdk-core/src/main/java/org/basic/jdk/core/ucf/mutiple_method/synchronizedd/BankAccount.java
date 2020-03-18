package org.basic.jdk.core.ucf.mutiple_method.synchronizedd;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <pre>
 * 新的Account提供了两个用于锁定的方法�? *   lock()和unlock()，供Account对象的客户端在需要时锁定Account和解锁Account�?
 *   Account通过委托给BusyFlag来提供这个机制�?
 *   
 *   另外，大家也发现了，新的Account中提供了对Account对象的缓存，同时去除了public的构造方法，改为使用�?��静�?工厂方法供用户获取Account的实例，
 *   这样做也是有必要的，因为我们希望�?��的ATM机同时只能有�?��能够对同�?��Account进行操作，我们在Account上的锁定是对�?��特定Account对象进行
 *   加锁�?如果多个ATM同时实例化多个同�?��user的Account对象，那么仍然可以同时操作同�?��账户�? *   �?��，要使用这种机制就必须保证Account对象在系统中的唯�??，所以，这儿使用�?��Account的缓存，并将Account的构造方法变为私有的。你也可以说�? *   通过在Account类锁上进行同步，即将Account中的BusyFlag对象声明为static的，但这样就使同时只能有�?��ATM机进行操作了�? *   这样，在�?��ATM机在操作时，全市其它的所有的ATM机都必须等待�? *   另外必须注意的一点是�? *     Account中的getAccount()方法必须同步，否则，将有可能生成多个Account对象，因为可能多个线程同时到达这个方法，并监测到accounts�? *     没有“John”的Account实例，从而实例化多个John的Account实例
 * </pre>
 */
public class BankAccount {

    String name;
    // float amount;

    // BusyFlag lock = new BusyFlag();
    Lock lock = new ReentrantLock();

    // 使用�?��Map模拟持久存储
    static Map<String, Float> storage = new HashMap<String, Float>();
    static {
        storage.put("John", new Float(1000.0f));
        storage.put("Mike", new Float(800.0f));
    }

    BankAccount(String name) {

        this.name = name;
        // this.amount = ((Float)storage.get(name)).floatValue();
    }

    public synchronized void deposit(float amt) {

        float amount = storage.get(name).floatValue();
        storage.put(name, new Float(amount + amt));
    }

    public synchronized void withdraw(float amt) throws InsufficientBalanceException {

        float amount = storage.get(name).floatValue();
        if (amount >= amt) {
            amount -= amt;
        } else {
            throw new InsufficientBalanceException();
        }
        storage.put(name, new Float(amount));
    }

    public float getBalance() {

        float amount = storage.get(name).floatValue();
        return amount;
    }

    public void lock() {

        lock.lock();
    }

    public void unlock() {

        lock.unlock();
    }

}
