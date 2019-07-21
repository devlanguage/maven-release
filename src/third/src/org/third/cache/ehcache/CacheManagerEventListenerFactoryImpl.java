
import java.util.Properties;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.event.CacheManagerEventListener;
import net.sf.ehcache.event.CacheManagerEventListenerFactory;

public class CacheManagerEventListenerFactoryImpl extends CacheManagerEventListenerFactory {

    /*
     * (non-Javadoc)
     * 
     * @see
     * net.sf.ehcache.event.CacheManagerEventListenerFactory#createCacheManagerEventListener(net.sf.ehcache.CacheManager
     * , java.util.Properties)
     */
    public CacheManagerEventListener createCacheManagerEventListener(CacheManager arg0, Properties arg1) {
        CacheManagerEventListener listener = new CacheManagerEventListenerImpl();

        System.out.println("Add event listern");
        return listener;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.ehcache.event.CacheManagerEventListenerFactory#createCacheManagerEventListener(java.util.Properties)
     */
    public CacheManagerEventListener createCacheManagerEventListener(Properties arg0) {
        // TODO Auto-generated method stub
        return null;
    }

}
