package org.third.cache.ehcache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.store.FifoPolicy;

public class EhCacheTest {

    /**
     * ##############################################################################
     * 
     * @DESCRIBE
     * @param args
     * @throws InterruptedException
     * 
     *             ##############################################################################
     */
    public static void main(String[] args) throws InterruptedException {

        CacheManager manager = new CacheManager(EhCacheTest.class.getClassLoader().getResource("ehcache.xml").getPath());

        String[] cacheNames = manager.getCacheNames();
        System.out.println("读取的缓存列表为：");
        for (int i = 0; i < cacheNames.length; i++) {
            System.out.println("\t" + (i + 1) + " " + cacheNames[i]);
        }

        Cache cache1 = manager.getCache("cache1");
        // cache1.setStatisticsEnabled(true);
        cache1.setDisabled(false);
        // cache1.setDiskStorePath("c:\temp")
        cache1.setMemoryStoreEvictionPolicy(new FifoPolicy());
        // cache1.setSampledStatisticsEnabled(true);
        // cache1.setStatisticsAccuracy(Statistics.STATISTICS_ACCURACY_BEST_EFFORT);
        for (int i = 0; i < 50; i++) {
            Element element = new Element("key-" + i, "value1");
            cache1.put(element);
        }

        Element element = cache1.get("key-1");
        System.out.println("序列化后的值为：" + element.getValue());
        System.out.println("未序列化的值为：" + element.getObjectValue());

        int elementsInMemory = cache1.getSize();
        System.out.println("得到缓存的对象数量：" + elementsInMemory);

        long elementsInMemory1 = cache1.getMemoryStoreSize();
        System.out.println("得到缓存对象占用内存的数量：" + elementsInMemory1);

        long elementsInMemory2 = cache1.getDiskStoreSize();
        System.out.println("得到缓存对对象占用磁盘的数量：" + elementsInMemory2);

        // int hits = cache.getAverageGetTime();
        // System.out.println("得到缓存读取的命中次数：" + hits);
        //
        // int hits1 = cache.getMemoryStoreHitCount();
        // System.out.println("得到内存中缓存读取的命中次数：" + hits1);
        //
        // int hits2 = cache.getDiskStoreHitCount();
        // System.out.println("得到磁盘中缓存读取的命中次数：" + hits2);
        //
        // int hits3 = cache.getMissCountNotFound();
        // System.out.println("得到缓存读取的丢失次数：" + hits3);
        //
        // int hits4 = cache.getMissCountExpired();
        // System.out.println("得到缓存读取的已经被销毁的对象丢失次数：" + hits4);

        manager.shutdown();
    }

}
