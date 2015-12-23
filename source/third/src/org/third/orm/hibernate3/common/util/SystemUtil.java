/**
 * Copyright reserved by Tellabs Communication Corp. LTD. The file org.basic.util.SystemUtil.java is
 * created on Sep 22, 2007 11:27:17 AM
 */
package org.third.orm.hibernate3.common.util;

import org.apache.commons.logging.Log;

public class SystemUtil {

    public static BasicException handleException(Log logger, Throwable e, String message) {

        logger.info(message);
        e.printStackTrace();
        return new BasicException(message);
    }

    public static boolean isNullOrBlank(Object obj) {

        return (obj == null) || obj.toString().trim().equals("");
    }
}
