package org.basic.common.util;

import java.net.InetAddress;

/**
 * <pre>
 *
 * </pre>
 */
public class NetworkUtil {
    public static String getLocalHostName() {
        String hostName;
        try {
            InetAddress addr = InetAddress.getLocalHost();
            hostName = addr.getHostName();
        } catch (Exception ex) {
            hostName = "";
        }
        return hostName;
    }

    public static String[] getAllLocalHostIP() {
        String[] ret = null;
        try {
            String hostName = getLocalHostName();
            if (hostName.length() > 0) {
                InetAddress[] addrs = InetAddress.getAllByName(hostName);
                if (addrs.length > 0) {
                    ret = new String[addrs.length];
                    for (int i = 0; i < addrs.length; i++) {
                        ret[i] = addrs[i].getHostAddress();
                    }
                }
            }

        } catch (Exception ex) {
            ret = null;
        }
        boolean isFound = false;
        for (String s : ret) {
            if ("10.0.0.2".equals(s)) {
                isFound = true;
            }
        }

        if (!isFound) {
            String[] all = new String[ret.length + 1];
            for (int i = 0; i < ret.length; ++i) {
                all[i] = ret[i];
            }
            all[ret.length] = "10.0.0.2";
            return all;
        } else {
            return ret;
        }
    }
}
