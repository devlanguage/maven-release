
import net.sf.ehcache.CacheException;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.event.CacheEventListener;

public class CacheEventListenerImpl implements CacheEventListener {

    public void dispose() {

    }

    public void notifyElementEvicted(Ehcache cache, Element element) {

    }

    public void notifyElementExpired(Ehcache cache, Element element) {

    }

    public void notifyElementPut(Ehcache cache, Element element)

    throws CacheException {

        System.out.println(element.getKey() + " was added.");

    }

    public void notifyElementRemoved(Ehcache cache, Element element)

    throws CacheException {

        System.out.println(element.getKey() + " was removed.");

    }

    public void notifyElementUpdated(Ehcache cache, Element element)

    throws CacheException {

    }

    public void notifyRemoveAll(Ehcache cache) {

    }

    @Override
    public Object clone() throws CloneNotSupportedException {

        return super.clone();

    }

}
