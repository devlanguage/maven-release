package org.third.cache.ehcache.cluster;

import java.io.Serializable;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class ClusteredEhcache {

    private CacheManager cManager;

    public CacheManager createCacheManager(String path) {

        return new CacheManager(path);
    }

    public void printCacheElements(Cache cache) {
        cache.getCacheManager().getName();
        System.out.println("------------------Cache Elements-------------------------");
        java.util.List<java.io.Serializable> keys = cache.getKeys();
        for (java.io.Serializable key : keys) {
            System.out.println(cache.getName() + ":" + cache.get(key));
        }
    }

    public static void main(String[] args) {

        String ehcacheFile1 = ClusteredEhcache.class.getResource("EhCache1.xml").getPath();
        String ehcacheFile2 = ClusteredEhcache.class.getResource("EhCache2.xml").getPath();
        String ehcacheFile3 = ClusteredEhcache.class.getResource("EhCache3.xml").getPath();

        ClusteredEhcache clusterEhcache = new ClusteredEhcache();
        CacheManager cacheManager1 = clusterEhcache.createCacheManager(ehcacheFile1);
        CacheManager cacheManager2 = clusterEhcache.createCacheManager(ehcacheFile2);
        CacheManager cacheManager3 = clusterEhcache.createCacheManager(ehcacheFile3);

        Cache cache1 = cacheManager1.getCache("simpleCache");
        Cache cache2 = cacheManager2.getCache("simpleCache");
        Cache cache3 = cacheManager3.getCache("simpleCache");

        cache1.put(new Element("cache1", "cache1"));
        cache2.put(new Element("cache2", "cache2"));
        cache3.put(new Element("cache3", "cache3"));

//        cache1.flush();
//        cache2.flush();
//        cache3.flush();
        
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        clusterEhcache.printCacheElements(cache1);   
        clusterEhcache.printCacheElements(cache2);   
        clusterEhcache.printCacheElements(cache3);

        cacheManager1.shutdown();
        cacheManager2.shutdown();
        cacheManager3.shutdown();
    }
}
