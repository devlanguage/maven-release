
import java.net.URL;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class ClusteredServer02 {

    public static void main(String[] args) throws Exception {

        URL url = ClusteredServer02.class.getClassLoader().getResource("ehcache_cluster_02.xml");
        CacheManager manager = new CacheManager(url);
        // ..Cache
        Cache cache = manager.getCache("UserCache");

        while (true) {
            Element e = cache.get("key1");
            if (e != null) {
                System.out.println("cache cluster succeed! Syrchorinized the Elemenet: ");
                printCacheElements(cache);
                break;
            }
            Thread.sleep(1000);
        }
        manager.shutdown();
    }

    public static void printCacheElements(Cache cache) {

        System.out.println("------------------Cache Elements-------------------------");
        java.util.List<java.io.Serializable> keys = cache.getKeys();
        for (java.io.Serializable key : keys) {
            System.out.println(cache.getName() + ":" + cache.get(key));
        }
    }

}
