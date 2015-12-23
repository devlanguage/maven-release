/**
 * Clever Little Trader
 *
 * Jubilee Group and Logical Cobwebs, 2002
 */
package org.logicalcobwebs.proxool;

import junit.framework.TestCase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.logicalcobwebs.concurrent.ReaderPreferenceReadWriteLock;

import java.util.Stack;

/**
 * Provides common code for all Proxool tests
 * @version $Revision: 1.1 $, $Date: 2015/06/30 06:16:29 $
 * @author bill
 * @author $Author: ygong $ (current maintainer)
 * @since Proxool 0.8
 */
public abstract class AbstractProxoolTest extends TestCase {

    private static final Log LOG = LogFactory.getLog(AbstractProxoolTest.class);

    private String alias;

    private static ReaderPreferenceReadWriteLock testLock = new ReaderPreferenceReadWriteLock();

    private Stack threadNames = new Stack();

    public AbstractProxoolTest(String alias) {
        super(alias);
        this.alias = alias;
    }

    /**
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        GlobalTest.globalSetup();
        threadNames.push(Thread.currentThread().getName());
        LOG.debug("Thread '" + Thread.currentThread().getName() + "' -> '" + alias + "'");
        Thread.currentThread().setName(alias);
        testLock.writeLock().acquire();
    }

    /**
     * @see TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        try {
            GlobalTest.globalTeardown(alias);
            Thread.currentThread().setName((String) threadNames.pop());
            LOG.debug("Thread '" + alias + "' -> '" + Thread.currentThread().getName() + "'");
        } finally {
            testLock.writeLock().release();
        }
    }

}