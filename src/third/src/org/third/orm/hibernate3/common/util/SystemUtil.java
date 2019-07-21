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
