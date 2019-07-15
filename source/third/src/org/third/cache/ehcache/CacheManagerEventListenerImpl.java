
import net.sf.ehcache.CacheException;
import net.sf.ehcache.Status;
import net.sf.ehcache.event.CacheManagerEventListener;

public class CacheManagerEventListenerImpl implements CacheManagerEventListener {

    @Override
    public void dispose() throws CacheException {

    }

    @Override
    public Status getStatus() {

        return null;
    }

    @Override
    public void init() throws CacheException {

    }

    @Override
    public void notifyCacheAdded(String cacheName) {

        System.out.println("Cache [" + cacheName + "] Added");

    }

    @Override
    public void notifyCacheRemoved(String cacheName) {

        System.out.println("Cache [" + cacheName + "] Deleted");

    }

}
