package org.logicalcobwebs.proxool;

import java.io.IOException;


/**
 * TODO: Document!
 *
 * @author Mark Eagle
 * @author Phil Barnes
 * @since Mar 16, 2006 @ 7:55:30 AM
 */
public abstract class AbstractSpringIntegrationTestBase extends TransactionContext {
    public AbstractSpringIntegrationTestBase(JDBCPersistenceAdapter persistenceAdapter) throws IOException {
		super(persistenceAdapter);
		// TODO Auto-generated constructor stub
	}

	protected String[] getConfigLocations() {
        return new String[]{
                "classpath:org/logicalcobwebs/proxool/applicationContext.xml"
        };
    }

    protected void onSetUpBeforeTransaction() throws Exception {
        GlobalTest.globalSetup();
    }

    
}
