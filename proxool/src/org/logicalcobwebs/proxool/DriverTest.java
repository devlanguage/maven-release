/*
 * This software is released under a licence similar to the Apache Software Licence.
 * See org.logicalcobwebs.proxool.package.html for details.
 * The latest version is available at http://proxool.sourceforge.net
 */
package org.logicalcobwebs.proxool;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.logicalcobwebs.proxool.ProxoolConstants;
import org.logicalcobwebs.proxool.ProxoolDriver;
import org.logicalcobwebs.proxool.ProxoolFacade;

import java.sql.DriverManager;
import java.util.Properties;

/**
 * Tests {@link ProxoolDriver}
 *
 * @version $Revision: 1.1 $, $Date: 2015/06/30 06:16:28 $
 * @author bill
 * @author $Author: ygong $ (current maintainer)
 * @since Proxool 0.8
 */
public class DriverTest extends AbstractProxoolTest {

    private static final Log LOG = LogFactory.getLog(DriverTest.class);

    public DriverTest(String alias) {
        super(alias);
    }

    /**
     * Can we refer to the same pool by either the complete URL or the alias?
     */
    public void testAlias() throws Exception {

        String testName = "alias";
        String alias = testName;

        // Register pool
        String url = TestHelper.buildProxoolUrl(alias,
                TestConstants.HYPERSONIC_DRIVER,
                TestConstants.HYPERSONIC_TEST_URL);
        Properties info = new Properties();
        info.setProperty(ProxoolConstants.USER_PROPERTY, TestConstants.HYPERSONIC_USER);
        info.setProperty(ProxoolConstants.PASSWORD_PROPERTY, TestConstants.HYPERSONIC_PASSWORD);
        DriverManager.getConnection(url, info).close();
        assertEquals("servedCount", 1, ProxoolFacade.getSnapshot(alias).getServedCount());

        // Get it back by url
        url = TestHelper.buildProxoolUrl(alias,
                TestConstants.HYPERSONIC_DRIVER,
                TestConstants.HYPERSONIC_TEST_URL);
        DriverManager.getConnection(url).close();
        assertEquals("servedCount", 2, ProxoolFacade.getSnapshot(alias).getServedCount());

        // Get it back by name
        url = TestHelper.buildProxoolUrl(alias);
        DriverManager.getConnection(ProxoolConstants.PROXOOL + "." + alias).close();
        assertEquals("servedCount", 3, ProxoolFacade.getSnapshot(alias).getServedCount());

    }


}