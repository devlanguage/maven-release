/*
 * This software is released under a licence similar to the Apache Software Licence.
 * See org.logicalcobwebs.proxool.package.html for details.
 * The latest version is available at http://proxool.sourceforge.net
 */
package org.logicalcobwebs.proxool;

import java.sql.SQLException;
import java.sql.Statement;

/**
 * Supports {@link InjectableInterfaceTest}
 * @author <a href="mailto:bill@logicalcobwebs.co.uk">Bill Horsman</a>
 * @author $Author: ygong $ (current maintainer)
 * @version $Revision: 1.1 $, $Date: 2015/06/30 06:16:29 $
 * @since 0.9.0
 */
public interface HsqlStatementIF extends Statement {

}

/*
 Revision history:
 $Log: HsqlStatementIF.java,v $
 Revision 1.1  2015/06/30 06:16:29  ygong
 *** empty log message ***

 Revision 1.2  2004/06/17 21:33:54  billhorsman
 Mistake. Can't put private classes in the interface. Doh.

 Revision 1.1  2004/06/02 20:59:52  billhorsman
 New injectable interface tests

*/