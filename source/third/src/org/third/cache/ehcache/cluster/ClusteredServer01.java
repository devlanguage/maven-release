
import java.net.URL;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class ClusteredServer01 {

    public static void main(String[] args) throws Exception {

        URL url = ClusteredServer01.class.getResource("ehcache_cluster_01.xml");
        CacheManager manager = new CacheManager(url);
        // 取得Cache
        Cache cache = manager.getCache("UserCache");
        cache.put(new Element("key1", "value1"));
        cache.put(new Element("key2", "value2"));
        cache.put(new Element("key3", "value3"));

        
        Element element1 = cache.get("key1");
        System.out.println(element1.getValue());
        
        manager.shutdown();
        
    }
}
